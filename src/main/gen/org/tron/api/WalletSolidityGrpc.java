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
public final class WalletSolidityGrpc {

  private WalletSolidityGrpc() {}

  public static final String SERVICE_NAME = "protocol.WalletSolidity";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @Deprecated // Use {@link #getGetAccountMethod()} instead.
  public static final io.grpc.MethodDescriptor<org.tron.protos.Protocol.Account,
      org.tron.protos.Protocol.Account> METHOD_GET_ACCOUNT = getGetAccountMethod();

  private static volatile io.grpc.MethodDescriptor<org.tron.protos.Protocol.Account,
      org.tron.protos.Protocol.Account> getGetAccountMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<org.tron.protos.Protocol.Account,
      org.tron.protos.Protocol.Account> getGetAccountMethod() {
    io.grpc.MethodDescriptor<org.tron.protos.Protocol.Account, org.tron.protos.Protocol.Account> getGetAccountMethod;
    if ((getGetAccountMethod = WalletSolidityGrpc.getGetAccountMethod) == null) {
      synchronized (WalletSolidityGrpc.class) {
        if ((getGetAccountMethod = WalletSolidityGrpc.getGetAccountMethod) == null) {
          WalletSolidityGrpc.getGetAccountMethod = getGetAccountMethod = 
              io.grpc.MethodDescriptor.<org.tron.protos.Protocol.Account, org.tron.protos.Protocol.Account>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "protocol.WalletSolidity", "GetAccount"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.tron.protos.Protocol.Account.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.tron.protos.Protocol.Account.getDefaultInstance()))
                  .setSchemaDescriptor(new WalletSolidityMethodDescriptorSupplier("GetAccount"))
                  .build();
          }
        }
     }
     return getGetAccountMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @Deprecated // Use {@link #getListAccountsMethod()} instead.
  public static final io.grpc.MethodDescriptor<GrpcAPI.EmptyMessage,
      GrpcAPI.AccountList> METHOD_LIST_ACCOUNTS = getListAccountsMethod();

  private static volatile io.grpc.MethodDescriptor<GrpcAPI.EmptyMessage,
      GrpcAPI.AccountList> getListAccountsMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<GrpcAPI.EmptyMessage,
      GrpcAPI.AccountList> getListAccountsMethod() {
    io.grpc.MethodDescriptor<GrpcAPI.EmptyMessage, GrpcAPI.AccountList> getListAccountsMethod;
    if ((getListAccountsMethod = WalletSolidityGrpc.getListAccountsMethod) == null) {
      synchronized (WalletSolidityGrpc.class) {
        if ((getListAccountsMethod = WalletSolidityGrpc.getListAccountsMethod) == null) {
          WalletSolidityGrpc.getListAccountsMethod = getListAccountsMethod = 
              io.grpc.MethodDescriptor.<GrpcAPI.EmptyMessage, GrpcAPI.AccountList>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "protocol.WalletSolidity", "ListAccounts"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  GrpcAPI.EmptyMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  GrpcAPI.AccountList.getDefaultInstance()))
                  .setSchemaDescriptor(new WalletSolidityMethodDescriptorSupplier("ListAccounts"))
                  .build();
          }
        }
     }
     return getListAccountsMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @Deprecated // Use {@link #getListWitnessesMethod()} instead.
  public static final io.grpc.MethodDescriptor<GrpcAPI.EmptyMessage,
      GrpcAPI.WitnessList> METHOD_LIST_WITNESSES = getListWitnessesMethod();

  private static volatile io.grpc.MethodDescriptor<GrpcAPI.EmptyMessage,
      GrpcAPI.WitnessList> getListWitnessesMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<GrpcAPI.EmptyMessage,
      GrpcAPI.WitnessList> getListWitnessesMethod() {
    io.grpc.MethodDescriptor<GrpcAPI.EmptyMessage, GrpcAPI.WitnessList> getListWitnessesMethod;
    if ((getListWitnessesMethod = WalletSolidityGrpc.getListWitnessesMethod) == null) {
      synchronized (WalletSolidityGrpc.class) {
        if ((getListWitnessesMethod = WalletSolidityGrpc.getListWitnessesMethod) == null) {
          WalletSolidityGrpc.getListWitnessesMethod = getListWitnessesMethod = 
              io.grpc.MethodDescriptor.<GrpcAPI.EmptyMessage, GrpcAPI.WitnessList>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "protocol.WalletSolidity", "ListWitnesses"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  GrpcAPI.EmptyMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  GrpcAPI.WitnessList.getDefaultInstance()))
                  .setSchemaDescriptor(new WalletSolidityMethodDescriptorSupplier("ListWitnesses"))
                  .build();
          }
        }
     }
     return getListWitnessesMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @Deprecated // Use {@link #getGetAssetIssueListMethod()} instead.
  public static final io.grpc.MethodDescriptor<GrpcAPI.EmptyMessage,
      GrpcAPI.AssetIssueList> METHOD_GET_ASSET_ISSUE_LIST = getGetAssetIssueListMethod();

  private static volatile io.grpc.MethodDescriptor<GrpcAPI.EmptyMessage,
      GrpcAPI.AssetIssueList> getGetAssetIssueListMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<GrpcAPI.EmptyMessage,
      GrpcAPI.AssetIssueList> getGetAssetIssueListMethod() {
    io.grpc.MethodDescriptor<GrpcAPI.EmptyMessage, GrpcAPI.AssetIssueList> getGetAssetIssueListMethod;
    if ((getGetAssetIssueListMethod = WalletSolidityGrpc.getGetAssetIssueListMethod) == null) {
      synchronized (WalletSolidityGrpc.class) {
        if ((getGetAssetIssueListMethod = WalletSolidityGrpc.getGetAssetIssueListMethod) == null) {
          WalletSolidityGrpc.getGetAssetIssueListMethod = getGetAssetIssueListMethod = 
              io.grpc.MethodDescriptor.<GrpcAPI.EmptyMessage, GrpcAPI.AssetIssueList>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "protocol.WalletSolidity", "GetAssetIssueList"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  GrpcAPI.EmptyMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  GrpcAPI.AssetIssueList.getDefaultInstance()))
                  .setSchemaDescriptor(new WalletSolidityMethodDescriptorSupplier("GetAssetIssueList"))
                  .build();
          }
        }
     }
     return getGetAssetIssueListMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @Deprecated // Use {@link #getGetAssetIssueListByTimestampMethod()} instead.
  public static final io.grpc.MethodDescriptor<GrpcAPI.NumberMessage,
      GrpcAPI.AssetIssueList> METHOD_GET_ASSET_ISSUE_LIST_BY_TIMESTAMP = getGetAssetIssueListByTimestampMethod();

  private static volatile io.grpc.MethodDescriptor<GrpcAPI.NumberMessage,
      GrpcAPI.AssetIssueList> getGetAssetIssueListByTimestampMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<GrpcAPI.NumberMessage,
      GrpcAPI.AssetIssueList> getGetAssetIssueListByTimestampMethod() {
    io.grpc.MethodDescriptor<GrpcAPI.NumberMessage, GrpcAPI.AssetIssueList> getGetAssetIssueListByTimestampMethod;
    if ((getGetAssetIssueListByTimestampMethod = WalletSolidityGrpc.getGetAssetIssueListByTimestampMethod) == null) {
      synchronized (WalletSolidityGrpc.class) {
        if ((getGetAssetIssueListByTimestampMethod = WalletSolidityGrpc.getGetAssetIssueListByTimestampMethod) == null) {
          WalletSolidityGrpc.getGetAssetIssueListByTimestampMethod = getGetAssetIssueListByTimestampMethod = 
              io.grpc.MethodDescriptor.<GrpcAPI.NumberMessage, GrpcAPI.AssetIssueList>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "protocol.WalletSolidity", "GetAssetIssueListByTimestamp"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  GrpcAPI.NumberMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  GrpcAPI.AssetIssueList.getDefaultInstance()))
                  .setSchemaDescriptor(new WalletSolidityMethodDescriptorSupplier("GetAssetIssueListByTimestamp"))
                  .build();
          }
        }
     }
     return getGetAssetIssueListByTimestampMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @Deprecated // Use {@link #getGetAssetIssueByAccountMethod()} instead.
  public static final io.grpc.MethodDescriptor<org.tron.protos.Protocol.Account,
      GrpcAPI.AssetIssueList> METHOD_GET_ASSET_ISSUE_BY_ACCOUNT = getGetAssetIssueByAccountMethod();

  private static volatile io.grpc.MethodDescriptor<org.tron.protos.Protocol.Account,
      GrpcAPI.AssetIssueList> getGetAssetIssueByAccountMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<org.tron.protos.Protocol.Account,
      GrpcAPI.AssetIssueList> getGetAssetIssueByAccountMethod() {
    io.grpc.MethodDescriptor<org.tron.protos.Protocol.Account, GrpcAPI.AssetIssueList> getGetAssetIssueByAccountMethod;
    if ((getGetAssetIssueByAccountMethod = WalletSolidityGrpc.getGetAssetIssueByAccountMethod) == null) {
      synchronized (WalletSolidityGrpc.class) {
        if ((getGetAssetIssueByAccountMethod = WalletSolidityGrpc.getGetAssetIssueByAccountMethod) == null) {
          WalletSolidityGrpc.getGetAssetIssueByAccountMethod = getGetAssetIssueByAccountMethod = 
              io.grpc.MethodDescriptor.<org.tron.protos.Protocol.Account, GrpcAPI.AssetIssueList>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "protocol.WalletSolidity", "GetAssetIssueByAccount"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.tron.protos.Protocol.Account.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  GrpcAPI.AssetIssueList.getDefaultInstance()))
                  .setSchemaDescriptor(new WalletSolidityMethodDescriptorSupplier("GetAssetIssueByAccount"))
                  .build();
          }
        }
     }
     return getGetAssetIssueByAccountMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @Deprecated // Use {@link #getGetAssetIssueByNameMethod()} instead.
  public static final io.grpc.MethodDescriptor<GrpcAPI.BytesMessage,
      org.tron.protos.Contract.AssetIssueContract> METHOD_GET_ASSET_ISSUE_BY_NAME = getGetAssetIssueByNameMethod();

  private static volatile io.grpc.MethodDescriptor<GrpcAPI.BytesMessage,
      org.tron.protos.Contract.AssetIssueContract> getGetAssetIssueByNameMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<GrpcAPI.BytesMessage,
      org.tron.protos.Contract.AssetIssueContract> getGetAssetIssueByNameMethod() {
    io.grpc.MethodDescriptor<GrpcAPI.BytesMessage, org.tron.protos.Contract.AssetIssueContract> getGetAssetIssueByNameMethod;
    if ((getGetAssetIssueByNameMethod = WalletSolidityGrpc.getGetAssetIssueByNameMethod) == null) {
      synchronized (WalletSolidityGrpc.class) {
        if ((getGetAssetIssueByNameMethod = WalletSolidityGrpc.getGetAssetIssueByNameMethod) == null) {
          WalletSolidityGrpc.getGetAssetIssueByNameMethod = getGetAssetIssueByNameMethod = 
              io.grpc.MethodDescriptor.<GrpcAPI.BytesMessage, org.tron.protos.Contract.AssetIssueContract>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "protocol.WalletSolidity", "GetAssetIssueByName"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  GrpcAPI.BytesMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.tron.protos.Contract.AssetIssueContract.getDefaultInstance()))
                  .setSchemaDescriptor(new WalletSolidityMethodDescriptorSupplier("GetAssetIssueByName"))
                  .build();
          }
        }
     }
     return getGetAssetIssueByNameMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @Deprecated // Use {@link #getGetNowBlockMethod()} instead.
  public static final io.grpc.MethodDescriptor<GrpcAPI.EmptyMessage,
      org.tron.protos.Protocol.Block> METHOD_GET_NOW_BLOCK = getGetNowBlockMethod();

  private static volatile io.grpc.MethodDescriptor<GrpcAPI.EmptyMessage,
      org.tron.protos.Protocol.Block> getGetNowBlockMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<GrpcAPI.EmptyMessage,
      org.tron.protos.Protocol.Block> getGetNowBlockMethod() {
    io.grpc.MethodDescriptor<GrpcAPI.EmptyMessage, org.tron.protos.Protocol.Block> getGetNowBlockMethod;
    if ((getGetNowBlockMethod = WalletSolidityGrpc.getGetNowBlockMethod) == null) {
      synchronized (WalletSolidityGrpc.class) {
        if ((getGetNowBlockMethod = WalletSolidityGrpc.getGetNowBlockMethod) == null) {
          WalletSolidityGrpc.getGetNowBlockMethod = getGetNowBlockMethod = 
              io.grpc.MethodDescriptor.<GrpcAPI.EmptyMessage, org.tron.protos.Protocol.Block>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "protocol.WalletSolidity", "GetNowBlock"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  GrpcAPI.EmptyMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.tron.protos.Protocol.Block.getDefaultInstance()))
                  .setSchemaDescriptor(new WalletSolidityMethodDescriptorSupplier("GetNowBlock"))
                  .build();
          }
        }
     }
     return getGetNowBlockMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @Deprecated // Use {@link #getGetBlockByNumMethod()} instead.
  public static final io.grpc.MethodDescriptor<GrpcAPI.NumberMessage,
      org.tron.protos.Protocol.Block> METHOD_GET_BLOCK_BY_NUM = getGetBlockByNumMethod();

  private static volatile io.grpc.MethodDescriptor<GrpcAPI.NumberMessage,
      org.tron.protos.Protocol.Block> getGetBlockByNumMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<GrpcAPI.NumberMessage,
      org.tron.protos.Protocol.Block> getGetBlockByNumMethod() {
    io.grpc.MethodDescriptor<GrpcAPI.NumberMessage, org.tron.protos.Protocol.Block> getGetBlockByNumMethod;
    if ((getGetBlockByNumMethod = WalletSolidityGrpc.getGetBlockByNumMethod) == null) {
      synchronized (WalletSolidityGrpc.class) {
        if ((getGetBlockByNumMethod = WalletSolidityGrpc.getGetBlockByNumMethod) == null) {
          WalletSolidityGrpc.getGetBlockByNumMethod = getGetBlockByNumMethod = 
              io.grpc.MethodDescriptor.<GrpcAPI.NumberMessage, org.tron.protos.Protocol.Block>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "protocol.WalletSolidity", "GetBlockByNum"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  GrpcAPI.NumberMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.tron.protos.Protocol.Block.getDefaultInstance()))
                  .setSchemaDescriptor(new WalletSolidityMethodDescriptorSupplier("GetBlockByNum"))
                  .build();
          }
        }
     }
     return getGetBlockByNumMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @Deprecated // Use {@link #getTotalTransactionMethod()} instead.
  public static final io.grpc.MethodDescriptor<GrpcAPI.EmptyMessage,
      GrpcAPI.NumberMessage> METHOD_TOTAL_TRANSACTION = getTotalTransactionMethod();

  private static volatile io.grpc.MethodDescriptor<GrpcAPI.EmptyMessage,
      GrpcAPI.NumberMessage> getTotalTransactionMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<GrpcAPI.EmptyMessage,
      GrpcAPI.NumberMessage> getTotalTransactionMethod() {
    io.grpc.MethodDescriptor<GrpcAPI.EmptyMessage, GrpcAPI.NumberMessage> getTotalTransactionMethod;
    if ((getTotalTransactionMethod = WalletSolidityGrpc.getTotalTransactionMethod) == null) {
      synchronized (WalletSolidityGrpc.class) {
        if ((getTotalTransactionMethod = WalletSolidityGrpc.getTotalTransactionMethod) == null) {
          WalletSolidityGrpc.getTotalTransactionMethod = getTotalTransactionMethod = 
              io.grpc.MethodDescriptor.<GrpcAPI.EmptyMessage, GrpcAPI.NumberMessage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "protocol.WalletSolidity", "TotalTransaction"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  GrpcAPI.EmptyMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  GrpcAPI.NumberMessage.getDefaultInstance()))
                  .setSchemaDescriptor(new WalletSolidityMethodDescriptorSupplier("TotalTransaction"))
                  .build();
          }
        }
     }
     return getTotalTransactionMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @Deprecated // Use {@link #getGetTransactionByIdMethod()} instead.
  public static final io.grpc.MethodDescriptor<GrpcAPI.BytesMessage,
      org.tron.protos.Protocol.Transaction> METHOD_GET_TRANSACTION_BY_ID = getGetTransactionByIdMethod();

  private static volatile io.grpc.MethodDescriptor<GrpcAPI.BytesMessage,
      org.tron.protos.Protocol.Transaction> getGetTransactionByIdMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<GrpcAPI.BytesMessage,
      org.tron.protos.Protocol.Transaction> getGetTransactionByIdMethod() {
    io.grpc.MethodDescriptor<GrpcAPI.BytesMessage, org.tron.protos.Protocol.Transaction> getGetTransactionByIdMethod;
    if ((getGetTransactionByIdMethod = WalletSolidityGrpc.getGetTransactionByIdMethod) == null) {
      synchronized (WalletSolidityGrpc.class) {
        if ((getGetTransactionByIdMethod = WalletSolidityGrpc.getGetTransactionByIdMethod) == null) {
          WalletSolidityGrpc.getGetTransactionByIdMethod = getGetTransactionByIdMethod = 
              io.grpc.MethodDescriptor.<GrpcAPI.BytesMessage, org.tron.protos.Protocol.Transaction>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "protocol.WalletSolidity", "GetTransactionById"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  GrpcAPI.BytesMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.tron.protos.Protocol.Transaction.getDefaultInstance()))
                  .setSchemaDescriptor(new WalletSolidityMethodDescriptorSupplier("GetTransactionById"))
                  .build();
          }
        }
     }
     return getGetTransactionByIdMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @Deprecated // Use {@link #getGetTransactionsByTimestampMethod()} instead.
  public static final io.grpc.MethodDescriptor<GrpcAPI.TimeMessage,
      GrpcAPI.TransactionList> METHOD_GET_TRANSACTIONS_BY_TIMESTAMP = getGetTransactionsByTimestampMethod();

  private static volatile io.grpc.MethodDescriptor<GrpcAPI.TimeMessage,
      GrpcAPI.TransactionList> getGetTransactionsByTimestampMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<GrpcAPI.TimeMessage,
      GrpcAPI.TransactionList> getGetTransactionsByTimestampMethod() {
    io.grpc.MethodDescriptor<GrpcAPI.TimeMessage, GrpcAPI.TransactionList> getGetTransactionsByTimestampMethod;
    if ((getGetTransactionsByTimestampMethod = WalletSolidityGrpc.getGetTransactionsByTimestampMethod) == null) {
      synchronized (WalletSolidityGrpc.class) {
        if ((getGetTransactionsByTimestampMethod = WalletSolidityGrpc.getGetTransactionsByTimestampMethod) == null) {
          WalletSolidityGrpc.getGetTransactionsByTimestampMethod = getGetTransactionsByTimestampMethod = 
              io.grpc.MethodDescriptor.<GrpcAPI.TimeMessage, GrpcAPI.TransactionList>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "protocol.WalletSolidity", "GetTransactionsByTimestamp"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  GrpcAPI.TimeMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  GrpcAPI.TransactionList.getDefaultInstance()))
                  .setSchemaDescriptor(new WalletSolidityMethodDescriptorSupplier("GetTransactionsByTimestamp"))
                  .build();
          }
        }
     }
     return getGetTransactionsByTimestampMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @Deprecated // Use {@link #getGetTransactionsFromThisMethod()} instead.
  public static final io.grpc.MethodDescriptor<org.tron.protos.Protocol.Account,
      GrpcAPI.TransactionList> METHOD_GET_TRANSACTIONS_FROM_THIS = getGetTransactionsFromThisMethod();

  private static volatile io.grpc.MethodDescriptor<org.tron.protos.Protocol.Account,
      GrpcAPI.TransactionList> getGetTransactionsFromThisMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<org.tron.protos.Protocol.Account,
      GrpcAPI.TransactionList> getGetTransactionsFromThisMethod() {
    io.grpc.MethodDescriptor<org.tron.protos.Protocol.Account, GrpcAPI.TransactionList> getGetTransactionsFromThisMethod;
    if ((getGetTransactionsFromThisMethod = WalletSolidityGrpc.getGetTransactionsFromThisMethod) == null) {
      synchronized (WalletSolidityGrpc.class) {
        if ((getGetTransactionsFromThisMethod = WalletSolidityGrpc.getGetTransactionsFromThisMethod) == null) {
          WalletSolidityGrpc.getGetTransactionsFromThisMethod = getGetTransactionsFromThisMethod = 
              io.grpc.MethodDescriptor.<org.tron.protos.Protocol.Account, GrpcAPI.TransactionList>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "protocol.WalletSolidity", "GetTransactionsFromThis"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.tron.protos.Protocol.Account.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  GrpcAPI.TransactionList.getDefaultInstance()))
                  .setSchemaDescriptor(new WalletSolidityMethodDescriptorSupplier("GetTransactionsFromThis"))
                  .build();
          }
        }
     }
     return getGetTransactionsFromThisMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @Deprecated // Use {@link #getGetTransactionsToThisMethod()} instead.
  public static final io.grpc.MethodDescriptor<org.tron.protos.Protocol.Account,
      GrpcAPI.TransactionList> METHOD_GET_TRANSACTIONS_TO_THIS = getGetTransactionsToThisMethod();

  private static volatile io.grpc.MethodDescriptor<org.tron.protos.Protocol.Account,
      GrpcAPI.TransactionList> getGetTransactionsToThisMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<org.tron.protos.Protocol.Account,
      GrpcAPI.TransactionList> getGetTransactionsToThisMethod() {
    io.grpc.MethodDescriptor<org.tron.protos.Protocol.Account, GrpcAPI.TransactionList> getGetTransactionsToThisMethod;
    if ((getGetTransactionsToThisMethod = WalletSolidityGrpc.getGetTransactionsToThisMethod) == null) {
      synchronized (WalletSolidityGrpc.class) {
        if ((getGetTransactionsToThisMethod = WalletSolidityGrpc.getGetTransactionsToThisMethod) == null) {
          WalletSolidityGrpc.getGetTransactionsToThisMethod = getGetTransactionsToThisMethod = 
              io.grpc.MethodDescriptor.<org.tron.protos.Protocol.Account, GrpcAPI.TransactionList>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "protocol.WalletSolidity", "GetTransactionsToThis"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.tron.protos.Protocol.Account.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  GrpcAPI.TransactionList.getDefaultInstance()))
                  .setSchemaDescriptor(new WalletSolidityMethodDescriptorSupplier("GetTransactionsToThis"))
                  .build();
          }
        }
     }
     return getGetTransactionsToThisMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static WalletSolidityStub newStub(io.grpc.Channel channel) {
    return new WalletSolidityStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static WalletSolidityBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new WalletSolidityBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static WalletSolidityFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new WalletSolidityFutureStub(channel);
  }

  /**
   */
  public static abstract class WalletSolidityImplBase implements io.grpc.BindableService {

    /**
     */
    public void getAccount(org.tron.protos.Protocol.Account request,
        io.grpc.stub.StreamObserver<org.tron.protos.Protocol.Account> responseObserver) {
      asyncUnimplementedUnaryCall(getGetAccountMethod(), responseObserver);
    }

    /**
     */
    public void listAccounts(GrpcAPI.EmptyMessage request,
        io.grpc.stub.StreamObserver<GrpcAPI.AccountList> responseObserver) {
      asyncUnimplementedUnaryCall(getListAccountsMethod(), responseObserver);
    }

    /**
     */
    public void listWitnesses(GrpcAPI.EmptyMessage request,
        io.grpc.stub.StreamObserver<GrpcAPI.WitnessList> responseObserver) {
      asyncUnimplementedUnaryCall(getListWitnessesMethod(), responseObserver);
    }

    /**
     */
    public void getAssetIssueList(GrpcAPI.EmptyMessage request,
        io.grpc.stub.StreamObserver<GrpcAPI.AssetIssueList> responseObserver) {
      asyncUnimplementedUnaryCall(getGetAssetIssueListMethod(), responseObserver);
    }

    /**
     */
    public void getAssetIssueListByTimestamp(GrpcAPI.NumberMessage request,
        io.grpc.stub.StreamObserver<GrpcAPI.AssetIssueList> responseObserver) {
      asyncUnimplementedUnaryCall(getGetAssetIssueListByTimestampMethod(), responseObserver);
    }

    /**
     */
    public void getAssetIssueByAccount(org.tron.protos.Protocol.Account request,
        io.grpc.stub.StreamObserver<GrpcAPI.AssetIssueList> responseObserver) {
      asyncUnimplementedUnaryCall(getGetAssetIssueByAccountMethod(), responseObserver);
    }

    /**
     */
    public void getAssetIssueByName(GrpcAPI.BytesMessage request,
        io.grpc.stub.StreamObserver<org.tron.protos.Contract.AssetIssueContract> responseObserver) {
      asyncUnimplementedUnaryCall(getGetAssetIssueByNameMethod(), responseObserver);
    }

    /**
     */
    public void getNowBlock(GrpcAPI.EmptyMessage request,
        io.grpc.stub.StreamObserver<org.tron.protos.Protocol.Block> responseObserver) {
      asyncUnimplementedUnaryCall(getGetNowBlockMethod(), responseObserver);
    }

    /**
     */
    public void getBlockByNum(GrpcAPI.NumberMessage request,
        io.grpc.stub.StreamObserver<org.tron.protos.Protocol.Block> responseObserver) {
      asyncUnimplementedUnaryCall(getGetBlockByNumMethod(), responseObserver);
    }

    /**
     * <pre>
     *get transaction
     * </pre>
     */
    public void totalTransaction(GrpcAPI.EmptyMessage request,
        io.grpc.stub.StreamObserver<GrpcAPI.NumberMessage> responseObserver) {
      asyncUnimplementedUnaryCall(getTotalTransactionMethod(), responseObserver);
    }

    /**
     */
    public void getTransactionById(GrpcAPI.BytesMessage request,
        io.grpc.stub.StreamObserver<org.tron.protos.Protocol.Transaction> responseObserver) {
      asyncUnimplementedUnaryCall(getGetTransactionByIdMethod(), responseObserver);
    }

    /**
     */
    public void getTransactionsByTimestamp(GrpcAPI.TimeMessage request,
        io.grpc.stub.StreamObserver<GrpcAPI.TransactionList> responseObserver) {
      asyncUnimplementedUnaryCall(getGetTransactionsByTimestampMethod(), responseObserver);
    }

    /**
     */
    public void getTransactionsFromThis(org.tron.protos.Protocol.Account request,
        io.grpc.stub.StreamObserver<GrpcAPI.TransactionList> responseObserver) {
      asyncUnimplementedUnaryCall(getGetTransactionsFromThisMethod(), responseObserver);
    }

    /**
     */
    public void getTransactionsToThis(org.tron.protos.Protocol.Account request,
        io.grpc.stub.StreamObserver<GrpcAPI.TransactionList> responseObserver) {
      asyncUnimplementedUnaryCall(getGetTransactionsToThisMethod(), responseObserver);
    }

    @Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetAccountMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                org.tron.protos.Protocol.Account,
                org.tron.protos.Protocol.Account>(
                  this, METHODID_GET_ACCOUNT)))
          .addMethod(
            getListAccountsMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                GrpcAPI.EmptyMessage,
                GrpcAPI.AccountList>(
                  this, METHODID_LIST_ACCOUNTS)))
          .addMethod(
            getListWitnessesMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                GrpcAPI.EmptyMessage,
                GrpcAPI.WitnessList>(
                  this, METHODID_LIST_WITNESSES)))
          .addMethod(
            getGetAssetIssueListMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                GrpcAPI.EmptyMessage,
                GrpcAPI.AssetIssueList>(
                  this, METHODID_GET_ASSET_ISSUE_LIST)))
          .addMethod(
            getGetAssetIssueListByTimestampMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                GrpcAPI.NumberMessage,
                GrpcAPI.AssetIssueList>(
                  this, METHODID_GET_ASSET_ISSUE_LIST_BY_TIMESTAMP)))
          .addMethod(
            getGetAssetIssueByAccountMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                org.tron.protos.Protocol.Account,
                GrpcAPI.AssetIssueList>(
                  this, METHODID_GET_ASSET_ISSUE_BY_ACCOUNT)))
          .addMethod(
            getGetAssetIssueByNameMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                GrpcAPI.BytesMessage,
                org.tron.protos.Contract.AssetIssueContract>(
                  this, METHODID_GET_ASSET_ISSUE_BY_NAME)))
          .addMethod(
            getGetNowBlockMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                GrpcAPI.EmptyMessage,
                org.tron.protos.Protocol.Block>(
                  this, METHODID_GET_NOW_BLOCK)))
          .addMethod(
            getGetBlockByNumMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                GrpcAPI.NumberMessage,
                org.tron.protos.Protocol.Block>(
                  this, METHODID_GET_BLOCK_BY_NUM)))
          .addMethod(
            getTotalTransactionMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                GrpcAPI.EmptyMessage,
                GrpcAPI.NumberMessage>(
                  this, METHODID_TOTAL_TRANSACTION)))
          .addMethod(
            getGetTransactionByIdMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                GrpcAPI.BytesMessage,
                org.tron.protos.Protocol.Transaction>(
                  this, METHODID_GET_TRANSACTION_BY_ID)))
          .addMethod(
            getGetTransactionsByTimestampMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                GrpcAPI.TimeMessage,
                GrpcAPI.TransactionList>(
                  this, METHODID_GET_TRANSACTIONS_BY_TIMESTAMP)))
          .addMethod(
            getGetTransactionsFromThisMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                org.tron.protos.Protocol.Account,
                GrpcAPI.TransactionList>(
                  this, METHODID_GET_TRANSACTIONS_FROM_THIS)))
          .addMethod(
            getGetTransactionsToThisMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                org.tron.protos.Protocol.Account,
                GrpcAPI.TransactionList>(
                  this, METHODID_GET_TRANSACTIONS_TO_THIS)))
          .build();
    }
  }

  /**
   */
  public static final class WalletSolidityStub extends io.grpc.stub.AbstractStub<WalletSolidityStub> {
    private WalletSolidityStub(io.grpc.Channel channel) {
      super(channel);
    }

    private WalletSolidityStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected WalletSolidityStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new WalletSolidityStub(channel, callOptions);
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
    public void listAccounts(GrpcAPI.EmptyMessage request,
        io.grpc.stub.StreamObserver<GrpcAPI.AccountList> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getListAccountsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void listWitnesses(GrpcAPI.EmptyMessage request,
        io.grpc.stub.StreamObserver<GrpcAPI.WitnessList> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getListWitnessesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getAssetIssueList(GrpcAPI.EmptyMessage request,
        io.grpc.stub.StreamObserver<GrpcAPI.AssetIssueList> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetAssetIssueListMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getAssetIssueListByTimestamp(GrpcAPI.NumberMessage request,
        io.grpc.stub.StreamObserver<GrpcAPI.AssetIssueList> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetAssetIssueListByTimestampMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getAssetIssueByAccount(org.tron.protos.Protocol.Account request,
        io.grpc.stub.StreamObserver<GrpcAPI.AssetIssueList> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetAssetIssueByAccountMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getAssetIssueByName(GrpcAPI.BytesMessage request,
        io.grpc.stub.StreamObserver<org.tron.protos.Contract.AssetIssueContract> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetAssetIssueByNameMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getNowBlock(GrpcAPI.EmptyMessage request,
        io.grpc.stub.StreamObserver<org.tron.protos.Protocol.Block> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetNowBlockMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getBlockByNum(GrpcAPI.NumberMessage request,
        io.grpc.stub.StreamObserver<org.tron.protos.Protocol.Block> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetBlockByNumMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *get transaction
     * </pre>
     */
    public void totalTransaction(GrpcAPI.EmptyMessage request,
        io.grpc.stub.StreamObserver<GrpcAPI.NumberMessage> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getTotalTransactionMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getTransactionById(GrpcAPI.BytesMessage request,
        io.grpc.stub.StreamObserver<org.tron.protos.Protocol.Transaction> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetTransactionByIdMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getTransactionsByTimestamp(GrpcAPI.TimeMessage request,
        io.grpc.stub.StreamObserver<GrpcAPI.TransactionList> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetTransactionsByTimestampMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getTransactionsFromThis(org.tron.protos.Protocol.Account request,
        io.grpc.stub.StreamObserver<GrpcAPI.TransactionList> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetTransactionsFromThisMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getTransactionsToThis(org.tron.protos.Protocol.Account request,
        io.grpc.stub.StreamObserver<GrpcAPI.TransactionList> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetTransactionsToThisMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class WalletSolidityBlockingStub extends io.grpc.stub.AbstractStub<WalletSolidityBlockingStub> {
    private WalletSolidityBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private WalletSolidityBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected WalletSolidityBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new WalletSolidityBlockingStub(channel, callOptions);
    }

    /**
     */
    public org.tron.protos.Protocol.Account getAccount(org.tron.protos.Protocol.Account request) {
      return blockingUnaryCall(
          getChannel(), getGetAccountMethod(), getCallOptions(), request);
    }

    /**
     */
    public GrpcAPI.AccountList listAccounts(GrpcAPI.EmptyMessage request) {
      return blockingUnaryCall(
          getChannel(), getListAccountsMethod(), getCallOptions(), request);
    }

    /**
     */
    public GrpcAPI.WitnessList listWitnesses(GrpcAPI.EmptyMessage request) {
      return blockingUnaryCall(
          getChannel(), getListWitnessesMethod(), getCallOptions(), request);
    }

    /**
     */
    public GrpcAPI.AssetIssueList getAssetIssueList(GrpcAPI.EmptyMessage request) {
      return blockingUnaryCall(
          getChannel(), getGetAssetIssueListMethod(), getCallOptions(), request);
    }

    /**
     */
    public GrpcAPI.AssetIssueList getAssetIssueListByTimestamp(GrpcAPI.NumberMessage request) {
      return blockingUnaryCall(
          getChannel(), getGetAssetIssueListByTimestampMethod(), getCallOptions(), request);
    }

    /**
     */
    public GrpcAPI.AssetIssueList getAssetIssueByAccount(org.tron.protos.Protocol.Account request) {
      return blockingUnaryCall(
          getChannel(), getGetAssetIssueByAccountMethod(), getCallOptions(), request);
    }

    /**
     */
    public org.tron.protos.Contract.AssetIssueContract getAssetIssueByName(GrpcAPI.BytesMessage request) {
      return blockingUnaryCall(
          getChannel(), getGetAssetIssueByNameMethod(), getCallOptions(), request);
    }

    /**
     */
    public org.tron.protos.Protocol.Block getNowBlock(GrpcAPI.EmptyMessage request) {
      return blockingUnaryCall(
          getChannel(), getGetNowBlockMethod(), getCallOptions(), request);
    }

    /**
     */
    public org.tron.protos.Protocol.Block getBlockByNum(GrpcAPI.NumberMessage request) {
      return blockingUnaryCall(
          getChannel(), getGetBlockByNumMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     *get transaction
     * </pre>
     */
    public GrpcAPI.NumberMessage totalTransaction(GrpcAPI.EmptyMessage request) {
      return blockingUnaryCall(
          getChannel(), getTotalTransactionMethod(), getCallOptions(), request);
    }

    /**
     */
    public org.tron.protos.Protocol.Transaction getTransactionById(GrpcAPI.BytesMessage request) {
      return blockingUnaryCall(
          getChannel(), getGetTransactionByIdMethod(), getCallOptions(), request);
    }

    /**
     */
    public GrpcAPI.TransactionList getTransactionsByTimestamp(GrpcAPI.TimeMessage request) {
      return blockingUnaryCall(
          getChannel(), getGetTransactionsByTimestampMethod(), getCallOptions(), request);
    }

    /**
     */
    public GrpcAPI.TransactionList getTransactionsFromThis(org.tron.protos.Protocol.Account request) {
      return blockingUnaryCall(
          getChannel(), getGetTransactionsFromThisMethod(), getCallOptions(), request);
    }

    /**
     */
    public GrpcAPI.TransactionList getTransactionsToThis(org.tron.protos.Protocol.Account request) {
      return blockingUnaryCall(
          getChannel(), getGetTransactionsToThisMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class WalletSolidityFutureStub extends io.grpc.stub.AbstractStub<WalletSolidityFutureStub> {
    private WalletSolidityFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private WalletSolidityFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected WalletSolidityFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new WalletSolidityFutureStub(channel, callOptions);
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
    public com.google.common.util.concurrent.ListenableFuture<GrpcAPI.AccountList> listAccounts(
        GrpcAPI.EmptyMessage request) {
      return futureUnaryCall(
          getChannel().newCall(getListAccountsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<GrpcAPI.WitnessList> listWitnesses(
        GrpcAPI.EmptyMessage request) {
      return futureUnaryCall(
          getChannel().newCall(getListWitnessesMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<GrpcAPI.AssetIssueList> getAssetIssueList(
        GrpcAPI.EmptyMessage request) {
      return futureUnaryCall(
          getChannel().newCall(getGetAssetIssueListMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<GrpcAPI.AssetIssueList> getAssetIssueListByTimestamp(
        GrpcAPI.NumberMessage request) {
      return futureUnaryCall(
          getChannel().newCall(getGetAssetIssueListByTimestampMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<GrpcAPI.AssetIssueList> getAssetIssueByAccount(
        org.tron.protos.Protocol.Account request) {
      return futureUnaryCall(
          getChannel().newCall(getGetAssetIssueByAccountMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.tron.protos.Contract.AssetIssueContract> getAssetIssueByName(
        GrpcAPI.BytesMessage request) {
      return futureUnaryCall(
          getChannel().newCall(getGetAssetIssueByNameMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.tron.protos.Protocol.Block> getNowBlock(
        GrpcAPI.EmptyMessage request) {
      return futureUnaryCall(
          getChannel().newCall(getGetNowBlockMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.tron.protos.Protocol.Block> getBlockByNum(
        GrpcAPI.NumberMessage request) {
      return futureUnaryCall(
          getChannel().newCall(getGetBlockByNumMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     *get transaction
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<GrpcAPI.NumberMessage> totalTransaction(
        GrpcAPI.EmptyMessage request) {
      return futureUnaryCall(
          getChannel().newCall(getTotalTransactionMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.tron.protos.Protocol.Transaction> getTransactionById(
        GrpcAPI.BytesMessage request) {
      return futureUnaryCall(
          getChannel().newCall(getGetTransactionByIdMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<GrpcAPI.TransactionList> getTransactionsByTimestamp(
        GrpcAPI.TimeMessage request) {
      return futureUnaryCall(
          getChannel().newCall(getGetTransactionsByTimestampMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<GrpcAPI.TransactionList> getTransactionsFromThis(
        org.tron.protos.Protocol.Account request) {
      return futureUnaryCall(
          getChannel().newCall(getGetTransactionsFromThisMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<GrpcAPI.TransactionList> getTransactionsToThis(
        org.tron.protos.Protocol.Account request) {
      return futureUnaryCall(
          getChannel().newCall(getGetTransactionsToThisMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_ACCOUNT = 0;
  private static final int METHODID_LIST_ACCOUNTS = 1;
  private static final int METHODID_LIST_WITNESSES = 2;
  private static final int METHODID_GET_ASSET_ISSUE_LIST = 3;
  private static final int METHODID_GET_ASSET_ISSUE_LIST_BY_TIMESTAMP = 4;
  private static final int METHODID_GET_ASSET_ISSUE_BY_ACCOUNT = 5;
  private static final int METHODID_GET_ASSET_ISSUE_BY_NAME = 6;
  private static final int METHODID_GET_NOW_BLOCK = 7;
  private static final int METHODID_GET_BLOCK_BY_NUM = 8;
  private static final int METHODID_TOTAL_TRANSACTION = 9;
  private static final int METHODID_GET_TRANSACTION_BY_ID = 10;
  private static final int METHODID_GET_TRANSACTIONS_BY_TIMESTAMP = 11;
  private static final int METHODID_GET_TRANSACTIONS_FROM_THIS = 12;
  private static final int METHODID_GET_TRANSACTIONS_TO_THIS = 13;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final WalletSolidityImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(WalletSolidityImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_ACCOUNT:
          serviceImpl.getAccount((org.tron.protos.Protocol.Account) request,
              (io.grpc.stub.StreamObserver<org.tron.protos.Protocol.Account>) responseObserver);
          break;
        case METHODID_LIST_ACCOUNTS:
          serviceImpl.listAccounts((GrpcAPI.EmptyMessage) request,
              (io.grpc.stub.StreamObserver<GrpcAPI.AccountList>) responseObserver);
          break;
        case METHODID_LIST_WITNESSES:
          serviceImpl.listWitnesses((GrpcAPI.EmptyMessage) request,
              (io.grpc.stub.StreamObserver<GrpcAPI.WitnessList>) responseObserver);
          break;
        case METHODID_GET_ASSET_ISSUE_LIST:
          serviceImpl.getAssetIssueList((GrpcAPI.EmptyMessage) request,
              (io.grpc.stub.StreamObserver<GrpcAPI.AssetIssueList>) responseObserver);
          break;
        case METHODID_GET_ASSET_ISSUE_LIST_BY_TIMESTAMP:
          serviceImpl.getAssetIssueListByTimestamp((GrpcAPI.NumberMessage) request,
              (io.grpc.stub.StreamObserver<GrpcAPI.AssetIssueList>) responseObserver);
          break;
        case METHODID_GET_ASSET_ISSUE_BY_ACCOUNT:
          serviceImpl.getAssetIssueByAccount((org.tron.protos.Protocol.Account) request,
              (io.grpc.stub.StreamObserver<GrpcAPI.AssetIssueList>) responseObserver);
          break;
        case METHODID_GET_ASSET_ISSUE_BY_NAME:
          serviceImpl.getAssetIssueByName((GrpcAPI.BytesMessage) request,
              (io.grpc.stub.StreamObserver<org.tron.protos.Contract.AssetIssueContract>) responseObserver);
          break;
        case METHODID_GET_NOW_BLOCK:
          serviceImpl.getNowBlock((GrpcAPI.EmptyMessage) request,
              (io.grpc.stub.StreamObserver<org.tron.protos.Protocol.Block>) responseObserver);
          break;
        case METHODID_GET_BLOCK_BY_NUM:
          serviceImpl.getBlockByNum((GrpcAPI.NumberMessage) request,
              (io.grpc.stub.StreamObserver<org.tron.protos.Protocol.Block>) responseObserver);
          break;
        case METHODID_TOTAL_TRANSACTION:
          serviceImpl.totalTransaction((GrpcAPI.EmptyMessage) request,
              (io.grpc.stub.StreamObserver<GrpcAPI.NumberMessage>) responseObserver);
          break;
        case METHODID_GET_TRANSACTION_BY_ID:
          serviceImpl.getTransactionById((GrpcAPI.BytesMessage) request,
              (io.grpc.stub.StreamObserver<org.tron.protos.Protocol.Transaction>) responseObserver);
          break;
        case METHODID_GET_TRANSACTIONS_BY_TIMESTAMP:
          serviceImpl.getTransactionsByTimestamp((GrpcAPI.TimeMessage) request,
              (io.grpc.stub.StreamObserver<GrpcAPI.TransactionList>) responseObserver);
          break;
        case METHODID_GET_TRANSACTIONS_FROM_THIS:
          serviceImpl.getTransactionsFromThis((org.tron.protos.Protocol.Account) request,
              (io.grpc.stub.StreamObserver<GrpcAPI.TransactionList>) responseObserver);
          break;
        case METHODID_GET_TRANSACTIONS_TO_THIS:
          serviceImpl.getTransactionsToThis((org.tron.protos.Protocol.Account) request,
              (io.grpc.stub.StreamObserver<GrpcAPI.TransactionList>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @Override
    @SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class WalletSolidityBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    WalletSolidityBaseDescriptorSupplier() {}

    @Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return GrpcAPI.getDescriptor();
    }

    @Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("WalletSolidity");
    }
  }

  private static final class WalletSolidityFileDescriptorSupplier
      extends WalletSolidityBaseDescriptorSupplier {
    WalletSolidityFileDescriptorSupplier() {}
  }

  private static final class WalletSolidityMethodDescriptorSupplier
      extends WalletSolidityBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    WalletSolidityMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (WalletSolidityGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new WalletSolidityFileDescriptorSupplier())
              .addMethod(getGetAccountMethod())
              .addMethod(getListAccountsMethod())
              .addMethod(getListWitnessesMethod())
              .addMethod(getGetAssetIssueListMethod())
              .addMethod(getGetAssetIssueListByTimestampMethod())
              .addMethod(getGetAssetIssueByAccountMethod())
              .addMethod(getGetAssetIssueByNameMethod())
              .addMethod(getGetNowBlockMethod())
              .addMethod(getGetBlockByNumMethod())
              .addMethod(getTotalTransactionMethod())
              .addMethod(getGetTransactionByIdMethod())
              .addMethod(getGetTransactionsByTimestampMethod())
              .addMethod(getGetTransactionsFromThisMethod())
              .addMethod(getGetTransactionsToThisMethod())
              .build();
        }
      }
    }
    return result;
  }
}
