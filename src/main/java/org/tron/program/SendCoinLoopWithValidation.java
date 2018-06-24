package org.tron.program;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.RateLimiter;

import java.io.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.tron.api.GrpcAPI;
import org.tron.common.crypto.ECKey;
import org.tron.common.utils.*;
import org.tron.core.exception.CancelException;
import org.tron.core.exception.CipherException;
import org.tron.walletserver.WalletClient;
import org.tron.protos.Protocol;



//Example --tps 1000 --amount 1  --count 1000000 --output trxsdata.csv --privatekey 8CB4480194192F30907E14B52498F594BD046E21D7C4D8FE866563A6760AC891 --config config3.conf
public class SendCoinLoopWithValidation {
    private static final int THREAD_COUNT = 160;

    private static List<WalletClient> walletClients = new ArrayList<>();

    public static void main(String[] args) throws IOException, InterruptedException, CipherException {
        SendCoinArgsWithVal args1 = new SendCoinArgsWithVal();
        JCommander.newBuilder().addObject(args1).build().parse(args);

        double tps = args1.getTps();
        long count = args1.getCount();

        // config file
        String config = args1.getConfig();

        // private key
        String rootPrivateKey = args1.getPrivateKey();


        byte[] privateKey = ByteArray.fromHexString(rootPrivateKey);
        WalletClient rootClient = new WalletClient(privateKey);
        rootClient.init(config,0);

        // init ECKeys for new accounts
        List<ECKey> keys = new ArrayList<>();
        for(int i=0;i<Math.sqrt(count/THREAD_COUNT);i++){
            ECKey key =  new ECKey(Utils.getRandom());
            System.err.println("init account: " +  WalletClient.encode58Check(key.getAddress()));
            keys.add(key);
        }



        int accountNum = (int)Math.sqrt(count/THREAD_COUNT) + 1;

        // increase bandwidth
        try {
            GrpcAPI.Return response = null;
            response = rootClient.freezeBalanceResponse((long) 5_000_000 * (long) 1000, 3);
        }catch (Exception e){
            e.printStackTrace();
        }
        //500_000_000_029
        //4000_000_000_000



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
                try {
                    response= rootClient.sendCoinResponse(key.getAddress(), (long) 10 * (long) 1_000_000);
//                    System.err.println(response.getMessage().toStringUtf8());
//                    System.err.println(response.getMessage());
//                    System.err.println(response.toString());
                    b = response.getResult();
                } catch (CipherException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (CancelException e) {
                    e.printStackTrace();
                }

                loop++;
                if(b==false){
                    System.err.println("failed to create "+ WalletClient.encode58Check(key.getAddress()) + " "+loop);
                }
            }while(b == false && loop < 2);
            if(b==false){
                System.err.println("failed to create "+ WalletClient.encode58Check(key.getAddress()));
            }

            // sendCoinAmount >= Transaction * amount + freezeTRX
            try {
                rootClient.sendCoinResponse(key.getAddress(), (long) 500_020 * (long) 1_000_000 - (long) 10 * (long) 1_000_000);
            } catch (CipherException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (CancelException e) {
                e.printStackTrace();
            }
        });

        walletClients = IntStream.range(0, 5).mapToObj(i -> {
            WalletClient walletClient = null;
            try {
                ECKey walletkey =  new ECKey(Utils.getRandom());
                walletClient = new WalletClient(walletkey.getPrivKeyBytes());
                walletClient.init(config,i % 5);
            } catch (CipherException e) {
                e.printStackTrace();
            }
            return walletClient;
        }).collect(Collectors.toList());

        // wait all the new accounts to be set up
        Thread.sleep(3000);

