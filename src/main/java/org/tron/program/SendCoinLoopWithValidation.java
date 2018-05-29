package org.tron.program;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.RateLimiter;

import java.io.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.Getter;
import org.apache.commons.csv.CSVRecord;
import org.tron.Validator.LongValidator;
import org.tron.Validator.StringValidator;
import org.tron.api.GrpcAPI;
import org.tron.common.crypto.ECKey;
import org.tron.common.utils.*;
import org.tron.protos.Contract;
import org.tron.protos.Protocol.Transaction;
import org.tron.service.WalletClient;
import org.tron.protos.Protocol;



//Example --tps 10000 --amount 1 --privatekeyFile privatekey.csv --count 1000000 --output trxsdata.csv
public class SendCoinLoopWithValidation {
    private static final int THREAD_COUNT = 160;

    private static List<WalletClient> walletClients = new ArrayList<>();

    public static void main(String[] args) throws IOException,InterruptedException {
        SendCoinArgsWithVal args1 = new SendCoinArgsWithVal();
        JCommander.newBuilder().addObject(args1).build().parse(args);

        double tps = args1.getTps();
        long count = args1.getCount();

        // init ECKeys for new accounts
        List<ECKey> keys = new ArrayList<>();
        for(int i=0;i<Math.sqrt(count/THREAD_COUNT);i++){
            ECKey key =  new ECKey(Utils.getRandom());
            System.err.println("init account: " + Base58.encode58Check(key.getAddress()));
            keys.add(key);
        }

        // private key
        List<String> privateKeyList = getStrings(args1.getPrivateKeyFile());
        int privateKeySize = privateKeyList.size();
        if (privateKeySize == 0) {
            System.out.println("private key is empty");
            return;
        }

        walletClients = IntStream.range(0, THREAD_COUNT).mapToObj(i -> {
            WalletClient walletClient = new WalletClient(true);
            walletClient.init();
            return walletClient;
        }).collect(Collectors.toList());

        WalletClient rootClient = new WalletClient(privateKeyList.get(0));
        rootClient.init();

        int accountNum = (int)Math.sqrt(count/THREAD_COUNT) + 1;

        // increase bandwidth
        rootClient.freezeBalance((long)accountNum * (long)1000 ,3);

        // send every account 1000 TRX to create the account on the chain (at least 1 TRX)
        keys.forEach(key->{
            boolean b= false;
            GrpcAPI.Return response;
            int loop = 0;
            do {
                if(loop!=0){
                    try{
                        Thread.sleep(1000);
                    }catch (InterruptedException e){

                    }
                }
                response= rootClient.sendCoinResponse(key.getAddress(), (long) 10 * (long) 1000000);
                System.err.println(response.getMessage().toStringUtf8());
                System.err.println(response.getMessage());
                System.err.println(response.toString());
                b = response.getResult();
                loop++;
                if(b==false){
                    System.err.println("failed to create "+ Base58.encode58Check(key.getAddress()) + " "+loop);
                }
            }while(b == false && loop < 20);
            if(b==false){
                System.err.println("failed to create "+ Base58.encode58Check(key.getAddress()));
            }

            // sendCoinAmount >= Transaction * amount + freezeTRX
            rootClient.sendCoin(key.getAddress(), (long) 70_0010 * (long) 1_000_000 - (long) 10 * (long) 1_000_000);
        });


        // wait all the new accounts to be set up
        Thread.sleep(3000);

        List<ECKey> failedKey = new ArrayList<>();
        keys.forEach(key->{
            WalletClient walletClient = new WalletClient(key);

            // need to freeze at least (freezeTRX) : (accountNum-1) * accountNum * 0.1 / 3 TRX
            // TransactionNum = (accountNum - 1)* accountNum
            // requiredBandwidth = TransactionNum * 0.1
            // freezeTRXPerAccount = (requiredBandwidth / 3)   TRX

            // freezeTRXPerAccount >= sqrt(count * THREAD_COUNT) / 30    trx
            // so 200*1000000 can support all case
            GrpcAPI.Return freezeResult = walletClient.freezeBalanceResponse((long)70_0000*1_000_000,3);
            int loop = 0;
            while(!freezeResult.getResult()&&loop<2){
                loop++;
                try {
                    Thread.sleep(1000);
                }
                catch(InterruptedException e){

                }
                freezeResult = walletClient.freezeBalanceResponse((long)70_0000*1000000,3);
            }
            if(freezeResult.getResult()==false){
                failedKey.add(key);
                System.err.println("freeze failed "+Base58.encode58Check(key.getAddress()) +" "+freezeResult.getMessage());
            }
            else{
                System.err.println("freeze success "+Base58.encode58Check(key.getAddress()));
            }
        });

        failedKey.forEach(key->{
            if(keys.indexOf(key)!=-1){
                keys.remove(key);
            }
        });

        long amount = args1.getAmount();

        File f = new File(args1.getOutput());
        FileOutputStream fos = new FileOutputStream(f);

        Thread.sleep(10000);
        AtomicInteger counter = new AtomicInteger(0);

        long sum1 =0;
        System.err.println("\nBefore Transaction");
        for(int i=0;i<keys.size();i++) {
            Protocol.Account account = WalletClient.queryAccount(keys.get(i).getAddress());
            sum1 += account.getBalance();
            System.err.println("\n" + Base58.encode58Check(keys.get(i).getAddress()) + " : " + account.getBalance());
        }
        System.err.println("\n Before Transaction sum: "+sum1);


        rateLimiter(tps, keys ,amount, count,rootClient, fos, counter);

        long sum2 =0;
        System.err.println("\nAfter Transaction");
        for(int i=0;i<keys.size();i++) {
            Protocol.Account account = WalletClient.queryAccount(keys.get(i).getAddress());
            sum2 += account.getBalance();
            System.err.println("\n" + Base58.encode58Check(keys.get(i).getAddress()) + " : " + account.getBalance());
        }
        System.err.println("\nAfter Transaction sum: "+sum2);

    }

