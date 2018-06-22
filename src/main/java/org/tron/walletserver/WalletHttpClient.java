package org.tron.walletserver;


import com.typesafe.config.Config;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import com.adobe.xmp.impl.Base64;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;
import org.tron.core.config.Configuration;
import org.tron.core.config.Parameter.CommonConstant;
import org.tron.core.exception.CipherException;

public class WalletHttpClient extends WalletClient{
  public CloseableHttpClient client = HttpClients.createDefault();
  public String HOST_IP = "http://47.93.9.236:18890";


  public WalletHttpClient(byte[] priKey) throws CipherException {
    super(priKey);
  }

  public void initIp (int i){
    Config config = Configuration.getByPath("config.conf");
    if (config.hasPath("http.ip.list")) {
      HOST_IP = config.getStringList("http.ip.list").get(i);
      nodeListSize =config.getStringList("http.ip.list").size();
    }
  }





  /**
   *
   * @param owner_address Base58 String
   * @param to_address Base58 String
   * @return
   * @throws IOException
   */
  public boolean sendCoinResponse(String owner_address, String to_address,long amount)
      throws IOException, ParseException {

    String temp = "THph9K2M2nLvkianrMGswRhz5hjSA9fuH7";
    String tempStr = new String(Base64.encode(WalletClient.decodeFromBase58Check(temp)),Charset.forName("UTF-8"));
    tempStr = "QZkrFsqOXHEuZ1QqjB24zGSs4txt";
    String owner_address_base64 = new String(Base64.encode(WalletClient.decodeFromBase58Check(owner_address)),Charset.forName("UTF-8"));
    String to_address_base64 = new String(Base64.encode(WalletClient.decodeFromBase58Check(to_address)),Charset.forName("UTF-8"));

    JSONObject transaction = createTransfer(owner_address_base64,tempStr,amount);
    //JSONObject transaction = createTransfer(owner_address_base64,new String(Base64.encode(WalletClient.decodeFromBase58Check(temp)),Charset.forName("UTF-8")),amount);
    JSONObject signedTransaction = this.signTransaction(transaction);
    boolean response =this.broadcast(signedTransaction);

    return response;

  }

  public HttpResponse getNodeList() throws IOException {
    HttpGet httpGet = new HttpGet(HOST_IP+"/wallet/listnodes");

    HttpResponse response = client.execute(httpGet);
    HttpEntity entity=response.getEntity();
    StringWriter writer=new StringWriter();
    IOUtils.copy(entity.getContent(),writer);
    String anwser = writer.toString();
    return response;
  }

  /**
   *
   * @param owner_address_base64
   * @param to_address_base64
   * @return a transaction presented as a json object
   * @throws IOException
   */
  public JSONObject createTransfer(String owner_address_base64, String to_address_base64, long amount)
      throws IOException {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("owner_address",owner_address_base64);
    jsonObject.put("to_address",to_address_base64);
    jsonObject.put("amount",amount);
    StringEntity params = new StringEntity(jsonObject.toString());

    HttpPost httpPost = new HttpPost(HOST_IP+"/wallet/createtransaction");
    httpPost.setEntity(params);
    HttpResponse response = client.execute(httpPost);
    HttpEntity entity=response.getEntity();
    StringWriter writer=new StringWriter();
    IOUtils.copy(entity.getContent(),writer);
    String anwser = writer.toString();
    return new JSONObject(anwser);
  }

  public JSONObject signTransaction(JSONObject transaction ) throws ParseException, IOException {
    transaction = setTimestamp(transaction);
    HttpPost httpPost = new HttpPost(HOST_IP+"/wallet/gettransactionsign");
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("transaction",transaction);
    String privateKey = new String(Base64.encode(this.eckey.getPrivKeyBytes()),Charset.forName("UTF-8"));
    jsonObject.put("privatekey",privateKey);
    StringEntity params = new StringEntity(jsonObject.toString());
    httpPost.setEntity(params);
    HttpResponse response = client.execute(httpPost);
    HttpEntity entity=response.getEntity();
    StringWriter writer=new StringWriter();
    IOUtils.copy(entity.getContent(),writer);
    String anwser = writer.toString();
    return new JSONObject(anwser);
  }

  public boolean broadcast(JSONObject signedTransaction) throws IOException {
    HttpPost httpPost = new HttpPost(HOST_IP+"/wallet/broadcasttransaction");
    StringEntity params = new StringEntity(signedTransaction.toString());
    httpPost.setEntity(params);
    CloseableHttpResponse response = client.execute(httpPost);

    boolean result = false;
    System.out.println(response.getStatusLine().getReasonPhrase());
    if (response.getStatusLine().getStatusCode()==200){
      result =true;
    }
    return result;

  }

  public synchronized static JSONObject setTimestamp(JSONObject transaction) throws ParseException {
    long currentTime = System.currentTimeMillis()*1000000 + System.nanoTime()%1000000;
    String s = transaction.toString();
    s=s.replace("timestamp\":\"0","timestamp\":\""+String.valueOf(currentTime));
    JSONObject transactionWithTimeStamp = new JSONObject(s);
    return transactionWithTimeStamp;
  }

}
