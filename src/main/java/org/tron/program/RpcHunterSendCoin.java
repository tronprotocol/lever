package org.tron.program;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.tron.common.utils.Base58;
import org.tron.service.WalletClient;

public class RpcHunterSendCoin extends AbstractJavaSamplerClient{
  private WalletClient walletClient;
  private String toAddress;
  private String amount;
  private String privateKey;
  private byte[] to;
  private long amountValue;

  @Override
  public Arguments getDefaultParameters() {
    Arguments arguments = new Arguments();
    arguments.addArgument("toAddress", "27ZESitosJfKouTBrGg6Nk5yEjnJHXMbkZp");
    arguments.addArgument("amount", "1");
    arguments.addArgument("privateKey", "cbe57d98134c118ed0d219c0c8bc4154372c02c1e13b5cce30dd22ecd7bed19e");
    return arguments;
  }

  @Override
  public void setupTest(JavaSamplerContext context) {
    toAddress = context.getParameter("toAddress");
    amount = context.getParameter("amount");
    privateKey = context.getParameter("privateKey");

    to = Base58.decodeFromBase58Check(toAddress);
    amountValue = new Long(amount);

    walletClient = new WalletClient(privateKey);
    walletClient.init(0);
  }

  @Override
  public void teardownTest(JavaSamplerContext context) {

  }

  @Override
  public SampleResult runTest(JavaSamplerContext context) {
    SampleResult sampleResult = new SampleResult();
    sampleResult.sampleStart();

    boolean b = walletClient.sendCoin(to, amountValue);

    System.out.println("" + b + ";" + System.currentTimeMillis()/1000);
    sampleResult.sampleEnd();
    sampleResult.setSuccessful(b);
    return sampleResult;
  }

  public static void main(String[] args) {
    Arguments arguments = new Arguments();
    arguments.addArgument("toAddress", "27ZESitosJfKouTBrGg6Nk5yEjnJHXMbkZp");
    arguments.addArgument("amount", "1");
    arguments.addArgument("privateKey", "cbe57d98134c118ed0d219c0c8bc4154372c02c1e13b5cce30dd22ecd7bed19e");
    JavaSamplerContext context = new JavaSamplerContext(arguments);
    RpcHunterSendCoin hunter = new RpcHunterSendCoin();
    hunter.setupTest(context);
    hunter.runTest(context);
    hunter.teardownTest(context);
  }
}
