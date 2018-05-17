package org.tron.program;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.RateLimiter;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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


//Example --tps 200 --datafile [trxsdata.csv] --toaddress toaddress.csv --amount 1 --privatekeyFile privatekey.csv --count 100 --output trxsdata.csv
public class SendCoinLoopWithValidation {
    private static final int THREAD_COUNT = 16;
    private static final int ACCOUNT_NUM = 100;

    private static List<WalletClient> walletClients = new ArrayList<>();
    private static Map<Long, List<Transaction>> transactionsMap = new HashMap<>();


    public static void main(String[] args) throws IOException,InterruptedException {
        SendCoinArgsWithVal args1 = new SendCoinArgsWithVal();
        JCommander.newBuilder().addObject(args1).build().parse(args);

        double tps = args1.getTps();

        // 读取to address
        List<String> toAddressList = getStrings(args1.getToAddress());
        List<byte[]> toAddressByteList = new ArrayList<>();
        int addressSize = toAddressList.size();
        if (addressSize == 0) {
            System.out.println("address is empty");
            return;
        }

        // 读取 private key
        List<String> privateKeyList = getStrings(args1.getPrivateKeyFile());
        int privateKeySize = privateKeyList.size();
        if (privateKeySize == 0) {
            System.out.println("private key is empty");
            return;
        }

        for (String toAddress : toAddressList) {
            byte[] addressBytes = Base58.decodeFromBase58Check(toAddress);
            toAddressByteList.add(addressBytes);
        }

        WalletClient rootClient = new WalletClient(privateKeyList.get(0));
        rootClient.init();

        // increase bandwidth
        rootClient.freezeBalance(ACCOUNT_NUM * 10 * 1000000,3);

        walletClients = IntStream.range(0, THREAD_COUNT).mapToObj(i -> {
            WalletClient walletClient = new WalletClient(true);
            walletClient.init();
            return walletClient;
        }).collect(Collectors.toList());


        long amount = args1.getAmount();

        File f = new File(args1.getOutput());
        FileOutputStream fos = new FileOutputStream(f);

        long count = args1.getCount();
        AtomicInteger counter = new AtomicInteger(0);
        rateLimiter(tps, toAddressByteList,privateKeyList,amount, count,rootClient, fos, counter);
    }

    public static void rateLimiter(double tps,List<byte[]> toAddressByteList,List<String> privateKeyList,long amount,long count, WalletClient root, FileOutputStream fos,AtomicInteger counter) {
        ListeningExecutorService executorService = MoreExecutors
                .listeningDecorator(Executors.newFixedThreadPool(THREAD_COUNT));
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);
        RateLimiter limiter = RateLimiter.create(tps);

        for (int i = 0; i < THREAD_COUNT; ++i) {
            executorService.execute(new TaskWithVal(walletClients.get(i % THREAD_COUNT), limiter, THREAD_COUNT,
                    toAddressByteList,privateKeyList,amount,count, root, fos, counter));
            latch.countDown();
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

    private List<String> privateKeyList;
    private List<byte[]> toAddressByteList;
    private long amount;
    private WalletClient root;
    private long count;
    private FileOutputStream fos;

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
                 int threadCount,List<byte[]> toAddressByteList, List<String> privateKeyList,
                       long amount, long count, WalletClient root, FileOutputStream fos, AtomicInteger counter) {
        this.walletClient = walletClient;
        this.limiter = limiter;
        this.threadCount = threadCount;
        this.privateKeyList= privateKeyList;
        this.toAddressByteList = toAddressByteList;
        this.amount = amount;
        this.root = root;
        this.count = count;
        this.fos = fos;
        this.counter = counter;
    }

    @Override
    public void run() {
        for (int i = 0; i < this.count / threadCount; i++) {
            int c = counter.incrementAndGet();
            ECKey key =  ECKey.fromPrivate(ByteArray.fromHexString(privateKeyList.get(c % privateKeyList.size())));
            byte[] owner = key.getAddress();
            Contract.TransferContract contract = WalletClient
                    .createTransferContract(toAddressByteList.get(c % toAddressByteList.size()),owner , amount);
            Transaction transaction = root.createTransaction4Transfer(contract);
            transaction = root.signTransaction(transaction);
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
            } else {
                falseCount.increment();
                if(response.getMessage().size()==0){
                    System.err.println(response.toString());
                }
                else {
                    System.err.println(response.getMessage().toStringUtf8());
                }
            }
            currentCount.increment();

            long currentMinutes = System.currentTimeMillis() / 1000L / 60;

            resultMap.computeIfAbsent(currentMinutes, k -> new LongAdder()).increment();
        }
        this.endCounts.increment();
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
            "--toaddress"}, description = "To address file", required = true, validateWith = StringValidator.class)
    private String toAddress;

    @Getter
    @Parameter(names = {
            "--amount"}, description = "Amount", required = true, validateWith = LongValidator.class)
    private long amount;

    @Getter
    @Parameter(names = {
            "--count"}, description = "Count", required = true, validateWith = LongValidator.class)
    private long count;

    @Getter
    @Parameter(names = {
            "--output"}, description = "Save data file", required = true, validateWith = StringValidator.class)
    private String output;

}