    public static void rateLimiter(double tps,List<ECKey> keys,long amount,long count, WalletClient root, FileOutputStream fos,AtomicInteger counter) {
        ListeningExecutorService executorService = MoreExecutors
                .listeningDecorator(Executors.newFixedThreadPool(THREAD_COUNT));
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);
        RateLimiter limiter = RateLimiter.create(tps);

        for (int i = 0; i < THREAD_COUNT; ++i) {
            executorService.execute(new TaskWithVal(walletClients.get(i % THREAD_COUNT), limiter, THREAD_COUNT,
                    keys,amount,count,  fos, counter, latch));
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }

    private static List<String> getStrings(String filePath) {
        List<CSVRecord> read = CsvUtils.read(new File(filePath));
        List<String> stringList = new ArrayList();

        read.forEach(l -> {
            stringList.add(l.get(0));
        });

        return stringList;
    }
}

class TaskWithVal implements Runnable {

    private static LongAdder trueCount = new LongAdder();
    private static LongAdder falseCount = new LongAdder();
    private static LongAdder currentCount = new LongAdder();
    private static ConcurrentHashMap<Long, LongAdder> resultMap = new ConcurrentHashMap<>();
    public static final ScheduledExecutorService service = Executors
            .newSingleThreadScheduledExecutor();
    private WalletClient walletClient;
    private RateLimiter limiter;
    private static LongAdder endCounts = new LongAdder();
    private static int threadCount;
    private AtomicInteger counter;

    private List<ECKey> keys;
    private long amount;
    private long count;
    private CountDownLatch latch;
    private FileOutputStream fos;
    public static long sum ;

    static {
        service.scheduleAtFixedRate(() -> {
            System.out.println(
                    "current: " + currentCount.longValue()
                            + ", true: " + trueCount.longValue()
                            + ", false: " + falseCount.longValue()
                            + ", timestamp: " + (System.currentTimeMillis() / 1000)
                            + ", map: " + resultMap);

            if (endCounts.longValue() == threadCount) {
                service.shutdown();
            }
        }, 5, 5, TimeUnit.SECONDS);
    }

