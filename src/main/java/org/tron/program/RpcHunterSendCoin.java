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
    arguments.addArgument("toAddress", "27d3byPxZXKQWfXX7sJvemJJuv5M65F3vjS");
    arguments.addArgument("amount", "1");
    arguments.addArgument("privateKey", "32012d7b024b2e62e0ca145f137bcfd2468cac99a1880b275e2e499b23af265c");
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
    walletClient.init();
  }

  @Override
  public void teardownTest(JavaSamplerContext context) {

  }

  @Override
  public SampleResult runTest(JavaSamplerContext context) {
    SampleResult sampleResult = new SampleResult();
    sampleResult.sampleStart();

    boolean b = walletClient.sendCoin(to, amountValue);

    System.out.println(b);
    sampleResult.sampleEnd();
    sampleResult.setSuccessful(b);
    return sampleResult;
  }

  public static void main(String[] args) {
    Arguments arguments = new Arguments();
    arguments.addArgument("toAddress", "27d3byPxZXKQWfXX7sJvemJJuv5M65F3vjS");
    arguments.addArgument("amount", "1");
    arguments.addArgument("privateKey", "32012d7b024b2e62e0ca145f137bcfd2468cac99a1880b275e2e499b23af265c");
    JavaSamplerContext context = new JavaSamplerContext(arguments);
    RpcHunterSendCoin hunter = new RpcHunterSendCoin();
    hunter.setupTest(context);
    hunter.runTest(context);
    hunter.teardownTest(context);
  }
}
