package org.tron.api;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.9.0)",
    comments = "Source: api/api.proto")
public final class WalletGrpc {

  private WalletGrpc() {}

  public static final String SERVICE_NAME = "protocol.Wallet";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getGetAccountMethod()} instead. 
  public static final io.grpc.MethodDescriptor<org.tron.protos.Protocol.Account,
      org.tron.protos.Protocol.Account> METHOD_GET_ACCOUNT = getGetAccountMethod();

  private static volatile io.grpc.MethodDescriptor<org.tron.protos.Protocol.Account,
      org.tron.protos.Protocol.Account> getGetAccountMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<org.tron.protos.Protocol.Account,
      org.tron.protos.Protocol.Account> getGetAccountMethod() {
    io.grpc.MethodDescriptor<org.tron.protos.Protocol.Account, org.tron.protos.Protocol.Account> getGetAccountMethod;
    if ((getGetAccountMethod = WalletGrpc.getGetAccountMethod) == null) {
      synchronized (WalletGrpc.class) {
        if ((getGetAccountMethod = WalletGrpc.getGetAccountMethod) == null) {
          WalletGrpc.getGetAccountMethod = getGetAccountMethod = 
              io.grpc.MethodDescriptor.<org.tron.protos.Protocol.Account, org.tron.protos.Protocol.Account>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "protocol.Wallet", "GetAccount"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.tron.protos.Protocol.Account.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.tron.protos.Protocol.Account.getDefaultInstance()))
                  .setSchemaDescriptor(new WalletMethodDescriptorSupplier("GetAccount"))
                  .build();
          }
        }
     }
     return getGetAccountMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getCreateTransactionMethod()} instead. 
  public static final io.grpc.MethodDescriptor<org.tron.protos.Contract.TransferContract,
      org.tron.protos.Protocol.Transaction> METHOD_CREATE_TRANSACTION = getCreateTransactionMethod();

  private static volatile io.grpc.MethodDescriptor<org.tron.protos.Contract.TransferContract,
      org.tron.protos.Protocol.Transaction> getCreateTransactionMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<org.tron.protos.Contract.TransferContract,
      org.tron.protos.Protocol.Transaction> getCreateTransactionMethod() {
    io.grpc.MethodDescriptor<org.tron.protos.Contract.TransferContract, org.tron.protos.Protocol.Transaction> getCreateTransactionMethod;
    if ((getCreateTransactionMethod = WalletGrpc.getCreateTransactionMethod) == null) {
      synchronized (WalletGrpc.class) {
        if ((getCreateTransactionMethod = WalletGrpc.getCreateTransactionMethod) == null) {
          WalletGrpc.getCreateTransactionMethod = getCreateTransactionMethod = 
              io.grpc.MethodDescriptor.<org.tron.protos.Contract.TransferContract, org.tron.protos.Protocol.Transaction>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "protocol.Wallet", "CreateTransaction"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.tron.protos.Contract.TransferContract.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.tron.protos.Protocol.Transaction.getDefaultInstance()))
                  .setSchemaDescriptor(new WalletMethodDescriptorSupplier("CreateTransaction"))
                  .build();
          }
        }
     }
     return getCreateTransactionMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getBroadcastTransactionMethod()} instead. 
  public static final io.grpc.MethodDescriptor<org.tron.protos.Protocol.Transaction,
      org.tron.api.GrpcAPI.Return> METHOD_BROADCAST_TRANSACTION = getBroadcastTransactionMethod();

  private static volatile io.grpc.MethodDescriptor<org.tron.protos.Protocol.Transaction,
      org.tron.api.GrpcAPI.Return> getBroadcastTransactionMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<org.tron.protos.Protocol.Transaction,
      org.tron.api.GrpcAPI.Return> getBroadcastTransactionMethod() {
    io.grpc.MethodDescriptor<org.tron.protos.Protocol.Transaction, org.tron.api.GrpcAPI.Return> getBroadcastTransactionMethod;
    if ((getBroadcastTransactionMethod = WalletGrpc.getBroadcastTransactionMethod) == null) {
      synchronized (WalletGrpc.class) {
        if ((getBroadcastTransactionMethod = WalletGrpc.getBroadcastTransactionMethod) == null) {
          WalletGrpc.getBroadcastTransactionMethod = getBroadcastTransactionMethod = 
              io.grpc.MethodDescriptor.<org.tron.protos.Protocol.Transaction, org.tron.api.GrpcAPI.Return>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "protocol.Wallet", "BroadcastTransaction"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.tron.protos.Protocol.Transaction.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.tron.api.GrpcAPI.Return.getDefaultInstance()))
                  .setSchemaDescriptor(new WalletMethodDescriptorSupplier("BroadcastTransaction"))
                  .build();
          }
        }
     }
     return getBroadcastTransactionMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getUpdateAccountMethod()} instead. 
  public static final io.grpc.MethodDescriptor<org.tron.protos.Contract.AccountUpdateContract,
      org.tron.protos.Protocol.Transaction> METHOD_UPDATE_ACCOUNT = getUpdateAccountMethod();

  private static volatile io.grpc.MethodDescriptor<org.tron.protos.Contract.AccountUpdateContract,
      org.tron.protos.Protocol.Transaction> getUpdateAccountMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<org.tron.protos.Contract.AccountUpdateContract,
      org.tron.protos.Protocol.Transaction> getUpdateAccountMethod() {
    io.grpc.MethodDescriptor<org.tron.protos.Contract.AccountUpdateContract, org.tron.protos.Protocol.Transaction> getUpdateAccountMethod;
    if ((getUpdateAccountMethod = WalletGrpc.getUpdateAccountMethod) == null) {
      synchronized (WalletGrpc.class) {
        if ((getUpdateAccountMethod = WalletGrpc.getUpdateAccountMethod) == null) {
          WalletGrpc.getUpdateAccountMethod = getUpdateAccountMethod = 
              io.grpc.MethodDescriptor.<org.tron.protos.Contract.AccountUpdateContract, org.tron.protos.Protocol.Transaction>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "protocol.Wallet", "UpdateAccount"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.tron.protos.Contract.AccountUpdateContract.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.tron.protos.Protocol.Transaction.getDefaultInstance()))
                  .setSchemaDescriptor(new WalletMethodDescriptorSupplier("UpdateAccount"))
                  .build();
          }
        }
     }
     return getUpdateAccountMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getVoteWitnessAccountMethod()} instead. 
  public static final io.grpc.MethodDescriptor<org.tron.protos.Contract.VoteWitnessContract,
      org.tron.protos.Protocol.Transaction> METHOD_VOTE_WITNESS_ACCOUNT = getVoteWitnessAccountMethod();

  private static volatile io.grpc.MethodDescriptor<org.tron.protos.Contract.VoteWitnessContract,
      org.tron.protos.Protocol.Transaction> getVoteWitnessAccountMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<org.tron.protos.Contract.VoteWitnessContract,
      org.tron.protos.Protocol.Transaction> getVoteWitnessAccountMethod() {
    io.grpc.MethodDescriptor<org.tron.protos.Contract.VoteWitnessContract, org.tron.protos.Protocol.Transaction> getVoteWitnessAccountMethod;
    if ((getVoteWitnessAccountMethod = WalletGrpc.getVoteWitnessAccountMethod) == null) {
      synchronized (WalletGrpc.class) {
        if ((getVoteWitnessAccountMethod = WalletGrpc.getVoteWitnessAccountMethod) == null) {
          WalletGrpc.getVoteWitnessAccountMethod = getVoteWitnessAccountMethod = 
              io.grpc.MethodDescriptor.<org.tron.protos.Contract.VoteWitnessContract, org.tron.protos.Protocol.Transaction>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "protocol.Wallet", "VoteWitnessAccount"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.tron.protos.Contract.VoteWitnessContract.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.tron.protos.Protocol.Transaction.getDefaultInstance()))
                  .setSchemaDescriptor(new WalletMethodDescriptorSupplier("VoteWitnessAccount"))
                  .build();
          }
        }
     }
     return getVoteWitnessAccountMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getCreateAssetIssueMethod()} instead. 
  public static final io.grpc.MethodDescriptor<org.tron.protos.Contract.AssetIssueContract,
      org.tron.protos.Protocol.Transaction> METHOD_CREATE_ASSET_ISSUE = getCreateAssetIssueMethod();

  private static volatile io.grpc.MethodDescriptor<org.tron.protos.Contract.AssetIssueContract,
      org.tron.protos.Protocol.Transaction> getCreateAssetIssueMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<org.tron.protos.Contract.AssetIssueContract,
      org.tron.protos.Protocol.Transaction> getCreateAssetIssueMethod() {
    io.grpc.MethodDescriptor<org.tron.protos.Contract.AssetIssueContract, org.tron.protos.Protocol.Transaction> getCreateAssetIssueMethod;
    if ((getCreateAssetIssueMethod = WalletGrpc.getCreateAssetIssueMethod) == null) {
      synchronized (WalletGrpc.class) {
        if ((getCreateAssetIssueMethod = WalletGrpc.getCreateAssetIssueMethod) == null) {
          WalletGrpc.getCreateAssetIssueMethod = getCreateAssetIssueMethod = 
              io.grpc.MethodDescriptor.<org.tron.protos.Contract.AssetIssueContract, org.tron.protos.Protocol.Transaction>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "protocol.Wallet", "CreateAssetIssue"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.tron.protos.Contract.AssetIssueContract.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.tron.protos.Protocol.Transaction.getDefaultInstance()))
                  .setSchemaDescriptor(new WalletMethodDescriptorSupplier("CreateAssetIssue"))
                  .build();
          }
        }
     }
     return getCreateAssetIssueMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getUpdateWitnessMethod()} instead. 
  public static final io.grpc.MethodDescriptor<org.tron.protos.Contract.WitnessUpdateContract,
      org.tron.protos.Protocol.Transaction> METHOD_UPDATE_WITNESS = getUpdateWitnessMethod();

  private static volatile io.grpc.MethodDescriptor<org.tron.protos.Contract.WitnessUpdateContract,
      org.tron.protos.Protocol.Transaction> getUpdateWitnessMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<org.tron.protos.Contract.WitnessUpdateContract,
      org.tron.protos.Protocol.Transaction> getUpdateWitnessMethod() {
    io.grpc.MethodDescriptor<org.tron.protos.Contract.WitnessUpdateContract, org.tron.protos.Protocol.Transaction> getUpdateWitnessMethod;
    if ((getUpdateWitnessMethod = WalletGrpc.getUpdateWitnessMethod) == null) {
      synchronized (WalletGrpc.class) {
        if ((getUpdateWitnessMethod = WalletGrpc.getUpdateWitnessMethod) == null) {
          WalletGrpc.getUpdateWitnessMethod = getUpdateWitnessMethod = 
              io.grpc.MethodDescriptor.<org.tron.protos.Contract.WitnessUpdateContract, org.tron.protos.Protocol.Transaction>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "protocol.Wallet", "UpdateWitness"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.tron.protos.Contract.WitnessUpdateContract.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.tron.protos.Protocol.Transaction.getDefaultInstance()))
                  .setSchemaDescriptor(new WalletMethodDescriptorSupplier("UpdateWitness"))
                  .build();
          }
        }
     }
     return getUpdateWitnessMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getCreateWitnessMethod()} instead. 
  public static final io.grpc.MethodDescriptor<org.tron.protos.Contract.WitnessCreateContract,
      org.tron.protos.Protocol.Transaction> METHOD_CREATE_WITNESS = getCreateWitnessMethod();

  private static volatile io.grpc.MethodDescriptor<org.tron.protos.Contract.WitnessCreateContract,
      org.tron.protos.Protocol.Transaction> getCreateWitnessMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<org.tron.protos.Contract.WitnessCreateContract,
      org.tron.protos.Protocol.Transaction> getCreateWitnessMethod() {
    io.grpc.MethodDescriptor<org.tron.protos.Contract.WitnessCreateContract, org.tron.protos.Protocol.Transaction> getCreateWitnessMethod;
    if ((getCreateWitnessMethod = WalletGrpc.getCreateWitnessMethod) == null) {
      synchronized (WalletGrpc.class) {
        if ((getCreateWitnessMethod = WalletGrpc.getCreateWitnessMethod) == null) {
          WalletGrpc.getCreateWitnessMethod = getCreateWitnessMethod = 
              io.grpc.MethodDescriptor.<org.tron.protos.Contract.WitnessCreateContract, org.tron.protos.Protocol.Transaction>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "protocol.Wallet", "CreateWitness"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.tron.protos.Contract.WitnessCreateContract.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.tron.protos.Protocol.Transaction.getDefaultInstance()))
                  .setSchemaDescriptor(new WalletMethodDescriptorSupplier("CreateWitness"))
                  .build();
          }
        }
     }
     return getCreateWitnessMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getTransferAssetMethod()} instead. 
  public static final io.grpc.MethodDescriptor<org.tron.protos.Contract.TransferAssetContract,
      org.tron.protos.Protocol.Transaction> METHOD_TRANSFER_ASSET = getTransferAssetMethod();

  private static volatile io.grpc.MethodDescriptor<org.tron.protos.Contract.TransferAssetContract,
      org.tron.protos.Protocol.Transaction> getTransferAssetMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<org.tron.protos.Contract.TransferAssetContract,
      org.tron.protos.Protocol.Transaction> getTransferAssetMethod() {
    io.grpc.MethodDescriptor<org.tron.protos.Contract.TransferAssetContract, org.tron.protos.Protocol.Transaction> getTransferAssetMethod;
    if ((getTransferAssetMethod = WalletGrpc.getTransferAssetMethod) == null) {
      synchronized (WalletGrpc.class) {
        if ((getTransferAssetMethod = WalletGrpc.getTransferAssetMethod) == null) {
          WalletGrpc.getTransferAssetMethod = getTransferAssetMethod = 
              io.grpc.MethodDescriptor.<org.tron.protos.Contract.TransferAssetContract, org.tron.protos.Protocol.Transaction>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "protocol.Wallet", "TransferAsset"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.tron.protos.Contract.TransferAssetContract.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.tron.protos.Protocol.Transaction.getDefaultInstance()))
                  .setSchemaDescriptor(new WalletMethodDescriptorSupplier("TransferAsset"))
                  .build();
          }
        }
     }
     return getTransferAssetMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getParticipateAssetIssueMethod()} instead. 
  public static final io.grpc.MethodDescriptor<org.tron.protos.Contract.ParticipateAssetIssueContract,
      org.tron.protos.Protocol.Transaction> METHOD_PARTICIPATE_ASSET_ISSUE = getParticipateAssetIssueMethod();

  private static volatile io.grpc.MethodDescriptor<org.tron.protos.Contract.ParticipateAssetIssueContract,
      org.tron.protos.Protocol.Transaction> getParticipateAssetIssueMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<org.tron.protos.Contract.ParticipateAssetIssueContract,
      org.tron.protos.Protocol.Transaction> getParticipateAssetIssueMethod() {
    io.grpc.MethodDescriptor<org.tron.protos.Contract.ParticipateAssetIssueContract, org.tron.protos.Protocol.Transaction> getParticipateAssetIssueMethod;
    if ((getParticipateAssetIssueMethod = WalletGrpc.getParticipateAssetIssueMethod) == null) {
      synchronized (WalletGrpc.class) {
        if ((getParticipateAssetIssueMethod = WalletGrpc.getParticipateAssetIssueMethod) == null) {
          WalletGrpc.getParticipateAssetIssueMethod = getParticipateAssetIssueMethod = 
              io.grpc.MethodDescriptor.<org.tron.protos.Contract.ParticipateAssetIssueContract, org.tron.protos.Protocol.Transaction>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "protocol.Wallet", "ParticipateAssetIssue"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.tron.protos.Contract.ParticipateAssetIssueContract.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.tron.protos.Protocol.Transaction.getDefaultInstance()))
                  .setSchemaDescriptor(new WalletMethodDescriptorSupplier("ParticipateAssetIssue"))
                  .build();
          }
        }
     }
     return getParticipateAssetIssueMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getFreezeBalanceMethod()} instead. 
  public static final io.grpc.MethodDescriptor<org.tron.protos.Contract.FreezeBalanceContract,
      org.tron.protos.Protocol.Transaction> METHOD_FREEZE_BALANCE = getFreezeBalanceMethod();

  private static volatile io.grpc.MethodDescriptor<org.tron.protos.Contract.FreezeBalanceContract,
      org.tron.protos.Protocol.Transaction> getFreezeBalanceMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<org.tron.protos.Contract.FreezeBalanceContract,
      org.tron.protos.Protocol.Transaction> getFreezeBalanceMethod() {
    io.grpc.MethodDescriptor<org.tron.protos.Contract.FreezeBalanceContract, org.tron.protos.Protocol.Transaction> getFreezeBalanceMethod;
    if ((getFreezeBalanceMethod = WalletGrpc.getFreezeBalanceMethod) == null) {
      synchronized (WalletGrpc.class) {
        if ((getFreezeBalanceMethod = WalletGrpc.getFreezeBalanceMethod) == null) {
          WalletGrpc.getFreezeBalanceMethod = getFreezeBalanceMethod = 
              io.grpc.MethodDescriptor.<org.tron.protos.Contract.FreezeBalanceContract, org.tron.protos.Protocol.Transaction>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "protocol.Wallet", "FreezeBalance"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.tron.protos.Contract.FreezeBalanceContract.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.tron.protos.Protocol.Transaction.getDefaultInstance()))
                  .setSchemaDescriptor(new WalletMethodDescriptorSupplier("FreezeBalance"))
                  .build();
          }
        }
     }
     return getFreezeBalanceMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getUnfreezeBalanceMethod()} instead. 
  public static final io.grpc.MethodDescriptor<org.tron.protos.Contract.UnfreezeBalanceContract,
      org.tron.protos.Protocol.Transaction> METHOD_UNFREEZE_BALANCE = getUnfreezeBalanceMethod();

  private static volatile io.grpc.MethodDescriptor<org.tron.protos.Contract.UnfreezeBalanceContract,
      org.tron.protos.Protocol.Transaction> getUnfreezeBalanceMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<org.tron.protos.Contract.UnfreezeBalanceContract,
      org.tron.protos.Protocol.Transaction> getUnfreezeBalanceMethod() {
    io.grpc.MethodDescriptor<org.tron.protos.Contract.UnfreezeBalanceContract, org.tron.protos.Protocol.Transaction> getUnfreezeBalanceMethod;
    if ((getUnfreezeBalanceMethod = WalletGrpc.getUnfreezeBalanceMethod) == null) {
      synchronized (WalletGrpc.class) {
        if ((getUnfreezeBalanceMethod = WalletGrpc.getUnfreezeBalanceMethod) == null) {
          WalletGrpc.getUnfreezeBalanceMethod = getUnfreezeBalanceMethod = 
              io.grpc.MethodDescriptor.<org.tron.protos.Contract.UnfreezeBalanceContract, org.tron.protos.Protocol.Transaction>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "protocol.Wallet", "UnfreezeBalance"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.tron.protos.Contract.UnfreezeBalanceContract.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.tron.protos.Protocol.Transaction.getDefaultInstance()))
                  .setSchemaDescriptor(new WalletMethodDescriptorSupplier("UnfreezeBalance"))
                  .build();
          }
        }
     }
     return getUnfreezeBalanceMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getUnfreezeAssetMethod()} instead. 
  public static final io.grpc.MethodDescriptor<org.tron.protos.Contract.UnfreezeAssetContract,
      org.tron.protos.Protocol.Transaction> METHOD_UNFREEZE_ASSET = getUnfreezeAssetMethod();

  private static volatile io.grpc.MethodDescriptor<org.tron.protos.Contract.UnfreezeAssetContract,
      org.tron.protos.Protocol.Transaction> getUnfreezeAssetMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<org.tron.protos.Contract.UnfreezeAssetContract,
      org.tron.protos.Protocol.Transaction> getUnfreezeAssetMethod() {
    io.grpc.MethodDescriptor<org.tron.protos.Contract.UnfreezeAssetContract, org.tron.protos.Protocol.Transaction> getUnfreezeAssetMethod;
    if ((getUnfreezeAssetMethod = WalletGrpc.getUnfreezeAssetMethod) == null) {
      synchronized (WalletGrpc.class) {
        if ((getUnfreezeAssetMethod = WalletGrpc.getUnfreezeAssetMethod) == null) {
          WalletGrpc.getUnfreezeAssetMethod = getUnfreezeAssetMethod = 
              io.grpc.MethodDescriptor.<org.tron.protos.Contract.UnfreezeAssetContract, org.tron.protos.Protocol.Transaction>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "protocol.Wallet", "UnfreezeAsset"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.tron.protos.Contract.UnfreezeAssetContract.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.tron.protos.Protocol.Transaction.getDefaultInstance()))
                  .setSchemaDescriptor(new WalletMethodDescriptorSupplier("UnfreezeAsset"))
                  .build();
          }
        }
     }
     return getUnfreezeAssetMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getWithdrawBalanceMethod()} instead. 
  public static final io.grpc.MethodDescriptor<org.tron.protos.Contract.WithdrawBalanceContract,
      org.tron.protos.Protocol.Transaction> METHOD_WITHDRAW_BALANCE = getWithdrawBalanceMethod();

  private static volatile io.grpc.MethodDescriptor<org.tron.protos.Contract.WithdrawBalanceContract,
      org.tron.protos.Protocol.Transaction> getWithdrawBalanceMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<org.tron.protos.Contract.WithdrawBalanceContract,
      org.tron.protos.Protocol.Transaction> getWithdrawBalanceMethod() {
    io.grpc.MethodDescriptor<org.tron.protos.Contract.WithdrawBalanceContract, org.tron.protos.Protocol.Transaction> getWithdrawBalanceMethod;
    if ((getWithdrawBalanceMethod = WalletGrpc.getWithdrawBalanceMethod) == null) {
      synchronized (WalletGrpc.class) {
        if ((getWithdrawBalanceMethod = WalletGrpc.getWithdrawBalanceMethod) == null) {
          WalletGrpc.getWithdrawBalanceMethod = getWithdrawBalanceMethod = 
              io.grpc.MethodDescriptor.<org.tron.protos.Contract.WithdrawBalanceContract, org.tron.protos.Protocol.Transaction>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "protocol.Wallet", "WithdrawBalance"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.tron.protos.Contract.WithdrawBalanceContract.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.tron.protos.Protocol.Transaction.getDefaultInstance()))
                  .setSchemaDescriptor(new WalletMethodDescriptorSupplier("WithdrawBalance"))
                  .build();
          }
        }
     }
     return getWithdrawBalanceMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getUpdateAssetMethod()} instead. 
  public static final io.grpc.MethodDescriptor<org.tron.protos.Contract.UpdateAssetContract,
      org.tron.protos.Protocol.Transaction> METHOD_UPDATE_ASSET = getUpdateAssetMethod();

  private static volatile io.grpc.MethodDescriptor<org.tron.protos.Contract.UpdateAssetContract,
      org.tron.protos.Protocol.Transaction> getUpdateAssetMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<org.tron.protos.Contract.UpdateAssetContract,
      org.tron.protos.Protocol.Transaction> getUpdateAssetMethod() {
    io.grpc.MethodDescriptor<org.tron.protos.Contract.UpdateAssetContract, org.tron.protos.Protocol.Transaction> getUpdateAssetMethod;
    if ((getUpdateAssetMethod = WalletGrpc.getUpdateAssetMethod) == null) {
      synchronized (WalletGrpc.class) {
        if ((getUpdateAssetMethod = WalletGrpc.getUpdateAssetMethod) == null) {
          WalletGrpc.getUpdateAssetMethod = getUpdateAssetMethod = 
              io.grpc.MethodDescriptor.<org.tron.protos.Contract.UpdateAssetContract, org.tron.protos.Protocol.Transaction>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "protocol.Wallet", "UpdateAsset"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.tron.protos.Contract.UpdateAssetContract.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.tron.protos.Protocol.Transaction.getDefaultInstance()))
                  .setSchemaDescriptor(new WalletMethodDescriptorSupplier("UpdateAsset"))
                  .build();
          }
        }
     }
     return getUpdateAssetMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getListNodesMethod()} instead. 
  public static final io.grpc.MethodDescriptor<org.tron.api.GrpcAPI.EmptyMessage,
      org.tron.api.GrpcAPI.NodeList> METHOD_LIST_NODES = getListNodesMethod();

  private static volatile io.grpc.MethodDescriptor<org.tron.api.GrpcAPI.EmptyMessage,
      org.tron.api.GrpcAPI.NodeList> getListNodesMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<org.tron.api.GrpcAPI.EmptyMessage,
      org.tron.api.GrpcAPI.NodeList> getListNodesMethod() {
    io.grpc.MethodDescriptor<org.tron.api.GrpcAPI.EmptyMessage, org.tron.api.GrpcAPI.NodeList> getListNodesMethod;
    if ((getListNodesMethod = WalletGrpc.getListNodesMethod) == null) {
      synchronized (WalletGrpc.class) {
        if ((getListNodesMethod = WalletGrpc.getListNodesMethod) == null) {
          WalletGrpc.getListNodesMethod = getListNodesMethod = 
              io.grpc.MethodDescriptor.<org.tron.api.GrpcAPI.EmptyMessage, org.tron.api.GrpcAPI.NodeList>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "protocol.Wallet", "ListNodes"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.tron.api.GrpcAPI.EmptyMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.tron.api.GrpcAPI.NodeList.getDefaultInstance()))
                  .setSchemaDescriptor(new WalletMethodDescriptorSupplier("ListNodes"))
                  .build();
          }
        }
     }
     return getListNodesMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getGetAssetIssueByAccountMethod()} instead. 
  public static final io.grpc.MethodDescriptor<org.tron.protos.Protocol.Account,
      org.tron.api.GrpcAPI.AssetIssueList> METHOD_GET_ASSET_ISSUE_BY_ACCOUNT = getGetAssetIssueByAccountMethod();

  private static volatile io.grpc.MethodDescriptor<org.tron.protos.Protocol.Account,
      org.tron.api.GrpcAPI.AssetIssueList> getGetAssetIssueByAccountMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<org.tron.protos.Protocol.Account,
      org.tron.api.GrpcAPI.AssetIssueList> getGetAssetIssueByAccountMethod() {
    io.grpc.MethodDescriptor<org.tron.protos.Protocol.Account, org.tron.api.GrpcAPI.AssetIssueList> getGetAssetIssueByAccountMethod;
    if ((getGetAssetIssueByAccountMethod = WalletGrpc.getGetAssetIssueByAccountMethod) == null) {
      synchronized (WalletGrpc.class) {
        if ((getGetAssetIssueByAccountMethod = WalletGrpc.getGetAssetIssueByAccountMethod) == null) {
          WalletGrpc.getGetAssetIssueByAccountMethod = getGetAssetIssueByAccountMethod = 
              io.grpc.MethodDescriptor.<org.tron.protos.Protocol.Account, org.tron.api.GrpcAPI.AssetIssueList>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "protocol.Wallet", "GetAssetIssueByAccount"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.tron.protos.Protocol.Account.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.tron.api.GrpcAPI.AssetIssueList.getDefaultInstance()))
                  .setSchemaDescriptor(new WalletMethodDescriptorSupplier("GetAssetIssueByAccount"))
                  .build();
          }
        }
     }
     return getGetAssetIssueByAccountMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getGetAccountNetMethod()} instead. 
  public static final io.grpc.MethodDescriptor<org.tron.protos.Protocol.Account,
      org.tron.api.GrpcAPI.AccountNetMessage> METHOD_GET_ACCOUNT_NET = getGetAccountNetMethod();

  private static volatile io.grpc.MethodDescriptor<org.tron.protos.Protocol.Account,
      org.tron.api.GrpcAPI.AccountNetMessage> getGetAccountNetMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<org.tron.protos.Protocol.Account,
      org.tron.api.GrpcAPI.AccountNetMessage> getGetAccountNetMethod() {
    io.grpc.MethodDescriptor<org.tron.protos.Protocol.Account, org.tron.api.GrpcAPI.AccountNetMessage> getGetAccountNetMethod;
    if ((getGetAccountNetMethod = WalletGrpc.getGetAccountNetMethod) == null) {
      synchronized (WalletGrpc.class) {
        if ((getGetAccountNetMethod = WalletGrpc.getGetAccountNetMethod) == null) {
          WalletGrpc.getGetAccountNetMethod = getGetAccountNetMethod = 
              io.grpc.MethodDescriptor.<org.tron.protos.Protocol.Account, org.tron.api.GrpcAPI.AccountNetMessage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "protocol.Wallet", "GetAccountNet"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.tron.protos.Protocol.Account.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.tron.api.GrpcAPI.AccountNetMessage.getDefaultInstance()))
                  .setSchemaDescriptor(new WalletMethodDescriptorSupplier("GetAccountNet"))
                  .build();
          }
        }
     }
     return getGetAccountNetMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getGetAssetIssueByNameMethod()} instead. 
  public static final io.grpc.MethodDescriptor<org.tron.api.GrpcAPI.BytesMessage,
      org.tron.protos.Contract.AssetIssueContract> METHOD_GET_ASSET_ISSUE_BY_NAME = getGetAssetIssueByNameMethod();

  private static volatile io.grpc.MethodDescriptor<org.tron.api.GrpcAPI.BytesMessage,
      org.tron.protos.Contract.AssetIssueContract> getGetAssetIssueByNameMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<org.tron.api.GrpcAPI.BytesMessage,
      org.tron.protos.Contract.AssetIssueContract> getGetAssetIssueByNameMethod() {
    io.grpc.MethodDescriptor<org.tron.api.GrpcAPI.BytesMessage, org.tron.protos.Contract.AssetIssueContract> getGetAssetIssueByNameMethod;
    if ((getGetAssetIssueByNameMethod = WalletGrpc.getGetAssetIssueByNameMethod) == null) {
      synchronized (WalletGrpc.class) {
        if ((getGetAssetIssueByNameMethod = WalletGrpc.getGetAssetIssueByNameMethod) == null) {
          WalletGrpc.getGetAssetIssueByNameMethod = getGetAssetIssueByNameMethod = 
              io.grpc.MethodDescriptor.<org.tron.api.GrpcAPI.BytesMessage, org.tron.protos.Contract.AssetIssueContract>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "protocol.Wallet", "GetAssetIssueByName"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.tron.api.GrpcAPI.BytesMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.tron.protos.Contract.AssetIssueContract.getDefaultInstance()))
                  .setSchemaDescriptor(new WalletMethodDescriptorSupplier("GetAssetIssueByName"))
                  .build();
          }
        }
     }
     return getGetAssetIssueByNameMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getGetNowBlockMethod()} instead. 
  public static final io.grpc.MethodDescriptor<org.tron.api.GrpcAPI.EmptyMessage,
      org.tron.protos.Protocol.Block> METHOD_GET_NOW_BLOCK = getGetNowBlockMethod();

  private static volatile io.grpc.MethodDescriptor<org.tron.api.GrpcAPI.EmptyMessage,
      org.tron.protos.Protocol.Block> getGetNowBlockMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<org.tron.api.GrpcAPI.EmptyMessage,
      org.tron.protos.Protocol.Block> getGetNowBlockMethod() {
    io.grpc.MethodDescriptor<org.tron.api.GrpcAPI.EmptyMessage, org.tron.protos.Protocol.Block> getGetNowBlockMethod;
    if ((getGetNowBlockMethod = WalletGrpc.getGetNowBlockMethod) == null) {
      synchronized (WalletGrpc.class) {
        if ((getGetNowBlockMethod = WalletGrpc.getGetNowBlockMethod) == null) {
          WalletGrpc.getGetNowBlockMethod = getGetNowBlockMethod = 
              io.grpc.MethodDescriptor.<org.tron.api.GrpcAPI.EmptyMessage, org.tron.protos.Protocol.Block>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "protocol.Wallet", "GetNowBlock"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.tron.api.GrpcAPI.EmptyMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.tron.protos.Protocol.Block.getDefaultInstance()))
                  .setSchemaDescriptor(new WalletMethodDescriptorSupplier("GetNowBlock"))
                  .build();
          }
        }
     }
     return getGetNowBlockMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getGetBlockByNumMethod()} instead. 
  public static final io.grpc.MethodDescriptor<org.tron.api.GrpcAPI.NumberMessage,
      org.tron.protos.Protocol.Block> METHOD_GET_BLOCK_BY_NUM = getGetBlockByNumMethod();

  private static volatile io.grpc.MethodDescriptor<org.tron.api.GrpcAPI.NumberMessage,
      org.tron.protos.Protocol.Block> getGetBlockByNumMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<org.tron.api.GrpcAPI.NumberMessage,
      org.tron.protos.Protocol.Block> getGetBlockByNumMethod() {
    io.grpc.MethodDescriptor<org.tron.api.GrpcAPI.NumberMessage, org.tron.protos.Protocol.Block> getGetBlockByNumMethod;
    if ((getGetBlockByNumMethod = WalletGrpc.getGetBlockByNumMethod) == null) {
      synchronized (WalletGrpc.class) {
        if ((getGetBlockByNumMethod = WalletGrpc.getGetBlockByNumMethod) == null) {
          WalletGrpc.getGetBlockByNumMethod = getGetBlockByNumMethod = 
              io.grpc.MethodDescriptor.<org.tron.api.GrpcAPI.NumberMessage, org.tron.protos.Protocol.Block>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "protocol.Wallet", "GetBlockByNum"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.tron.api.GrpcAPI.NumberMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.tron.protos.Protocol.Block.getDefaultInstance()))
                  .setSchemaDescriptor(new WalletMethodDescriptorSupplier("GetBlockByNum"))
                  .build();
          }
        }
     }
     return getGetBlockByNumMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getGetBlockByIdMethod()} instead. 
  public static final io.grpc.MethodDescriptor<org.tron.api.GrpcAPI.BytesMessage,
      org.tron.protos.Protocol.Block> METHOD_GET_BLOCK_BY_ID = getGetBlockByIdMethod();

  private static volatile io.grpc.MethodDescriptor<org.tron.api.GrpcAPI.BytesMessage,
      org.tron.protos.Protocol.Block> getGetBlockByIdMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<org.tron.api.GrpcAPI.BytesMessage,
      org.tron.protos.Protocol.Block> getGetBlockByIdMethod() {
    io.grpc.MethodDescriptor<org.tron.api.GrpcAPI.BytesMessage, org.tron.protos.Protocol.Block> getGetBlockByIdMethod;
    if ((getGetBlockByIdMethod = WalletGrpc.getGetBlockByIdMethod) == null) {
      synchronized (WalletGrpc.class) {
        if ((getGetBlockByIdMethod = WalletGrpc.getGetBlockByIdMethod) == null) {
          WalletGrpc.getGetBlockByIdMethod = getGetBlockByIdMethod = 
              io.grpc.MethodDescriptor.<org.tron.api.GrpcAPI.BytesMessage, org.tron.protos.Protocol.Block>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "protocol.Wallet", "GetBlockById"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.tron.api.GrpcAPI.BytesMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.tron.protos.Protocol.Block.getDefaultInstance()))
                  .setSchemaDescriptor(new WalletMethodDescriptorSupplier("GetBlockById"))
                  .build();
          }
        }
     }
     return getGetBlockByIdMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getGetBlockByLimitNextMethod()} instead. 
  public static final io.grpc.MethodDescriptor<org.tron.api.GrpcAPI.BlockLimit,
      org.tron.api.GrpcAPI.BlockList> METHOD_GET_BLOCK_BY_LIMIT_NEXT = getGetBlockByLimitNextMethod();

  private static volatile io.grpc.MethodDescriptor<org.tron.api.GrpcAPI.BlockLimit,
      org.tron.api.GrpcAPI.BlockList> getGetBlockByLimitNextMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<org.tron.api.GrpcAPI.BlockLimit,
      org.tron.api.GrpcAPI.BlockList> getGetBlockByLimitNextMethod() {
    io.grpc.MethodDescriptor<org.tron.api.GrpcAPI.BlockLimit, org.tron.api.GrpcAPI.BlockList> getGetBlockByLimitNextMethod;
    if ((getGetBlockByLimitNextMethod = WalletGrpc.getGetBlockByLimitNextMethod) == null) {
      synchronized (WalletGrpc.class) {
        if ((getGetBlockByLimitNextMethod = WalletGrpc.getGetBlockByLimitNextMethod) == null) {
          WalletGrpc.getGetBlockByLimitNextMethod = getGetBlockByLimitNextMethod = 
              io.grpc.MethodDescriptor.<org.tron.api.GrpcAPI.BlockLimit, org.tron.api.GrpcAPI.BlockList>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "protocol.Wallet", "GetBlockByLimitNext"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.tron.api.GrpcAPI.BlockLimit.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.tron.api.GrpcAPI.BlockList.getDefaultInstance()))
                  .setSchemaDescriptor(new WalletMethodDescriptorSupplier("GetBlockByLimitNext"))
                  .build();
          }
        }
     }
     return getGetBlockByLimitNextMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getGetBlockByLatestNumMethod()} instead. 
  public static final io.grpc.MethodDescriptor<org.tron.api.GrpcAPI.NumberMessage,
      org.tron.api.GrpcAPI.BlockList> METHOD_GET_BLOCK_BY_LATEST_NUM = getGetBlockByLatestNumMethod();

  private static volatile io.grpc.MethodDescriptor<org.tron.api.GrpcAPI.NumberMessage,
      org.tron.api.GrpcAPI.BlockList> getGetBlockByLatestNumMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<org.tron.api.GrpcAPI.NumberMessage,
      org.tron.api.GrpcAPI.BlockList> getGetBlockByLatestNumMethod() {
    io.grpc.MethodDescriptor<org.tron.api.GrpcAPI.NumberMessage, org.tron.api.GrpcAPI.BlockList> getGetBlockByLatestNumMethod;
    if ((getGetBlockByLatestNumMethod = WalletGrpc.getGetBlockByLatestNumMethod) == null) {
      synchronized (WalletGrpc.class) {
        if ((getGetBlockByLatestNumMethod = WalletGrpc.getGetBlockByLatestNumMethod) == null) {
          WalletGrpc.getGetBlockByLatestNumMethod = getGetBlockByLatestNumMethod = 
              io.grpc.MethodDescriptor.<org.tron.api.GrpcAPI.NumberMessage, org.tron.api.GrpcAPI.BlockList>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "protocol.Wallet", "GetBlockByLatestNum"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.tron.api.GrpcAPI.NumberMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.tron.api.GrpcAPI.BlockList.getDefaultInstance()))
                  .setSchemaDescriptor(new WalletMethodDescriptorSupplier("GetBlockByLatestNum"))
                  .build();
          }
        }
     }
     return getGetBlockByLatestNumMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getGetTransactionByIdMethod()} instead. 
  public static final io.grpc.MethodDescriptor<org.tron.api.GrpcAPI.BytesMessage,
      org.tron.protos.Protocol.Transaction> METHOD_GET_TRANSACTION_BY_ID = getGetTransactionByIdMethod();

  private static volatile io.grpc.MethodDescriptor<org.tron.api.GrpcAPI.BytesMessage,
      org.tron.protos.Protocol.Transaction> getGetTransactionByIdMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<org.tron.api.GrpcAPI.BytesMessage,
      org.tron.protos.Protocol.Transaction> getGetTransactionByIdMethod() {
    io.grpc.MethodDescriptor<org.tron.api.GrpcAPI.BytesMessage, org.tron.protos.Protocol.Transaction> getGetTransactionByIdMethod;
    if ((getGetTransactionByIdMethod = WalletGrpc.getGetTransactionByIdMethod) == null) {
      synchronized (WalletGrpc.class) {
        if ((getGetTransactionByIdMethod = WalletGrpc.getGetTransactionByIdMethod) == null) {
          WalletGrpc.getGetTransactionByIdMethod = getGetTransactionByIdMethod = 
              io.grpc.MethodDescriptor.<org.tron.api.GrpcAPI.BytesMessage, org.tron.protos.Protocol.Transaction>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "protocol.Wallet", "GetTransactionById"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.tron.api.GrpcAPI.BytesMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.tron.protos.Protocol.Transaction.getDefaultInstance()))
                  .setSchemaDescriptor(new WalletMethodDescriptorSupplier("GetTransactionById"))
                  .build();
          }
        }
     }
     return getGetTransactionByIdMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getListWitnessesMethod()} instead. 
  public static final io.grpc.MethodDescriptor<org.tron.api.GrpcAPI.EmptyMessage,
      org.tron.api.GrpcAPI.WitnessList> METHOD_LIST_WITNESSES = getListWitnessesMethod();

  private static volatile io.grpc.MethodDescriptor<org.tron.api.GrpcAPI.EmptyMessage,
      org.tron.api.GrpcAPI.WitnessList> getListWitnessesMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<org.tron.api.GrpcAPI.EmptyMessage,
      org.tron.api.GrpcAPI.WitnessList> getListWitnessesMethod() {
    io.grpc.MethodDescriptor<org.tron.api.GrpcAPI.EmptyMessage, org.tron.api.GrpcAPI.WitnessList> getListWitnessesMethod;
    if ((getListWitnessesMethod = WalletGrpc.getListWitnessesMethod) == null) {
      synchronized (WalletGrpc.class) {
        if ((getListWitnessesMethod = WalletGrpc.getListWitnessesMethod) == null) {
          WalletGrpc.getListWitnessesMethod = getListWitnessesMethod = 
              io.grpc.MethodDescriptor.<org.tron.api.GrpcAPI.EmptyMessage, org.tron.api.GrpcAPI.WitnessList>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "protocol.Wallet", "ListWitnesses"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.tron.api.GrpcAPI.EmptyMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.tron.api.GrpcAPI.WitnessList.getDefaultInstance()))
                  .setSchemaDescriptor(new WalletMethodDescriptorSupplier("ListWitnesses"))
                  .build();
          }
        }
     }
     return getListWitnessesMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getGetAssetIssueListMethod()} instead. 
  public static final io.grpc.MethodDescriptor<org.tron.api.GrpcAPI.EmptyMessage,
      org.tron.api.GrpcAPI.AssetIssueList> METHOD_GET_ASSET_ISSUE_LIST = getGetAssetIssueListMethod();

  private static volatile io.grpc.MethodDescriptor<org.tron.api.GrpcAPI.EmptyMessage,
      org.tron.api.GrpcAPI.AssetIssueList> getGetAssetIssueListMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<org.tron.api.GrpcAPI.EmptyMessage,
      org.tron.api.GrpcAPI.AssetIssueList> getGetAssetIssueListMethod() {
    io.grpc.MethodDescriptor<org.tron.api.GrpcAPI.EmptyMessage, org.tron.api.GrpcAPI.AssetIssueList> getGetAssetIssueListMethod;
    if ((getGetAssetIssueListMethod = WalletGrpc.getGetAssetIssueListMethod) == null) {
      synchronized (WalletGrpc.class) {
        if ((getGetAssetIssueListMethod = WalletGrpc.getGetAssetIssueListMethod) == null) {
          WalletGrpc.getGetAssetIssueListMethod = getGetAssetIssueListMethod = 
              io.grpc.MethodDescriptor.<org.tron.api.GrpcAPI.EmptyMessage, org.tron.api.GrpcAPI.AssetIssueList>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "protocol.Wallet", "GetAssetIssueList"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.tron.api.GrpcAPI.EmptyMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.tron.api.GrpcAPI.AssetIssueList.getDefaultInstance()))
                  .setSchemaDescriptor(new WalletMethodDescriptorSupplier("GetAssetIssueList"))
                  .build();
          }
        }
     }
     return getGetAssetIssueListMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getTotalTransactionMethod()} instead. 
  public static final io.grpc.MethodDescriptor<org.tron.api.GrpcAPI.EmptyMessage,
      org.tron.api.GrpcAPI.NumberMessage> METHOD_TOTAL_TRANSACTION = getTotalTransactionMethod();

  private static volatile io.grpc.MethodDescriptor<org.tron.api.GrpcAPI.EmptyMessage,
      org.tron.api.GrpcAPI.NumberMessage> getTotalTransactionMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<org.tron.api.GrpcAPI.EmptyMessage,
      org.tron.api.GrpcAPI.NumberMessage> getTotalTransactionMethod() {
    io.grpc.MethodDescriptor<org.tron.api.GrpcAPI.EmptyMessage, org.tron.api.GrpcAPI.NumberMessage> getTotalTransactionMethod;
    if ((getTotalTransactionMethod = WalletGrpc.getTotalTransactionMethod) == null) {
      synchronized (WalletGrpc.class) {
        if ((getTotalTransactionMethod = WalletGrpc.getTotalTransactionMethod) == null) {
          WalletGrpc.getTotalTransactionMethod = getTotalTransactionMethod = 
              io.grpc.MethodDescriptor.<org.tron.api.GrpcAPI.EmptyMessage, org.tron.api.GrpcAPI.NumberMessage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "protocol.Wallet", "TotalTransaction"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.tron.api.GrpcAPI.EmptyMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.tron.api.GrpcAPI.NumberMessage.getDefaultInstance()))
                  .setSchemaDescriptor(new WalletMethodDescriptorSupplier("TotalTransaction"))
                  .build();
          }
        }
     }
     return getTotalTransactionMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getGetNextMaintenanceTimeMethod()} instead. 
  public static final io.grpc.MethodDescriptor<org.tron.api.GrpcAPI.EmptyMessage,
      org.tron.api.GrpcAPI.NumberMessage> METHOD_GET_NEXT_MAINTENANCE_TIME = getGetNextMaintenanceTimeMethod();

  private static volatile io.grpc.MethodDescriptor<org.tron.api.GrpcAPI.EmptyMessage,
      org.tron.api.GrpcAPI.NumberMessage> getGetNextMaintenanceTimeMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<org.tron.api.GrpcAPI.EmptyMessage,
      org.tron.api.GrpcAPI.NumberMessage> getGetNextMaintenanceTimeMethod() {
    io.grpc.MethodDescriptor<org.tron.api.GrpcAPI.EmptyMessage, org.tron.api.GrpcAPI.NumberMessage> getGetNextMaintenanceTimeMethod;
    if ((getGetNextMaintenanceTimeMethod = WalletGrpc.getGetNextMaintenanceTimeMethod) == null) {
      synchronized (WalletGrpc.class) {
        if ((getGetNextMaintenanceTimeMethod = WalletGrpc.getGetNextMaintenanceTimeMethod) == null) {
          WalletGrpc.getGetNextMaintenanceTimeMethod = getGetNextMaintenanceTimeMethod = 
              io.grpc.MethodDescriptor.<org.tron.api.GrpcAPI.EmptyMessage, org.tron.api.GrpcAPI.NumberMessage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "protocol.Wallet", "GetNextMaintenanceTime"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.tron.api.GrpcAPI.EmptyMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.tron.api.GrpcAPI.NumberMessage.getDefaultInstance()))
                  .setSchemaDescriptor(new WalletMethodDescriptorSupplier("GetNextMaintenanceTime"))
                  .build();
          }
        }
     }
     return getGetNextMaintenanceTimeMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static WalletStub newStub(io.grpc.Channel channel) {
    return new WalletStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static WalletBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new WalletBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static WalletFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new WalletFutureStub(channel);
  }

  /**
   */
  public static abstract class WalletImplBase implements io.grpc.BindableService {

    /**
     */
    public void getAccount(org.tron.protos.Protocol.Account request,
        io.grpc.stub.StreamObserver<org.tron.protos.Protocol.Account> responseObserver) {
      asyncUnimplementedUnaryCall(getGetAccountMethod(), responseObserver);
    }

    /**
     */
    public void createTransaction(org.tron.protos.Contract.TransferContract request,
        io.grpc.stub.StreamObserver<org.tron.protos.Protocol.Transaction> responseObserver) {
      asyncUnimplementedUnaryCall(getCreateTransactionMethod(), responseObserver);
    }

    /**
     */
    public void broadcastTransaction(org.tron.protos.Protocol.Transaction request,
        io.grpc.stub.StreamObserver<org.tron.api.GrpcAPI.Return> responseObserver) {
      asyncUnimplementedUnaryCall(getBroadcastTransactionMethod(), responseObserver);
    }

    /**
     */
    public void updateAccount(org.tron.protos.Contract.AccountUpdateContract request,
        io.grpc.stub.StreamObserver<org.tron.protos.Protocol.Transaction> responseObserver) {
      asyncUnimplementedUnaryCall(getUpdateAccountMethod(), responseObserver);
    }

    /**
     */
    public void voteWitnessAccount(org.tron.protos.Contract.VoteWitnessContract request,
        io.grpc.stub.StreamObserver<org.tron.protos.Protocol.Transaction> responseObserver) {
      asyncUnimplementedUnaryCall(getVoteWitnessAccountMethod(), responseObserver);
    }

    /**
     */
    public void createAssetIssue(org.tron.protos.Contract.AssetIssueContract request,
        io.grpc.stub.StreamObserver<org.tron.protos.Protocol.Transaction> responseObserver) {
      asyncUnimplementedUnaryCall(getCreateAssetIssueMethod(), responseObserver);
    }

    /**
     */
    public void updateWitness(org.tron.protos.Contract.WitnessUpdateContract request,
        io.grpc.stub.StreamObserver<org.tron.protos.Protocol.Transaction> responseObserver) {
      asyncUnimplementedUnaryCall(getUpdateWitnessMethod(), responseObserver);
    }

    /**
     */
    public void createWitness(org.tron.protos.Contract.WitnessCreateContract request,
        io.grpc.stub.StreamObserver<org.tron.protos.Protocol.Transaction> responseObserver) {
      asyncUnimplementedUnaryCall(getCreateWitnessMethod(), responseObserver);
    }

    /**
     */
    public void transferAsset(org.tron.protos.Contract.TransferAssetContract request,
        io.grpc.stub.StreamObserver<org.tron.protos.Protocol.Transaction> responseObserver) {
      asyncUnimplementedUnaryCall(getTransferAssetMethod(), responseObserver);
    }

    /**
     */
    public void participateAssetIssue(org.tron.protos.Contract.ParticipateAssetIssueContract request,
        io.grpc.stub.StreamObserver<org.tron.protos.Protocol.Transaction> responseObserver) {
      asyncUnimplementedUnaryCall(getParticipateAssetIssueMethod(), responseObserver);
    }

    /**
     */
    public void freezeBalance(org.tron.protos.Contract.FreezeBalanceContract request,
        io.grpc.stub.StreamObserver<org.tron.protos.Protocol.Transaction> responseObserver) {
      asyncUnimplementedUnaryCall(getFreezeBalanceMethod(), responseObserver);
    }

    /**
     */
    public void unfreezeBalance(org.tron.protos.Contract.UnfreezeBalanceContract request,
        io.grpc.stub.StreamObserver<org.tron.protos.Protocol.Transaction> responseObserver) {
      asyncUnimplementedUnaryCall(getUnfreezeBalanceMethod(), responseObserver);
    }

    /**
     */
    public void unfreezeAsset(org.tron.protos.Contract.UnfreezeAssetContract request,
        io.grpc.stub.StreamObserver<org.tron.protos.Protocol.Transaction> responseObserver) {
      asyncUnimplementedUnaryCall(getUnfreezeAssetMethod(), responseObserver);
    }

    /**
     */
    public void withdrawBalance(org.tron.protos.Contract.WithdrawBalanceContract request,
        io.grpc.stub.StreamObserver<org.tron.protos.Protocol.Transaction> responseObserver) {
      asyncUnimplementedUnaryCall(getWithdrawBalanceMethod(), responseObserver);
    }

    /**
     */
    public void updateAsset(org.tron.protos.Contract.UpdateAssetContract request,
        io.grpc.stub.StreamObserver<org.tron.protos.Protocol.Transaction> responseObserver) {
      asyncUnimplementedUnaryCall(getUpdateAssetMethod(), responseObserver);
    }

    /**
     */
    public void listNodes(org.tron.api.GrpcAPI.EmptyMessage request,
        io.grpc.stub.StreamObserver<org.tron.api.GrpcAPI.NodeList> responseObserver) {
      asyncUnimplementedUnaryCall(getListNodesMethod(), responseObserver);
    }

    /**
     */
    public void getAssetIssueByAccount(org.tron.protos.Protocol.Account request,
        io.grpc.stub.StreamObserver<org.tron.api.GrpcAPI.AssetIssueList> responseObserver) {
      asyncUnimplementedUnaryCall(getGetAssetIssueByAccountMethod(), responseObserver);
    }

    /**
     */
    public void getAccountNet(org.tron.protos.Protocol.Account request,
        io.grpc.stub.StreamObserver<org.tron.api.GrpcAPI.AccountNetMessage> responseObserver) {
      asyncUnimplementedUnaryCall(getGetAccountNetMethod(), responseObserver);
    }

    /**
     */
    public void getAssetIssueByName(org.tron.api.GrpcAPI.BytesMessage request,
        io.grpc.stub.StreamObserver<org.tron.protos.Contract.AssetIssueContract> responseObserver) {
      asyncUnimplementedUnaryCall(getGetAssetIssueByNameMethod(), responseObserver);
    }

    /**
     */
    public void getNowBlock(org.tron.api.GrpcAPI.EmptyMessage request,
        io.grpc.stub.StreamObserver<org.tron.protos.Protocol.Block> responseObserver) {
      asyncUnimplementedUnaryCall(getGetNowBlockMethod(), responseObserver);
    }

    /**
     */
    public void getBlockByNum(org.tron.api.GrpcAPI.NumberMessage request,
        io.grpc.stub.StreamObserver<org.tron.protos.Protocol.Block> responseObserver) {
      asyncUnimplementedUnaryCall(getGetBlockByNumMethod(), responseObserver);
    }

    /**
     */
    public void getBlockById(org.tron.api.GrpcAPI.BytesMessage request,
        io.grpc.stub.StreamObserver<org.tron.protos.Protocol.Block> responseObserver) {
      asyncUnimplementedUnaryCall(getGetBlockByIdMethod(), responseObserver);
    }

    /**
     */
    public void getBlockByLimitNext(org.tron.api.GrpcAPI.BlockLimit request,
        io.grpc.stub.StreamObserver<org.tron.api.GrpcAPI.BlockList> responseObserver) {
      asyncUnimplementedUnaryCall(getGetBlockByLimitNextMethod(), responseObserver);
    }

    /**
     */
    public void getBlockByLatestNum(org.tron.api.GrpcAPI.NumberMessage request,
        io.grpc.stub.StreamObserver<org.tron.api.GrpcAPI.BlockList> responseObserver) {
      asyncUnimplementedUnaryCall(getGetBlockByLatestNumMethod(), responseObserver);
    }

    /**
     */
    public void getTransactionById(org.tron.api.GrpcAPI.BytesMessage request,
        io.grpc.stub.StreamObserver<org.tron.protos.Protocol.Transaction> responseObserver) {
      asyncUnimplementedUnaryCall(getGetTransactionByIdMethod(), responseObserver);
    }

    /**
     */
    public void listWitnesses(org.tron.api.GrpcAPI.EmptyMessage request,
        io.grpc.stub.StreamObserver<org.tron.api.GrpcAPI.WitnessList> responseObserver) {
      asyncUnimplementedUnaryCall(getListWitnessesMethod(), responseObserver);
    }

    /**
     */
    public void getAssetIssueList(org.tron.api.GrpcAPI.EmptyMessage request,
        io.grpc.stub.StreamObserver<org.tron.api.GrpcAPI.AssetIssueList> responseObserver) {
      asyncUnimplementedUnaryCall(getGetAssetIssueListMethod(), responseObserver);
    }

    /**
     */
    public void totalTransaction(org.tron.api.GrpcAPI.EmptyMessage request,
        io.grpc.stub.StreamObserver<org.tron.api.GrpcAPI.NumberMessage> responseObserver) {
      asyncUnimplementedUnaryCall(getTotalTransactionMethod(), responseObserver);
    }

    /**
     */
    public void getNextMaintenanceTime(org.tron.api.GrpcAPI.EmptyMessage request,
        io.grpc.stub.StreamObserver<org.tron.api.GrpcAPI.NumberMessage> responseObserver) {
      asyncUnimplementedUnaryCall(getGetNextMaintenanceTimeMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetAccountMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                org.tron.protos.Protocol.Account,
                org.tron.protos.Protocol.Account>(
                  this, METHODID_GET_ACCOUNT)))
          .addMethod(
            getCreateTransactionMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                org.tron.protos.Contract.TransferContract,
                org.tron.protos.Protocol.Transaction>(
                  this, METHODID_CREATE_TRANSACTION)))
          .addMethod(
            getBroadcastTransactionMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                org.tron.protos.Protocol.Transaction,
                org.tron.api.GrpcAPI.Return>(
                  this, METHODID_BROADCAST_TRANSACTION)))
          .addMethod(
            getUpdateAccountMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                org.tron.protos.Contract.AccountUpdateContract,
                org.tron.protos.Protocol.Transaction>(
                  this, METHODID_UPDATE_ACCOUNT)))
          .addMethod(
            getVoteWitnessAccountMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                org.tron.protos.Contract.VoteWitnessContract,
                org.tron.protos.Protocol.Transaction>(
                  this, METHODID_VOTE_WITNESS_ACCOUNT)))
          .addMethod(
            getCreateAssetIssueMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                org.tron.protos.Contract.AssetIssueContract,
                org.tron.protos.Protocol.Transaction>(
                  this, METHODID_CREATE_ASSET_ISSUE)))
          .addMethod(
            getUpdateWitnessMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                org.tron.protos.Contract.WitnessUpdateContract,
                org.tron.protos.Protocol.Transaction>(
                  this, METHODID_UPDATE_WITNESS)))
          .addMethod(
            getCreateWitnessMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                org.tron.protos.Contract.WitnessCreateContract,
                org.tron.protos.Protocol.Transaction>(
                  this, METHODID_CREATE_WITNESS)))
          .addMethod(
            getTransferAssetMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                org.tron.protos.Contract.TransferAssetContract,
                org.tron.protos.Protocol.Transaction>(
                  this, METHODID_TRANSFER_ASSET)))
          .addMethod(
            getParticipateAssetIssueMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                org.tron.protos.Contract.ParticipateAssetIssueContract,
                org.tron.protos.Protocol.Transaction>(
                  this, METHODID_PARTICIPATE_ASSET_ISSUE)))
          .addMethod(
            getFreezeBalanceMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                org.tron.protos.Contract.FreezeBalanceContract,
                org.tron.protos.Protocol.Transaction>(
                  this, METHODID_FREEZE_BALANCE)))
          .addMethod(
            getUnfreezeBalanceMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                org.tron.protos.Contract.UnfreezeBalanceContract,
                org.tron.protos.Protocol.Transaction>(
                  this, METHODID_UNFREEZE_BALANCE)))
          .addMethod(
            getUnfreezeAssetMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                org.tron.protos.Contract.UnfreezeAssetContract,
                org.tron.protos.Protocol.Transaction>(
                  this, METHODID_UNFREEZE_ASSET)))
          .addMethod(
            getWithdrawBalanceMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                org.tron.protos.Contract.WithdrawBalanceContract,
                org.tron.protos.Protocol.Transaction>(
                  this, METHODID_WITHDRAW_BALANCE)))
          .addMethod(
            getUpdateAssetMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                org.tron.protos.Contract.UpdateAssetContract,
                org.tron.protos.Protocol.Transaction>(
                  this, METHODID_UPDATE_ASSET)))
          .addMethod(
            getListNodesMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                org.tron.api.GrpcAPI.EmptyMessage,
                org.tron.api.GrpcAPI.NodeList>(
                  this, METHODID_LIST_NODES)))
          .addMethod(
            getGetAssetIssueByAccountMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                org.tron.protos.Protocol.Account,
                org.tron.api.GrpcAPI.AssetIssueList>(
                  this, METHODID_GET_ASSET_ISSUE_BY_ACCOUNT)))
          .addMethod(
            getGetAccountNetMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                org.tron.protos.Protocol.Account,
                org.tron.api.GrpcAPI.AccountNetMessage>(
                  this, METHODID_GET_ACCOUNT_NET)))
          .addMethod(
            getGetAssetIssueByNameMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                org.tron.api.GrpcAPI.BytesMessage,
                org.tron.protos.Contract.AssetIssueContract>(
                  this, METHODID_GET_ASSET_ISSUE_BY_NAME)))
          .addMethod(
            getGetNowBlockMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                org.tron.api.GrpcAPI.EmptyMessage,
                org.tron.protos.Protocol.Block>(
                  this, METHODID_GET_NOW_BLOCK)))
          .addMethod(
            getGetBlockByNumMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                org.tron.api.GrpcAPI.NumberMessage,
                org.tron.protos.Protocol.Block>(
                  this, METHODID_GET_BLOCK_BY_NUM)))
          .addMethod(
            getGetBlockByIdMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                org.tron.api.GrpcAPI.BytesMessage,
                org.tron.protos.Protocol.Block>(
                  this, METHODID_GET_BLOCK_BY_ID)))
          .addMethod(
            getGetBlockByLimitNextMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                org.tron.api.GrpcAPI.BlockLimit,
                org.tron.api.GrpcAPI.BlockList>(
                  this, METHODID_GET_BLOCK_BY_LIMIT_NEXT)))
          .addMethod(
            getGetBlockByLatestNumMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                org.tron.api.GrpcAPI.NumberMessage,
                org.tron.api.GrpcAPI.BlockList>(
                  this, METHODID_GET_BLOCK_BY_LATEST_NUM)))
          .addMethod(
            getGetTransactionByIdMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                org.tron.api.GrpcAPI.BytesMessage,
                org.tron.protos.Protocol.Transaction>(
                  this, METHODID_GET_TRANSACTION_BY_ID)))
          .addMethod(
            getListWitnessesMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                org.tron.api.GrpcAPI.EmptyMessage,
                org.tron.api.GrpcAPI.WitnessList>(
                  this, METHODID_LIST_WITNESSES)))
          .addMethod(
            getGetAssetIssueListMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                org.tron.api.GrpcAPI.EmptyMessage,
                org.tron.api.GrpcAPI.AssetIssueList>(
                  this, METHODID_GET_ASSET_ISSUE_LIST)))
          .addMethod(
            getTotalTransactionMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                org.tron.api.GrpcAPI.EmptyMessage,
                org.tron.api.GrpcAPI.NumberMessage>(
                  this, METHODID_TOTAL_TRANSACTION)))
          .addMethod(
            getGetNextMaintenanceTimeMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                org.tron.api.GrpcAPI.EmptyMessage,
                org.tron.api.GrpcAPI.NumberMessage>(
                  this, METHODID_GET_NEXT_MAINTENANCE_TIME)))
          .build();
    }
  }

  /**
   */
  public static final class WalletStub extends io.grpc.stub.AbstractStub<WalletStub> {
    private WalletStub(io.grpc.Channel channel) {
      super(channel);
    }

    private WalletStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected WalletStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new WalletStub(channel, callOptions);
    }

    /**
     */
    public void getAccount(org.tron.protos.Protocol.Account request,
        io.grpc.stub.StreamObserver<org.tron.protos.Protocol.Account> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetAccountMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void createTransaction(org.tron.protos.Contract.TransferContract request,
        io.grpc.stub.StreamObserver<org.tron.protos.Protocol.Transaction> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getCreateTransactionMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void broadcastTransaction(org.tron.protos.Protocol.Transaction request,
        io.grpc.stub.StreamObserver<org.tron.api.GrpcAPI.Return> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getBroadcastTransactionMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void updateAccount(org.tron.protos.Contract.AccountUpdateContract request,
        io.grpc.stub.StreamObserver<org.tron.protos.Protocol.Transaction> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getUpdateAccountMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void voteWitnessAccount(org.tron.protos.Contract.VoteWitnessContract request,
        io.grpc.stub.StreamObserver<org.tron.protos.Protocol.Transaction> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getVoteWitnessAccountMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void createAssetIssue(org.tron.protos.Contract.AssetIssueContract request,
        io.grpc.stub.StreamObserver<org.tron.protos.Protocol.Transaction> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getCreateAssetIssueMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void updateWitness(org.tron.protos.Contract.WitnessUpdateContract request,
        io.grpc.stub.StreamObserver<org.tron.protos.Protocol.Transaction> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getUpdateWitnessMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void createWitness(org.tron.protos.Contract.WitnessCreateContract request,
        io.grpc.stub.StreamObserver<org.tron.protos.Protocol.Transaction> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getCreateWitnessMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void transferAsset(org.tron.protos.Contract.TransferAssetContract request,
        io.grpc.stub.StreamObserver<org.tron.protos.Protocol.Transaction> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getTransferAssetMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void participateAssetIssue(org.tron.protos.Contract.ParticipateAssetIssueContract request,
        io.grpc.stub.StreamObserver<org.tron.protos.Protocol.Transaction> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getParticipateAssetIssueMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void freezeBalance(org.tron.protos.Contract.FreezeBalanceContract request,
        io.grpc.stub.StreamObserver<org.tron.protos.Protocol.Transaction> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getFreezeBalanceMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void unfreezeBalance(org.tron.protos.Contract.UnfreezeBalanceContract request,
        io.grpc.stub.StreamObserver<org.tron.protos.Protocol.Transaction> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getUnfreezeBalanceMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void unfreezeAsset(org.tron.protos.Contract.UnfreezeAssetContract request,
        io.grpc.stub.StreamObserver<org.tron.protos.Protocol.Transaction> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getUnfreezeAssetMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void withdrawBalance(org.tron.protos.Contract.WithdrawBalanceContract request,
        io.grpc.stub.StreamObserver<org.tron.protos.Protocol.Transaction> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getWithdrawBalanceMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void updateAsset(org.tron.protos.Contract.UpdateAssetContract request,
        io.grpc.stub.StreamObserver<org.tron.protos.Protocol.Transaction> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getUpdateAssetMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void listNodes(org.tron.api.GrpcAPI.EmptyMessage request,
        io.grpc.stub.StreamObserver<org.tron.api.GrpcAPI.NodeList> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getListNodesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getAssetIssueByAccount(org.tron.protos.Protocol.Account request,
        io.grpc.stub.StreamObserver<org.tron.api.GrpcAPI.AssetIssueList> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetAssetIssueByAccountMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getAccountNet(org.tron.protos.Protocol.Account request,
        io.grpc.stub.StreamObserver<org.tron.api.GrpcAPI.AccountNetMessage> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetAccountNetMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getAssetIssueByName(org.tron.api.GrpcAPI.BytesMessage request,
        io.grpc.stub.StreamObserver<org.tron.protos.Contract.AssetIssueContract> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetAssetIssueByNameMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getNowBlock(org.tron.api.GrpcAPI.EmptyMessage request,
        io.grpc.stub.StreamObserver<org.tron.protos.Protocol.Block> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetNowBlockMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getBlockByNum(org.tron.api.GrpcAPI.NumberMessage request,
        io.grpc.stub.StreamObserver<org.tron.protos.Protocol.Block> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetBlockByNumMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getBlockById(org.tron.api.GrpcAPI.BytesMessage request,
        io.grpc.stub.StreamObserver<org.tron.protos.Protocol.Block> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetBlockByIdMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getBlockByLimitNext(org.tron.api.GrpcAPI.BlockLimit request,
        io.grpc.stub.StreamObserver<org.tron.api.GrpcAPI.BlockList> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetBlockByLimitNextMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getBlockByLatestNum(org.tron.api.GrpcAPI.NumberMessage request,
        io.grpc.stub.StreamObserver<org.tron.api.GrpcAPI.BlockList> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetBlockByLatestNumMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getTransactionById(org.tron.api.GrpcAPI.BytesMessage request,
        io.grpc.stub.StreamObserver<org.tron.protos.Protocol.Transaction> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetTransactionByIdMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void listWitnesses(org.tron.api.GrpcAPI.EmptyMessage request,
        io.grpc.stub.StreamObserver<org.tron.api.GrpcAPI.WitnessList> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getListWitnessesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getAssetIssueList(org.tron.api.GrpcAPI.EmptyMessage request,
        io.grpc.stub.StreamObserver<org.tron.api.GrpcAPI.AssetIssueList> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetAssetIssueListMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void totalTransaction(org.tron.api.GrpcAPI.EmptyMessage request,
        io.grpc.stub.StreamObserver<org.tron.api.GrpcAPI.NumberMessage> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getTotalTransactionMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getNextMaintenanceTime(org.tron.api.GrpcAPI.EmptyMessage request,
        io.grpc.stub.StreamObserver<org.tron.api.GrpcAPI.NumberMessage> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetNextMaintenanceTimeMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class WalletBlockingStub extends io.grpc.stub.AbstractStub<WalletBlockingStub> {
    private WalletBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private WalletBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected WalletBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new WalletBlockingStub(channel, callOptions);
    }

    /**
     */
    public org.tron.protos.Protocol.Account getAccount(org.tron.protos.Protocol.Account request) {
      return blockingUnaryCall(
          getChannel(), getGetAccountMethod(), getCallOptions(), request);
    }

    /**
     */
    public org.tron.protos.Protocol.Transaction createTransaction(org.tron.protos.Contract.TransferContract request) {
      return blockingUnaryCall(
          getChannel(), getCreateTransactionMethod(), getCallOptions(), request);
    }

    /**
     */
    public org.tron.api.GrpcAPI.Return broadcastTransaction(org.tron.protos.Protocol.Transaction request) {
      return blockingUnaryCall(
          getChannel(), getBroadcastTransactionMethod(), getCallOptions(), request);
    }

    /**
     */
    public org.tron.protos.Protocol.Transaction updateAccount(org.tron.protos.Contract.AccountUpdateContract request) {
      return blockingUnaryCall(
          getChannel(), getUpdateAccountMethod(), getCallOptions(), request);
    }

    /**
     */
    public org.tron.protos.Protocol.Transaction voteWitnessAccount(org.tron.protos.Contract.VoteWitnessContract request) {
      return blockingUnaryCall(
          getChannel(), getVoteWitnessAccountMethod(), getCallOptions(), request);
    }

    /**
     */
    public org.tron.protos.Protocol.Transaction createAssetIssue(org.tron.protos.Contract.AssetIssueContract request) {
      return blockingUnaryCall(
          getChannel(), getCreateAssetIssueMethod(), getCallOptions(), request);
    }

    /**
     */
    public org.tron.protos.Protocol.Transaction updateWitness(org.tron.protos.Contract.WitnessUpdateContract request) {
      return blockingUnaryCall(
          getChannel(), getUpdateWitnessMethod(), getCallOptions(), request);
    }

    /**
     */
    public org.tron.protos.Protocol.Transaction createWitness(org.tron.protos.Contract.WitnessCreateContract request) {
      return blockingUnaryCall(
          getChannel(), getCreateWitnessMethod(), getCallOptions(), request);
    }

    /**
     */
    public org.tron.protos.Protocol.Transaction transferAsset(org.tron.protos.Contract.TransferAssetContract request) {
      return blockingUnaryCall(
          getChannel(), getTransferAssetMethod(), getCallOptions(), request);
    }

    /**
     */
    public org.tron.protos.Protocol.Transaction participateAssetIssue(org.tron.protos.Contract.ParticipateAssetIssueContract request) {
      return blockingUnaryCall(
          getChannel(), getParticipateAssetIssueMethod(), getCallOptions(), request);
    }

    /**
     */
    public org.tron.protos.Protocol.Transaction freezeBalance(org.tron.protos.Contract.FreezeBalanceContract request) {
      return blockingUnaryCall(
          getChannel(), getFreezeBalanceMethod(), getCallOptions(), request);
    }

    /**
     */
    public org.tron.protos.Protocol.Transaction unfreezeBalance(org.tron.protos.Contract.UnfreezeBalanceContract request) {
      return blockingUnaryCall(
          getChannel(), getUnfreezeBalanceMethod(), getCallOptions(), request);
    }

    /**
     */
    public org.tron.protos.Protocol.Transaction unfreezeAsset(org.tron.protos.Contract.UnfreezeAssetContract request) {
      return blockingUnaryCall(
          getChannel(), getUnfreezeAssetMethod(), getCallOptions(), request);
    }

    /**
     */
    public org.tron.protos.Protocol.Transaction withdrawBalance(org.tron.protos.Contract.WithdrawBalanceContract request) {
      return blockingUnaryCall(
          getChannel(), getWithdrawBalanceMethod(), getCallOptions(), request);
    }

    /**
     */
    public org.tron.protos.Protocol.Transaction updateAsset(org.tron.protos.Contract.UpdateAssetContract request) {
      return blockingUnaryCall(
          getChannel(), getUpdateAssetMethod(), getCallOptions(), request);
    }

    /**
     */
    public org.tron.api.GrpcAPI.NodeList listNodes(org.tron.api.GrpcAPI.EmptyMessage request) {
      return blockingUnaryCall(
          getChannel(), getListNodesMethod(), getCallOptions(), request);
    }

    /**
     */
    public org.tron.api.GrpcAPI.AssetIssueList getAssetIssueByAccount(org.tron.protos.Protocol.Account request) {
      return blockingUnaryCall(
          getChannel(), getGetAssetIssueByAccountMethod(), getCallOptions(), request);
    }

    /**
     */
    public org.tron.api.GrpcAPI.AccountNetMessage getAccountNet(org.tron.protos.Protocol.Account request) {
      return blockingUnaryCall(
          getChannel(), getGetAccountNetMethod(), getCallOptions(), request);
    }

    /**
     */
    public org.tron.protos.Contract.AssetIssueContract getAssetIssueByName(org.tron.api.GrpcAPI.BytesMessage request) {
      return blockingUnaryCall(
          getChannel(), getGetAssetIssueByNameMethod(), getCallOptions(), request);
    }

    /**
     */
    public org.tron.protos.Protocol.Block getNowBlock(org.tron.api.GrpcAPI.EmptyMessage request) {
      return blockingUnaryCall(
          getChannel(), getGetNowBlockMethod(), getCallOptions(), request);
    }

    /**
     */
    public org.tron.protos.Protocol.Block getBlockByNum(org.tron.api.GrpcAPI.NumberMessage request) {
      return blockingUnaryCall(
          getChannel(), getGetBlockByNumMethod(), getCallOptions(), request);
    }

    /**
     */
    public org.tron.protos.Protocol.Block getBlockById(org.tron.api.GrpcAPI.BytesMessage request) {
      return blockingUnaryCall(
          getChannel(), getGetBlockByIdMethod(), getCallOptions(), request);
    }

    /**
     */
    public org.tron.api.GrpcAPI.BlockList getBlockByLimitNext(org.tron.api.GrpcAPI.BlockLimit request) {
      return blockingUnaryCall(
          getChannel(), getGetBlockByLimitNextMethod(), getCallOptions(), request);
    }

    /**
     */
    public org.tron.api.GrpcAPI.BlockList getBlockByLatestNum(org.tron.api.GrpcAPI.NumberMessage request) {
      return blockingUnaryCall(
          getChannel(), getGetBlockByLatestNumMethod(), getCallOptions(), request);
    }

    /**
     */
    public org.tron.protos.Protocol.Transaction getTransactionById(org.tron.api.GrpcAPI.BytesMessage request) {
      return blockingUnaryCall(
          getChannel(), getGetTransactionByIdMethod(), getCallOptions(), request);
    }

    /**
     */
    public org.tron.api.GrpcAPI.WitnessList listWitnesses(org.tron.api.GrpcAPI.EmptyMessage request) {
      return blockingUnaryCall(
          getChannel(), getListWitnessesMethod(), getCallOptions(), request);
    }

    /**
     */
    public org.tron.api.GrpcAPI.AssetIssueList getAssetIssueList(org.tron.api.GrpcAPI.EmptyMessage request) {
      return blockingUnaryCall(
          getChannel(), getGetAssetIssueListMethod(), getCallOptions(), request);
    }

    /**
     */
    public org.tron.api.GrpcAPI.NumberMessage totalTransaction(org.tron.api.GrpcAPI.EmptyMessage request) {
      return blockingUnaryCall(
          getChannel(), getTotalTransactionMethod(), getCallOptions(), request);
    }

    /**
     */
    public org.tron.api.GrpcAPI.NumberMessage getNextMaintenanceTime(org.tron.api.GrpcAPI.EmptyMessage request) {
      return blockingUnaryCall(
          getChannel(), getGetNextMaintenanceTimeMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class WalletFutureStub extends io.grpc.stub.AbstractStub<WalletFutureStub> {
    private WalletFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private WalletFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected WalletFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new WalletFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.tron.protos.Protocol.Account> getAccount(
        org.tron.protos.Protocol.Account request) {
      return futureUnaryCall(
          getChannel().newCall(getGetAccountMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.tron.protos.Protocol.Transaction> createTransaction(
        org.tron.protos.Contract.TransferContract request) {
      return futureUnaryCall(
          getChannel().newCall(getCreateTransactionMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.tron.api.GrpcAPI.Return> broadcastTransaction(
        org.tron.protos.Protocol.Transaction request) {
      return futureUnaryCall(
          getChannel().newCall(getBroadcastTransactionMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.tron.protos.Protocol.Transaction> updateAccount(
        org.tron.protos.Contract.AccountUpdateContract request) {
      return futureUnaryCall(
          getChannel().newCall(getUpdateAccountMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.tron.protos.Protocol.Transaction> voteWitnessAccount(
        org.tron.protos.Contract.VoteWitnessContract request) {
      return futureUnaryCall(
          getChannel().newCall(getVoteWitnessAccountMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.tron.protos.Protocol.Transaction> createAssetIssue(
        org.tron.protos.Contract.AssetIssueContract request) {
      return futureUnaryCall(
          getChannel().newCall(getCreateAssetIssueMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.tron.protos.Protocol.Transaction> updateWitness(
        org.tron.protos.Contract.WitnessUpdateContract request) {
      return futureUnaryCall(
          getChannel().newCall(getUpdateWitnessMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.tron.protos.Protocol.Transaction> createWitness(
        org.tron.protos.Contract.WitnessCreateContract request) {
      return futureUnaryCall(
          getChannel().newCall(getCreateWitnessMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.tron.protos.Protocol.Transaction> transferAsset(
        org.tron.protos.Contract.TransferAssetContract request) {
      return futureUnaryCall(
          getChannel().newCall(getTransferAssetMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.tron.protos.Protocol.Transaction> participateAssetIssue(
        org.tron.protos.Contract.ParticipateAssetIssueContract request) {
      return futureUnaryCall(
          getChannel().newCall(getParticipateAssetIssueMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.tron.protos.Protocol.Transaction> freezeBalance(
        org.tron.protos.Contract.FreezeBalanceContract request) {
      return futureUnaryCall(
          getChannel().newCall(getFreezeBalanceMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.tron.protos.Protocol.Transaction> unfreezeBalance(
        org.tron.protos.Contract.UnfreezeBalanceContract request) {
      return futureUnaryCall(
          getChannel().newCall(getUnfreezeBalanceMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.tron.protos.Protocol.Transaction> unfreezeAsset(
        org.tron.protos.Contract.UnfreezeAssetContract request) {
      return futureUnaryCall(
          getChannel().newCall(getUnfreezeAssetMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.tron.protos.Protocol.Transaction> withdrawBalance(
        org.tron.protos.Contract.WithdrawBalanceContract request) {
      return futureUnaryCall(
          getChannel().newCall(getWithdrawBalanceMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.tron.protos.Protocol.Transaction> updateAsset(
        org.tron.protos.Contract.UpdateAssetContract request) {
      return futureUnaryCall(
          getChannel().newCall(getUpdateAssetMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.tron.api.GrpcAPI.NodeList> listNodes(
        org.tron.api.GrpcAPI.EmptyMessage request) {
      return futureUnaryCall(
          getChannel().newCall(getListNodesMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.tron.api.GrpcAPI.AssetIssueList> getAssetIssueByAccount(
        org.tron.protos.Protocol.Account request) {
      return futureUnaryCall(
          getChannel().newCall(getGetAssetIssueByAccountMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.tron.api.GrpcAPI.AccountNetMessage> getAccountNet(
        org.tron.protos.Protocol.Account request) {
      return futureUnaryCall(
          getChannel().newCall(getGetAccountNetMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.tron.protos.Contract.AssetIssueContract> getAssetIssueByName(
        org.tron.api.GrpcAPI.BytesMessage request) {
      return futureUnaryCall(
          getChannel().newCall(getGetAssetIssueByNameMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.tron.protos.Protocol.Block> getNowBlock(
        org.tron.api.GrpcAPI.EmptyMessage request) {
      return futureUnaryCall(
          getChannel().newCall(getGetNowBlockMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.tron.protos.Protocol.Block> getBlockByNum(
        org.tron.api.GrpcAPI.NumberMessage request) {
      return futureUnaryCall(
          getChannel().newCall(getGetBlockByNumMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.tron.protos.Protocol.Block> getBlockById(
        org.tron.api.GrpcAPI.BytesMessage request) {
      return futureUnaryCall(
          getChannel().newCall(getGetBlockByIdMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.tron.api.GrpcAPI.BlockList> getBlockByLimitNext(
        org.tron.api.GrpcAPI.BlockLimit request) {
      return futureUnaryCall(
          getChannel().newCall(getGetBlockByLimitNextMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.tron.api.GrpcAPI.BlockList> getBlockByLatestNum(
        org.tron.api.GrpcAPI.NumberMessage request) {
      return futureUnaryCall(
          getChannel().newCall(getGetBlockByLatestNumMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.tron.protos.Protocol.Transaction> getTransactionById(
        org.tron.api.GrpcAPI.BytesMessage request) {
      return futureUnaryCall(
          getChannel().newCall(getGetTransactionByIdMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.tron.api.GrpcAPI.WitnessList> listWitnesses(
        org.tron.api.GrpcAPI.EmptyMessage request) {
      return futureUnaryCall(
          getChannel().newCall(getListWitnessesMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.tron.api.GrpcAPI.AssetIssueList> getAssetIssueList(
        org.tron.api.GrpcAPI.EmptyMessage request) {
      return futureUnaryCall(
          getChannel().newCall(getGetAssetIssueListMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.tron.api.GrpcAPI.NumberMessage> totalTransaction(
        org.tron.api.GrpcAPI.EmptyMessage request) {
      return futureUnaryCall(
          getChannel().newCall(getTotalTransactionMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.tron.api.GrpcAPI.NumberMessage> getNextMaintenanceTime(
        org.tron.api.GrpcAPI.EmptyMessage request) {
      return futureUnaryCall(
          getChannel().newCall(getGetNextMaintenanceTimeMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_ACCOUNT = 0;
  private static final int METHODID_CREATE_TRANSACTION = 1;
  private static final int METHODID_BROADCAST_TRANSACTION = 2;
  private static final int METHODID_UPDATE_ACCOUNT = 3;
  private static final int METHODID_VOTE_WITNESS_ACCOUNT = 4;
  private static final int METHODID_CREATE_ASSET_ISSUE = 5;
  private static final int METHODID_UPDATE_WITNESS = 6;
  private static final int METHODID_CREATE_WITNESS = 7;
  private static final int METHODID_TRANSFER_ASSET = 8;
  private static final int METHODID_PARTICIPATE_ASSET_ISSUE = 9;
  private static final int METHODID_FREEZE_BALANCE = 10;
  private static final int METHODID_UNFREEZE_BALANCE = 11;
  private static final int METHODID_UNFREEZE_ASSET = 12;
  private static final int METHODID_WITHDRAW_BALANCE = 13;
  private static final int METHODID_UPDATE_ASSET = 14;
  private static final int METHODID_LIST_NODES = 15;
  private static final int METHODID_GET_ASSET_ISSUE_BY_ACCOUNT = 16;
  private static final int METHODID_GET_ACCOUNT_NET = 17;
  private static final int METHODID_GET_ASSET_ISSUE_BY_NAME = 18;
  private static final int METHODID_GET_NOW_BLOCK = 19;
  private static final int METHODID_GET_BLOCK_BY_NUM = 20;
  private static final int METHODID_GET_BLOCK_BY_ID = 21;
  private static final int METHODID_GET_BLOCK_BY_LIMIT_NEXT = 22;
  private static final int METHODID_GET_BLOCK_BY_LATEST_NUM = 23;
  private static final int METHODID_GET_TRANSACTION_BY_ID = 24;
  private static final int METHODID_LIST_WITNESSES = 25;
  private static final int METHODID_GET_ASSET_ISSUE_LIST = 26;
  private static final int METHODID_TOTAL_TRANSACTION = 27;
  private static final int METHODID_GET_NEXT_MAINTENANCE_TIME = 28;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final WalletImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(WalletImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_ACCOUNT:
          serviceImpl.getAccount((org.tron.protos.Protocol.Account) request,
              (io.grpc.stub.StreamObserver<org.tron.protos.Protocol.Account>) responseObserver);
          break;
        case METHODID_CREATE_TRANSACTION:
          serviceImpl.createTransaction((org.tron.protos.Contract.TransferContract) request,
              (io.grpc.stub.StreamObserver<org.tron.protos.Protocol.Transaction>) responseObserver);
          break;
        case METHODID_BROADCAST_TRANSACTION:
          serviceImpl.broadcastTransaction((org.tron.protos.Protocol.Transaction) request,
              (io.grpc.stub.StreamObserver<org.tron.api.GrpcAPI.Return>) responseObserver);
          break;
        case METHODID_UPDATE_ACCOUNT:
          serviceImpl.updateAccount((org.tron.protos.Contract.AccountUpdateContract) request,
              (io.grpc.stub.StreamObserver<org.tron.protos.Protocol.Transaction>) responseObserver);
          break;
        case METHODID_VOTE_WITNESS_ACCOUNT:
          serviceImpl.voteWitnessAccount((org.tron.protos.Contract.VoteWitnessContract) request,
              (io.grpc.stub.StreamObserver<org.tron.protos.Protocol.Transaction>) responseObserver);
          break;
        case METHODID_CREATE_ASSET_ISSUE:
          serviceImpl.createAssetIssue((org.tron.protos.Contract.AssetIssueContract) request,
              (io.grpc.stub.StreamObserver<org.tron.protos.Protocol.Transaction>) responseObserver);
          break;
        case METHODID_UPDATE_WITNESS:
          serviceImpl.updateWitness((org.tron.protos.Contract.WitnessUpdateContract) request,
              (io.grpc.stub.StreamObserver<org.tron.protos.Protocol.Transaction>) responseObserver);
          break;
        case METHODID_CREATE_WITNESS:
          serviceImpl.createWitness((org.tron.protos.Contract.WitnessCreateContract) request,
              (io.grpc.stub.StreamObserver<org.tron.protos.Protocol.Transaction>) responseObserver);
          break;
        case METHODID_TRANSFER_ASSET:
          serviceImpl.transferAsset((org.tron.protos.Contract.TransferAssetContract) request,
              (io.grpc.stub.StreamObserver<org.tron.protos.Protocol.Transaction>) responseObserver);
          break;
        case METHODID_PARTICIPATE_ASSET_ISSUE:
          serviceImpl.participateAssetIssue((org.tron.protos.Contract.ParticipateAssetIssueContract) request,
              (io.grpc.stub.StreamObserver<org.tron.protos.Protocol.Transaction>) responseObserver);
          break;
        case METHODID_FREEZE_BALANCE:
          serviceImpl.freezeBalance((org.tron.protos.Contract.FreezeBalanceContract) request,
              (io.grpc.stub.StreamObserver<org.tron.protos.Protocol.Transaction>) responseObserver);
          break;
        case METHODID_UNFREEZE_BALANCE:
          serviceImpl.unfreezeBalance((org.tron.protos.Contract.UnfreezeBalanceContract) request,
              (io.grpc.stub.StreamObserver<org.tron.protos.Protocol.Transaction>) responseObserver);
          break;
        case METHODID_UNFREEZE_ASSET:
          serviceImpl.unfreezeAsset((org.tron.protos.Contract.UnfreezeAssetContract) request,
              (io.grpc.stub.StreamObserver<org.tron.protos.Protocol.Transaction>) responseObserver);
          break;
        case METHODID_WITHDRAW_BALANCE:
          serviceImpl.withdrawBalance((org.tron.protos.Contract.WithdrawBalanceContract) request,
              (io.grpc.stub.StreamObserver<org.tron.protos.Protocol.Transaction>) responseObserver);
          break;
        case METHODID_UPDATE_ASSET:
          serviceImpl.updateAsset((org.tron.protos.Contract.UpdateAssetContract) request,
              (io.grpc.stub.StreamObserver<org.tron.protos.Protocol.Transaction>) responseObserver);
          break;
        case METHODID_LIST_NODES:
          serviceImpl.listNodes((org.tron.api.GrpcAPI.EmptyMessage) request,
              (io.grpc.stub.StreamObserver<org.tron.api.GrpcAPI.NodeList>) responseObserver);
          break;
        case METHODID_GET_ASSET_ISSUE_BY_ACCOUNT:
          serviceImpl.getAssetIssueByAccount((org.tron.protos.Protocol.Account) request,
              (io.grpc.stub.StreamObserver<org.tron.api.GrpcAPI.AssetIssueList>) responseObserver);
          break;
        case METHODID_GET_ACCOUNT_NET:
          serviceImpl.getAccountNet((org.tron.protos.Protocol.Account) request,
              (io.grpc.stub.StreamObserver<org.tron.api.GrpcAPI.AccountNetMessage>) responseObserver);
          break;
        case METHODID_GET_ASSET_ISSUE_BY_NAME:
          serviceImpl.getAssetIssueByName((org.tron.api.GrpcAPI.BytesMessage) request,
              (io.grpc.stub.StreamObserver<org.tron.protos.Contract.AssetIssueContract>) responseObserver);
          break;
        case METHODID_GET_NOW_BLOCK:
          serviceImpl.getNowBlock((org.tron.api.GrpcAPI.EmptyMessage) request,
              (io.grpc.stub.StreamObserver<org.tron.protos.Protocol.Block>) responseObserver);
          break;
        case METHODID_GET_BLOCK_BY_NUM:
          serviceImpl.getBlockByNum((org.tron.api.GrpcAPI.NumberMessage) request,
              (io.grpc.stub.StreamObserver<org.tron.protos.Protocol.Block>) responseObserver);
          break;
        case METHODID_GET_BLOCK_BY_ID:
          serviceImpl.getBlockById((org.tron.api.GrpcAPI.BytesMessage) request,
              (io.grpc.stub.StreamObserver<org.tron.protos.Protocol.Block>) responseObserver);
          break;
        case METHODID_GET_BLOCK_BY_LIMIT_NEXT:
          serviceImpl.getBlockByLimitNext((org.tron.api.GrpcAPI.BlockLimit) request,
              (io.grpc.stub.StreamObserver<org.tron.api.GrpcAPI.BlockList>) responseObserver);
          break;
        case METHODID_GET_BLOCK_BY_LATEST_NUM:
          serviceImpl.getBlockByLatestNum((org.tron.api.GrpcAPI.NumberMessage) request,
              (io.grpc.stub.StreamObserver<org.tron.api.GrpcAPI.BlockList>) responseObserver);
          break;
        case METHODID_GET_TRANSACTION_BY_ID:
          serviceImpl.getTransactionById((org.tron.api.GrpcAPI.BytesMessage) request,
              (io.grpc.stub.StreamObserver<org.tron.protos.Protocol.Transaction>) responseObserver);
          break;
        case METHODID_LIST_WITNESSES:
          serviceImpl.listWitnesses((org.tron.api.GrpcAPI.EmptyMessage) request,
              (io.grpc.stub.StreamObserver<org.tron.api.GrpcAPI.WitnessList>) responseObserver);
          break;
        case METHODID_GET_ASSET_ISSUE_LIST:
          serviceImpl.getAssetIssueList((org.tron.api.GrpcAPI.EmptyMessage) request,
              (io.grpc.stub.StreamObserver<org.tron.api.GrpcAPI.AssetIssueList>) responseObserver);
          break;
        case METHODID_TOTAL_TRANSACTION:
          serviceImpl.totalTransaction((org.tron.api.GrpcAPI.EmptyMessage) request,
              (io.grpc.stub.StreamObserver<org.tron.api.GrpcAPI.NumberMessage>) responseObserver);
          break;
        case METHODID_GET_NEXT_MAINTENANCE_TIME:
          serviceImpl.getNextMaintenanceTime((org.tron.api.GrpcAPI.EmptyMessage) request,
              (io.grpc.stub.StreamObserver<org.tron.api.GrpcAPI.NumberMessage>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class WalletBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    WalletBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return org.tron.api.GrpcAPI.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Wallet");
    }
  }

  private static final class WalletFileDescriptorSupplier
      extends WalletBaseDescriptorSupplier {
    WalletFileDescriptorSupplier() {}
  }

  private static final class WalletMethodDescriptorSupplier
      extends WalletBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    WalletMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (WalletGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new WalletFileDescriptorSupplier())
              .addMethod(getGetAccountMethod())
              .addMethod(getCreateTransactionMethod())
              .addMethod(getBroadcastTransactionMethod())
              .addMethod(getUpdateAccountMethod())
              .addMethod(getVoteWitnessAccountMethod())
              .addMethod(getCreateAssetIssueMethod())
              .addMethod(getUpdateWitnessMethod())
              .addMethod(getCreateWitnessMethod())
              .addMethod(getTransferAssetMethod())
              .addMethod(getParticipateAssetIssueMethod())
              .addMethod(getFreezeBalanceMethod())
              .addMethod(getUnfreezeBalanceMethod())
              .addMethod(getUnfreezeAssetMethod())
              .addMethod(getWithdrawBalanceMethod())
              .addMethod(getUpdateAssetMethod())
              .addMethod(getListNodesMethod())
              .addMethod(getGetAssetIssueByAccountMethod())
              .addMethod(getGetAccountNetMethod())
              .addMethod(getGetAssetIssueByNameMethod())
              .addMethod(getGetNowBlockMethod())
              .addMethod(getGetBlockByNumMethod())
              .addMethod(getGetBlockByIdMethod())
              .addMethod(getGetBlockByLimitNextMethod())
              .addMethod(getGetBlockByLatestNumMethod())
              .addMethod(getGetTransactionByIdMethod())
              .addMethod(getListWitnessesMethod())
              .addMethod(getGetAssetIssueListMethod())
              .addMethod(getTotalTransactionMethod())
              .addMethod(getGetNextMaintenanceTimeMethod())
              .build();
        }
      }
    }
    return result;
  }
}
