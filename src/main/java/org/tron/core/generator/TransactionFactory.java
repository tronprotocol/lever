package org.tron.core.generator;

import org.tron.common.config.TestType;
import org.tron.core.generator.asset.TransferAsset;
import org.tron.core.generator.contract.DeployContract;
import org.tron.core.generator.contract.TriggerContract;
import org.tron.core.generator.transfer.TransferBalance;
import org.tron.protos.Protocol.Transaction;

public class TransactionFactory {

  public static Transaction create(TestType type) {
    Transaction transaction = null;

    switch (type) {
      case TRANSFER_BALANCE:
        transaction = new TransferBalance().create();
        break;
      case TRANSFER_ASSET:
        transaction = new TransferAsset().create();
        break;
      case DEPLOY_CONTRACT:
        transaction = new DeployContract().create();
        break;
      case TRIGGER_CONTRACT:
        transaction = new TriggerContract().create();
        break;
      case AIRDROP:
        break;
      default:
        break;
    }

    return transaction;
  }
}
