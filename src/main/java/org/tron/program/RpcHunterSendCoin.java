package org.tron.program;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.spongycastle.util.encoders.Hex;
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
    arguments.addArgument("toAddress", "a05e5f8ab18990272b7dab71220df060135050f60e");
    arguments.addArgument("amount", "1");
    arguments.addArgument("privateKey", "cf1984a8312e0d435f25fd2a6ab19f8b06dae77d6b23a731d9ce27e996886913");
    return arguments;
  }

  @Override
  public void setupTest(JavaSamplerContext context) {
    toAddress = context.getParameter("toAddress");
    amount = context.getParameter("amount");
    privateKey = context.getParameter("privateKey");

    to = Hex.decode(toAddress);
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
    arguments.addArgument("toAddress", "a05e5f8ab18990272b7dab71220df060135050f60e");
    arguments.addArgument("amount", "1");
    arguments.addArgument("privateKey", "cf1984a8312e0d435f25fd2a6ab19f8b06dae77d6b23a731d9ce27e996886913");
    JavaSamplerContext context = new JavaSamplerContext(arguments);
    RpcHunterSendCoin hunter = new RpcHunterSendCoin();
    hunter.setupTest(context);
    hunter.runTest(context);
    hunter.teardownTest(context);
  }
}
