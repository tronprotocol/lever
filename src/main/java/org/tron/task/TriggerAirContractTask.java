package org.tron.task;

import static org.tron.core.contract.CreateSmartContract.createContractDeployContract;

import com.google.protobuf.ByteString;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.tron.common.config.Args;
import org.tron.common.crypto.ECKey;
import org.tron.common.utils.Base58;
import org.tron.common.utils.ByteArray;
import org.tron.common.utils.TransactionUtils;
import org.tron.protos.Contract.CreateSmartContract;
import org.tron.protos.Protocol;
import org.tron.protos.Protocol.Transaction.Contract.ContractType;
import org.tron.service.WalletGrpcClient;

@Slf4j
public class TriggerAirContractTask implements Task {

  private WalletGrpcClient client;
  private Args args;
  private String contractName;
  private String ownerAddress;
  private String Abi;
  private String Code;
  private long feeLimit;
  private String OwnerPrivateKey;
  private static byte[] contractAddress;

  public TriggerAirContractTask(){
    logger.info("Create task: {}.", getClass().getSimpleName());
  }

  @Override
  public void init(Args args) {
    logger.info("Init deploy air contract task.");
    contractName = "airdrop";
    ownerAddress = "27meR2d4HodFPYX2V8YRDrLuFpYdbLvBEWi";
    Abi = "[{\"constant\":true,\"inputs\":[],\"name\":\"name\",\"outputs\":[{\"name\":\"\",\"type\":\"string\"}],"
        + "\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[],"
        + "\"name\":\"stop\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},"
        + "{\"constant\":false,\"inputs\":[{\"name\":\"_spender\",\"type\":\"address\"},{\"name\":\"_value\",\"type\":\"uint256\"}],"
        + "\"name\":\"approve\",\"outputs\":[{\"name\":\"success\",\"type\":\"bool\"}],\"payable\":false,\"stateMutability\":\"nonpayable\","
        + "\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"totalSupply\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],"
        + "\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"_from\",\"type\":\"address\"},"
        + "{\"name\":\"_to\",\"type\":\"address\"},{\"name\":\"_value\",\"type\":\"uint256\"}],\"name\":\"transferFrom\",\"outputs\":[{\"name\":\"success\",\"type\":\"bool\"}],"
        + "\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"decimals\",\"outputs\":"
        + "[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\""
        + ":[{\"name\":\"_value\",\"type\":\"uint256\"}],\"name\":\"burn\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},"
        + "{\"constant\":true,\"inputs\":[{\"name\":\"\",\"type\":\"address\"}],\"name\":\"balanceOf\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],"
        + "\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"stopped\",\"outputs\""
        + ":[{\"name\":\"\",\"type\":\"bool\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\""
        + ":[],\"name\":\"symbol\",\"outputs\":[{\"name\":\"\",\"type\":\"string\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},"
        + "{\"constant\":false,\"inputs\":[{\"name\":\"_to\",\"type\":\"address\"},{\"name\":\"_value\",\"type\":\"uint256\"}],\"name\":\"transfer\",\"outputs\""
        + ":[{\"name\":\"success\",\"type\":\"bool\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\""
        + ":[],\"name\":\"start\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\""
        + ":[{\"name\":\"_name\",\"type\":\"string\"}],\"name\":\"setName\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},"
        + "{\"constant\":true,\"inputs\":[{\"name\":\"\",\"type\":\"address\"},{\"name\":\"\",\"type\":\"address\"}],\"name\":\"allowance\",\"outputs\""
        + ":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\""
        + ":[{\"name\":\"_from\",\"type\":\"address\"}],\"name\":\"getBalance\",\"outputs\":[{\"name\":\"_value\",\"type\":\"uint256\"}],\"payable\""
        + ":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},"
        + "{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"name\":\"_from\",\"type\":\"address\"},{\"indexed\":true,\"name\":\"_to\",\"type\":\"address\"},"
        + "{\"indexed\":false,\"name\":\"_value\",\"type\":\"uint256\"}],\"name\":\"Transfer\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\""
        + ":[{\"indexed\":true,\"name\":\"_owner\",\"type\":\"address\"},{\"indexed\":true,\"name\":\"_spender\",\"type\":\"address\"},"
        + "{\"indexed\":false,\"name\":\"_value\",\"type\":\"uint256\"}],\"name\":\"Approval\",\"type\":\"event\"}]";
    Code = "60806040526040805190810160405280600681526020017f54726f6e69780000000000000000000000000000000000000000000000000000815250600090805190602001906200005192919"
        + "06200020f565b506040805190810160405280600381526020017f5452580000000000000000000000000000000000000000000000000000000000815250600190805190602001906200009f92"
        + "91906200020f565b50600660025560006005556000600660006101000a81548160ff0219169083151502179055506000600660016101000a81548173ffffffffffffffffffffffffffffffffff"
        + "ffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055503480156200011457600080fd5b5033600660016101000a81548173ffffffffffffffffffffffffffffff"
        + "ffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555067016345785d8a000060058190555067016345785d8a0000600360003373fffffffffffffffffffff"
        + "fffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020819055503373ffffffffffffffffffffffffffffffffffffffff1660007fdd"
        + "f252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef67016345785d8a00006040518082815260200191505060405180910390a3620002be565b828054600181600116156"
        + "101000203166002900490600052602060002090601f016020900481019282601f106200025257805160ff191683800117855562000283565b8280016001018555821562000283579182015b8281"
        + "11156200028257825182559160200191906001019062000265565b5b50905062000292919062000296565b5090565b620002bb91905b80821115620002b75760008160009055506001016200029"
        + "d565b5090565b90565b61125480620002ce6000396000f3006080604052600436106100db576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff"
        + "16806306fdde03146100e057806307da68f514610170578063095ea7b31461018757806318160ddd146101ec57806323b872dd14610217578063313ce5671461029c57806342966c68146102c75"
        + "7806370a08231146102f457806375f12b211461034b57806395d89b411461037a578063a9059cbb1461040a578063be9a65551461046f578063c47f002714610486578063dd62ed3e146104ef57"
        + "8063f8b2cb4f14610566575b600080fd5b3480156100ec57600080fd5b506100f56105bd565b6040518080602001828103825283818151815260200191508051906020019080838360005b83811"
        + "01561013557808201518184015260208101905061011a565b50505050905090810190601f1680156101625780820380516001836020036101000a031916815260200191505b5092505050604051"
        + "80910390f35b34801561017c57600080fd5b5061018561065b565b005b34801561019357600080fd5b506101d2600480360381019080803573ffffffffffffffffffffffffffffffffffffffff1"
        + "69060200190929190803590602001909291905050506106d1565b604051808215151515815260200191505060405180910390f35b3480156101f857600080fd5b50610201610895565b604051808"
        + "2815260200191505060405180910390f35b34801561022357600080fd5b50610282600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803573f"
        + "fffffffffffffffffffffffffffffffffffffff1690602001909291908035906020019092919050505061089b565b604051808215151515815260200191505060405180910390f35b3480156102a"
        + "857600080fd5b506102b1610bd5565b6040518082815260200191505060405180910390f35b3480156102d357600080fd5b506102f260048036038101908080359060200190929190505050610bd"
        + "b565b005b34801561030057600080fd5b50610335600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610d00565b60405180828152602"
        + "00191505060405180910390f35b34801561035757600080fd5b50610360610d18565b604051808215151515815260200191505060405180910390f35b34801561038657600080fd5b5061038f610"
        + "d2b565b6040518080602001828103825283818151815260200191508051906020019080838360005b838110156103cf5780820151818401526020810190506103b4565b505050509050908101906"
        + "01f1680156103fc5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b34801561041657600080fd5b50610455600480360381019080803573fff"
        + "fffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190505050610dc9565b604051808215151515815260200191505060405180910390f35b34801561047b5"
        + "7600080fd5b50610484610fed565b005b34801561049257600080fd5b506104ed600480360381019080803590602001908201803590602001908080601f016020809104026020016040519081016"
        + "0405280939291908181526020018383808284378201915050505050509192919290505050611063565b005b3480156104fb57600080fd5b50610550600480360381019080803573fffffffffffff"
        + "fffffffffffffffffffffffffff169060200190929190803573ffffffffffffffffffffffffffffffffffffffff1690602001909291905050506110d6565b6040518082815260200191505060405"
        + "180910390f35b34801561057257600080fd5b506105a7600480360381019080803573ffffffffffffffffffffffffffffffffffffffff1690602001909291905050506110fb565b60405180828152"
        + "60200191505060405180910390f35b60008054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156"
        + "101000203166002900480156106535780601f1061062857610100808354040283529160200191610653565b820191906000526020600020905b815481529060010190602001808311610636578290"
        + "03601f168201915b505050505081565b3373ffffffffffffffffffffffffffffffffffffffff16600660019054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673fffffff"
        + "fffffffffffffffffffffffffffffffff161415156106b457fe5b6001600660006101000a81548160ff021916908315150217905550565b6000600660009054906101000a900460ff161515156106"
        + "ec57fe5b3373ffffffffffffffffffffffffffffffffffffffff1660001415151561070f57fe5b600082148061079a57506000600460003373ffffffffffffffffffffffffffffffffffffffff167"
        + "3ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffff"
        + "ffffff16815260200190815260200160002054145b15156107a557600080fd5b81600460003373ffffffffffffffffffffffffffffffffffffffff1673fffffffffffffffffffffffffffffffffff"
        + "fffff16815260200190815260200160002060008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020"
        + "819055508273ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff167f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8"
        + "c7c3b925846040518082815260200191505060405180910390a36001905092915050565b60055481565b6000600660009054906101000a900460ff161515156108b657fe5b3373ffffffffffffffff"
        + "ffffffffffffffffffffffff166000141515156108d957fe5b81600360008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020"
        + "01908152602001600020541015151561092757600080fd5b600360008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190"
        + "81526020016000205482600360008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020540110151515"
        + "6109b657600080fd5b81600460008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060003373ffff"
        + "ffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205410151515610a4157600080fd5b81600360008573ffffff"
        + "ffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206000828254019250508190555081600360008673ffffffffff"
        + "ffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206000828254039250508190555081600460008673ffffffffffffff"
        + "ffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060003373ffffffffffffffffffffffffffffffffffffffff1673ffffff"
        + "ffffffffffffffffffffffffffffffffff168152602001908152602001600020600082825403925050819055508273ffffffffffffffffffffffffffffffffffffffff168473ffffffffffffffffff"
        + "ffffffffffffffffffffff167fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef846040518082815260200191505060405180910390a3600190509392505050565b60"
        + "025481565b80600360003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205410151515610c295760"
        + "0080fd5b80600360003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206000828254039250508190"
        + "555080600360008073ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206000828254019250508190555060003373ffffffffffffffffffffffffffffffffffff"
        + "ffff167fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef836040518082815260200191505060405180910390a350565b600360205280600052604060002060009150"
        + "90505481565b600660009054906101000a900460ff1681565b60018054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182"
        + "805460018160011615610100020316600290048015610dc15780601f10610d9657610100808354040283529160200191610dc1565b820191906000526020600020905b815481529060010190602001"
        + "808311610da457829003601f168201915b505050505081565b6000600660009054906101000a900460ff16151515610de457fe5b3373ffffffffffffffffffffffffffffffffffffffff1660001415"
        + "1515610e0757fe5b81600360003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205410151515610e"
        + "5557600080fd5b600360008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205482600360008673ff"
        + "ffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020540110151515610ee457600080fd5b81600360003373ff"
        + "ffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206000828254039250508190555081600360008573ffffff"
        + "ffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600082825401925050819055508273ffffffffffffffffffff"
        + "ffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff167fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef846040518082815260200191"
        + "505060405180910390a36001905092915050565b3373ffffffffffffffffffffffffffffffffffffffff16600660019054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673"
        + "ffffffffffffffffffffffffffffffffffffffff1614151561104657fe5b6000600660006101000a81548160ff021916908315150217905550565b3373ffffffffffffffffffffffffffffffffffff"
        + "ffff16600660019054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff161415156110bc57fe5b80600090805190602001"
        + "906110d2929190611183565b5050565b6004602052816000526040600020602052806000526040600020600091509150505481565b6000600660009054906101000a900460ff1615151561111657fe"
        + "5b3373ffffffffffffffffffffffffffffffffffffffff1660001415151561113957fe5b600360008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffff"
        + "ffffffffff168152602001908152602001600020549050809050919050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106111c457"
        + "805160ff19168380011785556111f2565b828001600101855582156111f2579182015b828111156111f15782518255916020019190600101906111d6565b5b5090506111ff9190611203565b509056"
        + "5b61122591905b80821115611221576000816000905550600101611209565b5090565b905600a165627a7a7230582055e37901411d4935e90a35732b921d158c888b183342c72a87210ecdeb7333680029";
    feeLimit = 100000000;
    OwnerPrivateKey = "cbe57d98134c118ed0d219c0c8bc4154372c02c1e13b5cce30dd22ecd7bed19e";
    this.args = args;
  }

  @Override
  public void start() {
    logger.info("Start deploy contract task.");
    CreateSmartContract contract = createContractDeployContract(
        contractName,
        ByteString.copyFrom(
            Objects.requireNonNull(
                Base58.decodeFromBase58Check(ownerAddress))).toByteArray(),
        Abi,
        Code,
        0L,
        100,
        null
    );

    Protocol.Transaction transaction = TransactionUtils
        .createTransaction(contract, ContractType.CreateSmartContract);

    transaction = transaction.toBuilder()
        .setRawData(transaction.getRawData().toBuilder().setFeeLimit(feeLimit).build()).build();

    transaction = TransactionUtils
        .signTransaction(transaction,
            ECKey.fromPrivate(ByteArray.fromHexString(OwnerPrivateKey)));

    String grpcHost = this.args.getGRpcCheckAddress().get(0);
    client = new WalletGrpcClient(
        grpcHost);
    boolean isSuccess = client.broadcastTransaction(transaction);
    if (isSuccess) {
      System.out.println(
          "success:" + Base58
              .encode58Check(TransactionUtils.generateContractAddress(Objects
                  .requireNonNull(Base58.decodeFromBase58Check(ownerAddress)), transaction)));

      contractAddress = TransactionUtils.generateContractAddress(Objects
          .requireNonNull(Base58.decodeFromBase58Check(ownerAddress)), transaction);

    } else {
      System.out.println("failed");
    }

    try {
      Thread.sleep(15000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

  }

  public static byte[] getContractAddress() {
    return contractAddress;
  }

  @Override
  public void shutdown() {
    logger.info("Shutdown deploy contract task.");
    try {
      client.shutdown();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

}