        List<ECKey> failedKey = new ArrayList<>();
        keys.forEach(key->{
            WalletClient walletClient = null;
            rootClient.eckey = key;
            rootClient.address = key.getAddress();


            GrpcAPI.Return freezeResult = null;
            try {
                freezeResult = rootClient.freezeBalanceResponse((long)20*1_000_000,3);
            } catch (CipherException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (CancelException e) {
                e.printStackTrace();
            }
            int loop = 0;
            while(null == freezeResult||(!freezeResult.getResult()&&loop<2)){
                loop++;
                try {
                    Thread.sleep(1000);
                }
                catch(InterruptedException e){

                }
                try {
                    freezeResult = rootClient.freezeBalanceResponse((long)20*1000_000,3);
                } catch (CipherException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (CancelException e) {
                    e.printStackTrace();
                }
            }
            if(freezeResult.getResult()==false){
                failedKey.add(key);
                System.err.println("freeze failed "+WalletClient.encode58Check(key.getAddress()) +" "+freezeResult.getMessage());
            }
            else{
                System.err.println("freeze success "+WalletClient.encode58Check(key.getAddress()));
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
            Protocol.Account account = rootClient.queryAccount(keys.get(i).getAddress());
            sum1 += account.getBalance();
            System.err.println("\n" + rootClient.encode58Check(keys.get(i).getAddress()) + " : " + account.getBalance());
        }
        System.err.println("\n Before Transaction sum: "+sum1);

        Protocol.Account accountBlackHole = rootClient.queryAccount(rootClient.decodeFromBase58Check("THmtHi1Rzq4gSKYGEKv1DPkV7au6xU1AUB"));
        System.err.println("\nBlackholeBefore" +" : " + accountBlackHole.getBalance());

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.err.println("System time before transactions: " + df.format(new Date()));



        rateLimiter(tps, walletClients,keys ,amount, count,rootClient, fos, counter);



        System.err.println("System time after transactions: " + df.format(new Date()));
        long sum2 =0;
        System.err.println("\nAfter Transaction");
        for(int i=0;i<keys.size();i++) {
            Protocol.Account account = rootClient.queryAccount(keys.get(i).getAddress());
            sum2 += account.getBalance();
            System.err.println("\n" + WalletClient.encode58Check(keys.get(i).getAddress()) + " : " + account.getBalance());
        }
        System.err.println("\nAfter Transaction sum: "+sum2);
        accountBlackHole = rootClient.queryAccount(rootClient.decodeFromBase58Check("THmtHi1Rzq4gSKYGEKv1DPkV7au6xU1AUB"));
        System.err.println("\nBlackholeAfter" +" : " + accountBlackHole.getBalance());

    }

    public static void rateLimiter(double tps,List<WalletClient> clients, List<ECKey> keys,long amount,long count, WalletClient root, FileOutputStream fos,AtomicInteger counter) {
        ListeningExecutorService executorService = MoreExecutors
                .listeningDecorator(Executors.newFixedThreadPool(THREAD_COUNT));
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);
        RateLimiter limiter = RateLimiter.create(tps);

        for (int i = 0; i < THREAD_COUNT; ++i) {
            executorService.execute(new TaskWithVal(limiter, clients,THREAD_COUNT,
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
    public List<WalletClient> Clients;

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

    public TaskWithVal(RateLimiter limiter,List<WalletClient> clients,
                 int threadCount,List<ECKey> keys,
                       long amount, long count,  FileOutputStream fos, AtomicInteger counter, CountDownLatch latch) {
        this.Clients = clients;
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
        for(int i = 0; i< keys.size(); i++){
            for(int j = 0; j< keys.size(); j++){
                if(i == j){
                    continue;
                }
                GrpcAPI.Return response = null;
                int c = counter.incrementAndGet();

                Clients.get(j%5).eckey = keys.get(i);
                Clients.get(j%5).address = Clients.get(j%5).eckey.getAddress();

                try {
                     response = Clients.get(j%5).sendCoinResponse(keys.get(j).getAddress(),amount);
                } catch (CipherException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (CancelException e) {
                    e.printStackTrace();
                }

                if ((c + 1) % 1000 == 0) {
                    System.out.println("create transaction current: " + (c + 1));
                }
                limiter.acquire();
                boolean b = response.getResult();
                if (b) {
                    trueCount.increment();
                    //System.err.println("Success from:" + WalletClient.encode58Check(keys.get(i).getAddress()) + " to:" + WalletClient.encode58Check(keys.get(j).getAddress()));
                } else {
                    falseCount.increment();
                    System.err.println(response);
                    System.err.println(response.toString().replace("\n",""));
                    System.err.println(response.getMessage().toStringUtf8()+" from:" + WalletClient.encode58Check(keys.get(i).getAddress()) + " to:" + WalletClient.encode58Check(keys.get(j).getAddress()));
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
            "--tps"}, description = "tps", required = true)
    private double tps;


    @Getter
    @Parameter(names = {
            "--privatekey"}, description = "Private key file", required = true)
    private String privateKey;


    @Getter
    @Parameter(names = {
            "--amount"}, description = "Drops amount per transaction", required = true)
    private long amount;

    @Getter
    @Parameter(names = {
            "--count"}, description = "Transaction counts", required = true)
    private long count;

    @Getter
    @Parameter(names = {
            "--output"}, description = "Save data file", required = true)
    private String output;

    @Getter
    @Parameter(names = {
        "--config"}, description = "configuration file path", required = true)
    private String config;

}