    public TaskWithVal(final WalletClient walletClient, RateLimiter limiter,
                 int threadCount,List<ECKey> keys,
                       long amount, long count,  FileOutputStream fos, AtomicInteger counter, CountDownLatch latch) {
        this.walletClient = walletClient;
        this.limiter = limiter;
        this.threadCount = threadCount;
        this.keys = keys;
        this.amount = amount;
        this.count = count;
        this.fos = fos;
        this.counter = counter;
        this.sum =0;
        this.latch = latch;
    }

    @Override
    public void run() {
        //Math.sqrt(count / threadCount) + 1


        for(int i = 0; i< keys.size(); i++){
            for(int j = 0; j< keys.size(); j++){
                if(i == j){
                    continue;
                }
                int c = counter.incrementAndGet();
                Contract.TransferContract contract = WalletClient
                    .createTransferContract(keys.get(j).getAddress(), keys.get(i).getAddress() , amount);
                walletClient = new WalletClient(keys.get(i));

//                GrpcAPI.Return freezeResult = walletClient.freezeBalances(10000,3);
//                if(freezeResult.getResult()==false){
//                    System.err.println("freeze failed "+Base58.encode58Check(keys.get(i).getAddress())
//                            +" "+freezeResult.getMessage());
//
//                }
//                else{
//                    System.err.println("freeze success "+Base58.encode58Check(keys.get(i).getAddress()));
//                }
//
//                Protocol.Account account = WalletClient.queryAccount(keys.get(i).getAddress());
//                System.err.println("\nBefore transaction: " + account.getBalance());


                Transaction transaction = walletClient.createTransaction4Transfer(contract);
                transaction = walletClient.signTransaction(transaction);
                try{
                    transaction.writeDelimitedTo(fos);
                }catch (IOException e){
                    e.printStackTrace();
                }

                if ((c + 1) % 1000 == 0) {
                    System.out.println("create transaction current: " + (c + 1));
                }
                limiter.acquire();
                GrpcAPI.Return response= walletClient.broadcastTransaction(transaction);
                boolean b = response.getResult();
                if (b) {
                    trueCount.increment();
                    System.err.println("Success from:" + Base58.encode58Check(keys.get(i).getAddress()) + " to:" + Base58.encode58Check(keys.get(j).getAddress()));
                } else {
                    falseCount.increment();
                    if(response.getMessage().size()==0){
                        System.err.println(response.toString().replace("\n",""));
                    }
                    else {
                        System.err.println(response.getMessage().toStringUtf8()+" from:"+ Base58.encode58Check(keys.get(i).getAddress()) + " to:" + Base58.encode58Check(keys.get(j).getAddress()));
                    }
                }
                currentCount.increment();

                long currentMinutes = System.currentTimeMillis() / 1000L / 60;

                resultMap.computeIfAbsent(currentMinutes, k -> new LongAdder()).increment();
            }
        }
        this.endCounts.increment();

        latch.countDown();
    }
}

class SendCoinArgsWithVal {

    @Getter
    @Parameter(names = {
            "--tps"}, description = "tps", required = true, validateWith = LongValidator.class)
    private double tps;


    @Getter
    @Parameter(names = {
            "--privatekeyFile"}, description = "Private key file", required = true, validateWith = StringValidator.class)
    private String privateKeyFile;


    @Getter
    @Parameter(names = {
            "--amount"}, description = "Drops amount per transaction", required = true, validateWith = LongValidator.class)
    private long amount;

    @Getter
    @Parameter(names = {
            "--count"}, description = "Transaction counts", required = true, validateWith = LongValidator.class)
    private long count;

    @Getter
    @Parameter(names = {
            "--output"}, description = "Save data file", required = true, validateWith = StringValidator.class)
    private String output;

}