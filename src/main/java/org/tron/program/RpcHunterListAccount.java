package org.tron.program;

import java.util.Optional;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.tron.api.GrpcAPI.AccountList;
import org.tron.common.utils.ByteArray;
import org.tron.service.WalletClient;

public class RpcHunterListAccount extends AbstractJavaSamplerClient{
  private WalletClient walletClient;

  @Override
  public void setupTest(JavaSamplerContext context) {
    walletClient = new WalletClient();
    walletClient.init(0);
  }

  @Override
  public void teardownTest(JavaSamplerContext context) {

  }

  @Override
  public SampleResult runTest(JavaSamplerContext context) {
    SampleResult sampleResult = new SampleResult();
    boolean success = true;
    sampleResult.sampleStart();

    Optional<AccountList> result = walletClient.listAccounts();

    if (result.isPresent()) {
      AccountList accountList = result.get();
      accountList.getAccountsList().forEach(account -> {
        System.out.println(ByteArray.toHexString(account.getAddress().toByteArray()));
      });
    }

    sampleResult.sampleEnd();
    sampleResult.setSuccessful(success);
    return sampleResult;
  }

  public static void main(String[] args) {
    Arguments arguments = new Arguments();
    JavaSamplerContext context = new JavaSamplerContext(arguments);
    RpcHunterListAccount hunter = new RpcHunterListAccount();
    hunter.setupTest(context);
    hunter.runTest(context);
    hunter.teardownTest(context);
  }
}
