//package org.tron.program;
//
//import com.beust.jcommander.JCommander;
//import com.beust.jcommander.Parameter;
//import com.google.common.util.concurrent.ListeningExecutorService;
//import com.google.common.util.concurrent.MoreExecutors;
//import com.google.common.util.concurrent.RateLimiter;
//import io.reactivex.netty.protocol.http.client.HttpClientResponse;
//import io.reactivex.netty.servo.http.HttpClientListener;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.CountDownLatch;
//import java.util.concurrent.Executors;
//import java.util.concurrent.ScheduledExecutorService;
//import java.util.concurrent.TimeUnit;
//import java.util.concurrent.atomic.AtomicInteger;
//import java.util.concurrent.atomic.LongAdder;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;
//import lombok.Getter;
//import org.apache.commons.csv.CSVRecord;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.json.JSONObject;
//import org.json.simple.parser.ParseException;
//import org.tron.api.GrpcAPI;
//import org.tron.common.crypto.ECKey;
//import org.tron.common.utils.ByteArray;
//import org.tron.common.utils.CsvUtils;
//import org.tron.common.utils.Utils;
//import org.tron.core.exception.CancelException;
//import org.tron.core.exception.CipherException;
//import org.tron.protos.Protocol;
//import org.tron.walletserver.WalletClient;
//import org.tron.walletserver.WalletHttpClient;
//import sun.net.www.http.HttpClient;
//
//
////Example --tps 10000 --amount 1 --privatekeyFile privatekey.csv --count 1000000 --output trxsdata.csv
//public class SendCoinHttp {
//  private static final int THREAD_COUNT = 16;
//
//  private static List<WalletHttpClient> walletHttpClients = new ArrayList<>();
//
//  public static void main(String[] args)
//      throws IOException, InterruptedException, CipherException, ParseException {
//    SendCoinHttpArgs args1 = new SendCoinHttpArgs();
//    JCommander.newBuilder().addObject(args1).build().parse(args);
//    Date date = new Date();
//
//
//    double tps = args1.getTps();
//    long count = args1.getCount();
//
//    // init ECKeys for new accounts
//    List<ECKey> keys = new ArrayList<>();
//    for(int i=0;i<Math.sqrt(count/THREAD_COUNT);i++){
//      ECKey key =  new ECKey(Utils.getRandom());
//      System.err.println("init account: " +  WalletClient.encode58Check(key.getAddress()));
//      keys.add(key);
//    }
//
//
//    // private key
//    List<String> privateKeyList = getStrings(args1.getPrivateKeyFile());
//    int privateKeySize = privateKeyList.size();
//    if (privateKeySize == 0) {
//      System.out.println("private key is empty");
//      return;
//    }
//
//    byte[] privateKey = ByteArray.fromHexString(privateKeyList.get(0));
//    WalletHttpClient rootClient = new WalletHttpClient(privateKey);
//    rootClient.init();
//
//
//    int accountNum = (int)Math.sqrt(count/THREAD_COUNT) + 1;
//
//    // increase bandwidth
//    try {
//      GrpcAPI.Return response = null;
//      //response = rootClient.freezeBalanceResponse((long) 500_000_000 * (long) 1000, 3);
//    }catch (Exception e){
//      e.printStackTrace();
//    }
//    //500_000_000_029
//    //4000_000_000_000
//
//    boolean re = rootClient.sendCoinResponse(WalletClient.encode58Check(rootClient.eckey.getAddress()),
//       WalletClient.encode58Check(keys.get(0).getAddress()),1);
//
//    walletHttpClients = IntStream.range(0, THREAD_COUNT).mapToObj(i -> {
//      WalletHttpClient walletHttpClient = null;
//      try {
//        ECKey walletkey =  new ECKey(Utils.getRandom());
//        walletHttpClient = new WalletHttpClient(walletkey.getPrivKeyBytes());
//      } catch (CipherException e) {
//        e.printStackTrace();
//      }
//      walletHttpClient.init(THREAD_COUNT % 5);
//      walletHttpClient.initIp(THREAD_COUNT % 5);
//      return walletHttpClient;
//    }).collect(Collectors.toList());
//
//
//
//    // send every account 1000 TRX to create the account on the chain (at least 1 TRX)
//    keys.forEach(key->{
//      try {
//        //rootClient.sendCoinResponse(key.getAddress(), (long) 500_020 * (long) 1_000_000 - (long) 10 * (long) 1_000_000);
//        boolean res = rootClient.sendCoinResponse(WalletClient.encode58Check(rootClient.eckey.getAddress()),WalletClient.encode58Check(key.getAddress()), 1L * 1_000_000L);
//        if(res==false){
//          System.err.println("failed to create "+ WalletClient.encode58Check(key.getAddress()));
//        }
//      } catch (IOException e) {
//        e.printStackTrace();
//      } catch (ParseException e) {
//        e.printStackTrace();
//      }
//    });
//    System.err.println("Send coin to 80 account done! ");
//
//
//    // wait all the new accounts to be set up
//    Thread.sleep(3000);
//
//    List<ECKey> failedKey = new ArrayList<>();
////    keys.forEach(key->{
////      WalletClient walletClient = null;
////      try {
////        walletClient = new WalletClient(key.getPrivKeyBytes());
////      } catch (CipherException e) {
////        e.printStackTrace();
////      }
////
////      // need to freeze at least (freezeTRX) : (accountNum-1) * accountNum * 0.1 / 3 TRX
////      // TransactionNum = (accountNum - 1)* accountNum
////      // requiredBandwidth = TransactionNum * 0.1
////      // freezeTRXPerAccount = (requiredBandwidth / 3)   TRX
////
////      // freezeTRXPerAccount >= sqrt(count * THREAD_COUNT) / 30    trx
////      // so 200*1000000 can support all case
////      GrpcAPI.Return freezeResult = null;
////      try {
////        freezeResult = walletClient.freezeBalanceResponse((long)20*1_000_000,3);
////      } catch (CipherException e) {
////        e.printStackTrace();
////      } catch (IOException e) {
////        e.printStackTrace();
////      } catch (CancelException e) {
////        e.printStackTrace();
////      }
////      int loop = 0;
////      while(!freezeResult.getResult()&&loop<2){
////        loop++;
////        try {
////          Thread.sleep(1000);
////        }
////        catch(InterruptedException e){
////
////        }
////        try {
////          freezeResult = walletClient.freezeBalanceResponse((long)20*1000_000,3);
////        } catch (CipherException e) {
////          e.printStackTrace();
////        } catch (IOException e) {
////          e.printStackTrace();
////        } catch (CancelException e) {
////          e.printStackTrace();
////        }
////      }
////      if(freezeResult.getResult()==false){
////        failedKey.add(key);
////        System.err.println("freeze failed "+WalletClient.encode58Check(key.getAddress()) +" "+freezeResult.getMessage());
////      }
////      else{
////        System.err.println("freeze success "+WalletClient.encode58Check(key.getAddress()));
////      }
////    });
//
////    failedKey.forEach(key->{
////      if(keys.indexOf(key)!=-1){
////        keys.remove(key);
////      }
////    });
//
//    long amount = args1.getAmount();
//
//    File f = new File(args1.getOutput());
//    FileOutputStream fos = new FileOutputStream(f);
//
//    Thread.sleep(10000);
//    AtomicInteger counter = new AtomicInteger(0);
//
//    long sum1 =0;
//    System.err.println("\nBefore Transaction");
//    for(int i=0;i<keys.size();i++) {
//      Protocol.Account account = rootClient.queryAccount(keys.get(i).getAddress());
//      sum1 += account.getBalance();
//      System.err.println("\n" + WalletClient.encode58Check(keys.get(i).getAddress()) + " : " + account.getBalance());
//    }
//    System.err.println("\n Before Transaction sum: "+sum1);
//
//
//    rateLimiter(tps, keys ,amount, count,rootClient, fos, counter);
//
//    long sum2 =0;
//    System.err.println("\nAfter Transaction");
//    for(int i=0;i<keys.size();i++) {
//      Protocol.Account account = rootClient.queryAccount(keys.get(i).getAddress());
//      sum2 += account.getBalance();
//      System.err.println("\n" + WalletClient.encode58Check(keys.get(i).getAddress()) + " : " + account.getBalance());
//    }
//    System.err.println("\nAfter Transaction sum: "+sum2);
//
//  }
//
//  public static void rateLimiter(double tps,List<ECKey> keys,long amount,long count, WalletClient root, FileOutputStream fos,AtomicInteger counter) {
//    ListeningExecutorService executorService = MoreExecutors
//        .listeningDecorator(Executors.newFixedThreadPool(THREAD_COUNT));
//    CountDownLatch latch = new CountDownLatch(THREAD_COUNT);
//    RateLimiter limiter = RateLimiter.create(tps);
//
//    for (int i = 0; i < THREAD_COUNT; ++i) {
//      executorService.execute(new TaskWithVal(limiter,walletHttpClients.get(i % THREAD_COUNT), THREAD_COUNT,
//          keys,amount,count,  fos, counter, latch));
//    }
//
//    try {
//      latch.await();
//    } catch (InterruptedException e) {
//      e.printStackTrace();
//    } finally {
//      executorService.shutdown();
//    }
//  }
//
//  private static List<String> getStrings(String filePath) {
//    List<CSVRecord> read = CsvUtils.read(new File(filePath));
//    List<String> stringList = new ArrayList();
//
//    read.forEach(l -> {
//      stringList.add(l.get(0));
//    });
//
//    return stringList;
//  }
//}
//
//class TaskSendCoinHttp implements Runnable {
//
//  private static LongAdder trueCount = new LongAdder();
//  private static LongAdder falseCount = new LongAdder();
//  private static LongAdder currentCount = new LongAdder();
//  private static ConcurrentHashMap<Long, LongAdder> resultMap = new ConcurrentHashMap<>();
//  public static final ScheduledExecutorService service = Executors
//      .newSingleThreadScheduledExecutor();
//  private WalletHttpClient walletHttpClient;
//  private RateLimiter limiter;
//  private static LongAdder endCounts = new LongAdder();
//  private static int threadCount;
//  private AtomicInteger counter;
//
//  private List<ECKey> keys;
//  private long amount;
//  private long count;
//  private CountDownLatch latch;
//  private FileOutputStream fos;
//  public static long sum ;
//
//  static {
//    service.scheduleAtFixedRate(() -> {
//      System.out.println(
//          "current: " + currentCount.longValue()
//              + ", true: " + trueCount.longValue()
//              + ", false: " + falseCount.longValue()
//              + ", timestamp: " + (System.currentTimeMillis() / 1000)
//              + ", map: " + resultMap);
//
//      if (endCounts.longValue() == threadCount) {
//        service.shutdown();
//      }
//    }, 5, 5, TimeUnit.SECONDS);
//  }
//
//  public TaskSendCoinHttp(RateLimiter limiter,final WalletHttpClient walletClient,
//      int threadCount,List<ECKey> keys,
//      long amount, long count,  FileOutputStream fos, AtomicInteger counter, CountDownLatch latch) {
//    this.walletHttpClient = walletClient;
//    this.limiter = limiter;
//    this.threadCount = threadCount;
//    this.keys = keys;
//    this.amount = amount;
//    this.count = count;
//    this.fos = fos;
//    this.counter = counter;
//    this.sum =0;
//    this.latch = latch;
//  }
//
//  @Override
//  public void run() {
//    //Math.sqrt(count / threadCount) + 1
//
//
//    for(int i = 0; i< keys.size(); i++){
//      for(int j = 0; j< keys.size(); j++){
//        if(i == j){
//          continue;
//        }
//        boolean response = false;
//        int c = counter.incrementAndGet();
////                Contract.TransferContract contract = WalletClient
////                    .createTransferContract(keys.get(j).getAddress(), keys.get(i).getAddress() , amount);
//        try {
//          walletHttpClient = new WalletHttpClient(keys.get(i).getPrivKeyBytes());
//        } catch (CipherException e) {
//          e.printStackTrace();
//        }
//        try {
//          response = walletHttpClient.sendCoinResponse(WalletClient.encode58Check(keys.get(i).getAddress()),WalletClient.encode58Check(keys.get(j).getAddress()),amount);
//        } catch (IOException e) {
//          e.printStackTrace();
//        } catch (ParseException e) {
//          e.printStackTrace();
//        }
//
//
//        if ((c + 1) % 1000 == 0) {
//          System.out.println("create transaction current: " + (c + 1));
//        }
//        limiter.acquire();
//        if (response) {
//          trueCount.increment();
//          //System.err.println("Success from:" + WalletClient.encode58Check(keys.get(i).getAddress()) + " to:" + WalletClient.encode58Check(keys.get(j).getAddress()));
//        } else {
//          falseCount.increment();
//          System.err.println(response);
////          System.err.println(response.toString().replace("\n",""));
////          System.err.println(response.getMessage().toStringUtf8()+" from:" + WalletClient.encode58Check(keys.get(i).getAddress()) + " to:" + WalletClient.encode58Check(keys.get(j).getAddress()));
//        }
//        currentCount.increment();
//
//        long currentMinutes = System.currentTimeMillis() / 1000L / 60;
//
//        resultMap.computeIfAbsent(currentMinutes, k -> new LongAdder()).increment();
//      }
//    }
//    this.endCounts.increment();
//
//    latch.countDown();
//  }
//}
//
//class SendCoinHttpArgs {
//
//  @Getter
//  @Parameter(names = {
//      "--tps"}, description = "tps", required = true)
//  private double tps;
//
//
//  @Getter
//  @Parameter(names = {
//      "--privatekeyFile"}, description = "Private key file", required = true)
//  private String privateKeyFile;
//
//
//  @Getter
//  @Parameter(names = {
//      "--amount"}, description = "Drops amount per transaction", required = true)
//  private long amount;
//
//  @Getter
//  @Parameter(names = {
//      "--count"}, description = "Transaction counts", required = true)
//  private long count;
//
//  @Getter
//  @Parameter(names = {
//      "--output"}, description = "Save data file", required = true)
//  private String output;
//
//}