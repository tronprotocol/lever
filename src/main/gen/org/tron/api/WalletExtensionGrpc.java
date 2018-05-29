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
public final class WalletExtensionGrpc {

  private WalletExtensionGrpc() {}

  public static final String SERVICE_NAME = "protocol.WalletExtension";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getGetTransactionsByTimestampMethod()} instead. 
  public static final io.grpc.MethodDescriptor<org.tron.api.GrpcAPI.TimePaginatedMessage,
      org.tron.api.GrpcAPI.TransactionList> METHOD_GET_TRANSACTIONS_BY_TIMESTAMP = getGetTransactionsByTimestampMethod();

  private static volatile io.grpc.MethodDescriptor<org.tron.api.GrpcAPI.TimePaginatedMessage,
      org.tron.api.GrpcAPI.TransactionList> getGetTransactionsByTimestampMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<org.tron.api.GrpcAPI.TimePaginatedMessage,
      org.tron.api.GrpcAPI.TransactionList> getGetTransactionsByTimestampMethod() {
    io.grpc.MethodDescriptor<org.tron.api.GrpcAPI.TimePaginatedMessage, org.tron.api.GrpcAPI.TransactionList> getGetTransactionsByTimestampMethod;
    if ((getGetTransactionsByTimestampMethod = WalletExtensionGrpc.getGetTransactionsByTimestampMethod) == null) {
      synchronized (WalletExtensionGrpc.class) {
        if ((getGetTransactionsByTimestampMethod = WalletExtensionGrpc.getGetTransactionsByTimestampMethod) == null) {
          WalletExtensionGrpc.getGetTransactionsByTimestampMethod = getGetTransactionsByTimestampMethod = 
              io.grpc.MethodDescriptor.<org.tron.api.GrpcAPI.TimePaginatedMessage, org.tron.api.GrpcAPI.TransactionList>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "protocol.WalletExtension", "GetTransactionsByTimestamp"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.tron.api.GrpcAPI.TimePaginatedMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.tron.api.GrpcAPI.TransactionList.getDefaultInstance()))
                  .setSchemaDescriptor(new WalletExtensionMethodDescriptorSupplier("GetTransactionsByTimestamp"))
                  .build();
          }
        }
     }
     return getGetTransactionsByTimestampMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getGetTransactionsByTimestampCountMethod()} instead. 
  public static final io.grpc.MethodDescriptor<org.tron.api.GrpcAPI.TimeMessage,
      org.tron.api.GrpcAPI.NumberMessage> METHOD_GET_TRANSACTIONS_BY_TIMESTAMP_COUNT = getGetTransactionsByTimestampCountMethod();

  private static volatile io.grpc.MethodDescriptor<org.tron.api.GrpcAPI.TimeMessage,
      org.tron.api.GrpcAPI.NumberMessage> getGetTransactionsByTimestampCountMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<org.tron.api.GrpcAPI.TimeMessage,
      org.tron.api.GrpcAPI.NumberMessage> getGetTransactionsByTimestampCountMethod() {
    io.grpc.MethodDescriptor<org.tron.api.GrpcAPI.TimeMessage, org.tron.api.GrpcAPI.NumberMessage> getGetTransactionsByTimestampCountMethod;
    if ((getGetTransactionsByTimestampCountMethod = WalletExtensionGrpc.getGetTransactionsByTimestampCountMethod) == null) {
      synchronized (WalletExtensionGrpc.class) {
        if ((getGetTransactionsByTimestampCountMethod = WalletExtensionGrpc.getGetTransactionsByTimestampCountMethod) == null) {
          WalletExtensionGrpc.getGetTransactionsByTimestampCountMethod = getGetTransactionsByTimestampCountMethod = 
              io.grpc.MethodDescriptor.<org.tron.api.GrpcAPI.TimeMessage, org.tron.api.GrpcAPI.NumberMessage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "protocol.WalletExtension", "GetTransactionsByTimestampCount"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.tron.api.GrpcAPI.TimeMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.tron.api.GrpcAPI.NumberMessage.getDefaultInstance()))
                  .setSchemaDescriptor(new WalletExtensionMethodDescriptorSupplier("GetTransactionsByTimestampCount"))
                  .build();
          }
        }
     }
     return getGetTransactionsByTimestampCountMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getGetTransactionsFromThisMethod()} instead. 
  public static final io.grpc.MethodDescriptor<org.tron.api.GrpcAPI.AccountPaginated,
      org.tron.api.GrpcAPI.TransactionList> METHOD_GET_TRANSACTIONS_FROM_THIS = getGetTransactionsFromThisMethod();

  private static volatile io.grpc.MethodDescriptor<org.tron.api.GrpcAPI.AccountPaginated,
      org.tron.api.GrpcAPI.TransactionList> getGetTransactionsFromThisMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<org.tron.api.GrpcAPI.AccountPaginated,
      org.tron.api.GrpcAPI.TransactionList> getGetTransactionsFromThisMethod() {
    io.grpc.MethodDescriptor<org.tron.api.GrpcAPI.AccountPaginated, org.tron.api.GrpcAPI.TransactionList> getGetTransactionsFromThisMethod;
    if ((getGetTransactionsFromThisMethod = WalletExtensionGrpc.getGetTransactionsFromThisMethod) == null) {
      synchronized (WalletExtensionGrpc.class) {
        if ((getGetTransactionsFromThisMethod = WalletExtensionGrpc.getGetTransactionsFromThisMethod) == null) {
          WalletExtensionGrpc.getGetTransactionsFromThisMethod = getGetTransactionsFromThisMethod = 
              io.grpc.MethodDescriptor.<org.tron.api.GrpcAPI.AccountPaginated, org.tron.api.GrpcAPI.TransactionList>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "protocol.WalletExtension", "GetTransactionsFromThis"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.tron.api.GrpcAPI.AccountPaginated.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.tron.api.GrpcAPI.TransactionList.getDefaultInstance()))
                  .setSchemaDescriptor(new WalletExtensionMethodDescriptorSupplier("GetTransactionsFromThis"))
                  .build();
          }
        }
     }
     return getGetTransactionsFromThisMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getGetTransactionsToThisMethod()} instead. 
  public static final io.grpc.MethodDescriptor<org.tron.api.GrpcAPI.AccountPaginated,
      org.tron.api.GrpcAPI.TransactionList> METHOD_GET_TRANSACTIONS_TO_THIS = getGetTransactionsToThisMethod();

  private static volatile io.grpc.MethodDescriptor<org.tron.api.GrpcAPI.AccountPaginated,
      org.tron.api.GrpcAPI.TransactionList> getGetTransactionsToThisMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<org.tron.api.GrpcAPI.AccountPaginated,
      org.tron.api.GrpcAPI.TransactionList> getGetTransactionsToThisMethod() {
    io.grpc.MethodDescriptor<org.tron.api.GrpcAPI.AccountPaginated, org.tron.api.GrpcAPI.TransactionList> getGetTransactionsToThisMethod;
    if ((getGetTransactionsToThisMethod = WalletExtensionGrpc.getGetTransactionsToThisMethod) == null) {
      synchronized (WalletExtensionGrpc.class) {
        if ((getGetTransactionsToThisMethod = WalletExtensionGrpc.getGetTransactionsToThisMethod) == null) {
          WalletExtensionGrpc.getGetTransactionsToThisMethod = getGetTransactionsToThisMethod = 
              io.grpc.MethodDescriptor.<org.tron.api.GrpcAPI.AccountPaginated, org.tron.api.GrpcAPI.TransactionList>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "protocol.WalletExtension", "GetTransactionsToThis"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.tron.api.GrpcAPI.AccountPaginated.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.tron.api.GrpcAPI.TransactionList.getDefaultInstance()))
                  .setSchemaDescriptor(new WalletExtensionMethodDescriptorSupplier("GetTransactionsToThis"))
                  .build();
          }
        }
     }
     return getGetTransactionsToThisMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getGetTransactionsFromThisCountMethod()} instead. 
  public static final io.grpc.MethodDescriptor<org.tron.protos.Protocol.Account,
      org.tron.api.GrpcAPI.NumberMessage> METHOD_GET_TRANSACTIONS_FROM_THIS_COUNT = getGetTransactionsFromThisCountMethod();

  private static volatile io.grpc.MethodDescriptor<org.tron.protos.Protocol.Account,
      org.tron.api.GrpcAPI.NumberMessage> getGetTransactionsFromThisCountMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<org.tron.protos.Protocol.Account,
      org.tron.api.GrpcAPI.NumberMessage> getGetTransactionsFromThisCountMethod() {
    io.grpc.MethodDescriptor<org.tron.protos.Protocol.Account, org.tron.api.GrpcAPI.NumberMessage> getGetTransactionsFromThisCountMethod;
    if ((getGetTransactionsFromThisCountMethod = WalletExtensionGrpc.getGetTransactionsFromThisCountMethod) == null) {
      synchronized (WalletExtensionGrpc.class) {
        if ((getGetTransactionsFromThisCountMethod = WalletExtensionGrpc.getGetTransactionsFromThisCountMethod) == null) {
          WalletExtensionGrpc.getGetTransactionsFromThisCountMethod = getGetTransactionsFromThisCountMethod = 
              io.grpc.MethodDescriptor.<org.tron.protos.Protocol.Account, org.tron.api.GrpcAPI.NumberMessage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "protocol.WalletExtension", "GetTransactionsFromThisCount"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.tron.protos.Protocol.Account.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.tron.api.GrpcAPI.NumberMessage.getDefaultInstance()))
                  .setSchemaDescriptor(new WalletExtensionMethodDescriptorSupplier("GetTransactionsFromThisCount"))
                  .build();
          }
        }
     }
     return getGetTransactionsFromThisCountMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getGetTransactionsToThisCountMethod()} instead. 
  public static final io.grpc.MethodDescriptor<org.tron.protos.Protocol.Account,
      org.tron.api.GrpcAPI.NumberMessage> METHOD_GET_TRANSACTIONS_TO_THIS_COUNT = getGetTransactionsToThisCountMethod();

  private static volatile io.grpc.MethodDescriptor<org.tron.protos.Protocol.Account,
      org.tron.api.GrpcAPI.NumberMessage> getGetTransactionsToThisCountMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<org.tron.protos.Protocol.Account,
      org.tron.api.GrpcAPI.NumberMessage> getGetTransactionsToThisCountMethod() {
    io.grpc.MethodDescriptor<org.tron.protos.Protocol.Account, org.tron.api.GrpcAPI.NumberMessage> getGetTransactionsToThisCountMethod;
    if ((getGetTransactionsToThisCountMethod = WalletExtensionGrpc.getGetTransactionsToThisCountMethod) == null) {
      synchronized (WalletExtensionGrpc.class) {
        if ((getGetTransactionsToThisCountMethod = WalletExtensionGrpc.getGetTransactionsToThisCountMethod) == null) {
          WalletExtensionGrpc.getGetTransactionsToThisCountMethod = getGetTransactionsToThisCountMethod = 
              io.grpc.MethodDescriptor.<org.tron.protos.Protocol.Account, org.tron.api.GrpcAPI.NumberMessage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "protocol.WalletExtension", "GetTransactionsToThisCount"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.tron.protos.Protocol.Account.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.tron.api.GrpcAPI.NumberMessage.getDefaultInstance()))
                  .setSchemaDescriptor(new WalletExtensionMethodDescriptorSupplier("GetTransactionsToThisCount"))
                  .build();
          }
        }
     }
     return getGetTransactionsToThisCountMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static WalletExtensionStub newStub(io.grpc.Channel channel) {
    return new WalletExtensionStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static WalletExtensionBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new WalletExtensionBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static WalletExtensionFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new WalletExtensionFutureStub(channel);
  }

  /**
   */
  public static abstract class WalletExtensionImplBase implements io.grpc.BindableService {

    /**
     */
    public void getTransactionsByTimestamp(org.tron.api.GrpcAPI.TimePaginatedMessage request,
        io.grpc.stub.StreamObserver<org.tron.api.GrpcAPI.TransactionList> responseObserver) {
      asyncUnimplementedUnaryCall(getGetTransactionsByTimestampMethod(), responseObserver);
    }

    /**
     */
    public void getTransactionsByTimestampCount(org.tron.api.GrpcAPI.TimeMessage request,
        io.grpc.stub.StreamObserver<org.tron.api.GrpcAPI.NumberMessage> responseObserver) {
      asyncUnimplementedUnaryCall(getGetTransactionsByTimestampCountMethod(), responseObserver);
    }

    /**
     */
    public void getTransactionsFromThis(org.tron.api.GrpcAPI.AccountPaginated request,
        io.grpc.stub.StreamObserver<org.tron.api.GrpcAPI.TransactionList> responseObserver) {
      asyncUnimplementedUnaryCall(getGetTransactionsFromThisMethod(), responseObserver);
    }

    /**
     */
    public void getTransactionsToThis(org.tron.api.GrpcAPI.AccountPaginated request,
        io.grpc.stub.StreamObserver<org.tron.api.GrpcAPI.TransactionList> responseObserver) {
      asyncUnimplementedUnaryCall(getGetTransactionsToThisMethod(), responseObserver);
    }

    /**
     */
    public void getTransactionsFromThisCount(org.tron.protos.Protocol.Account request,
        io.grpc.stub.StreamObserver<org.tron.api.GrpcAPI.NumberMessage> responseObserver) {
      asyncUnimplementedUnaryCall(getGetTransactionsFromThisCountMethod(), responseObserver);
    }

    /**
     */
    public void getTransactionsToThisCount(org.tron.protos.Protocol.Account request,
        io.grpc.stub.StreamObserver<org.tron.api.GrpcAPI.NumberMessage> responseObserver) {
      asyncUnimplementedUnaryCall(getGetTransactionsToThisCountMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetTransactionsByTimestampMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                org.tron.api.GrpcAPI.TimePaginatedMessage,
                org.tron.api.GrpcAPI.TransactionList>(
                  this, METHODID_GET_TRANSACTIONS_BY_TIMESTAMP)))
          .addMethod(
            getGetTransactionsByTimestampCountMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                org.tron.api.GrpcAPI.TimeMessage,
                org.tron.api.GrpcAPI.NumberMessage>(
                  this, METHODID_GET_TRANSACTIONS_BY_TIMESTAMP_COUNT)))
          .addMethod(
            getGetTransactionsFromThisMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                org.tron.api.GrpcAPI.AccountPaginated,
                org.tron.api.GrpcAPI.TransactionList>(
                  this, METHODID_GET_TRANSACTIONS_FROM_THIS)))
          .addMethod(
            getGetTransactionsToThisMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                org.tron.api.GrpcAPI.AccountPaginated,
                org.tron.api.GrpcAPI.TransactionList>(
                  this, METHODID_GET_TRANSACTIONS_TO_THIS)))
          .addMethod(
            getGetTransactionsFromThisCountMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                org.tron.protos.Protocol.Account,
                org.tron.api.GrpcAPI.NumberMessage>(
                  this, METHODID_GET_TRANSACTIONS_FROM_THIS_COUNT)))
          .addMethod(
            getGetTransactionsToThisCountMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                org.tron.protos.Protocol.Account,
                org.tron.api.GrpcAPI.NumberMessage>(
                  this, METHODID_GET_TRANSACTIONS_TO_THIS_COUNT)))
          .build();
    }
  }

  /**
   */
  public static final class WalletExtensionStub extends io.grpc.stub.AbstractStub<WalletExtensionStub> {
    private WalletExtensionStub(io.grpc.Channel channel) {
      super(channel);
    }

    private WalletExtensionStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected WalletExtensionStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new WalletExtensionStub(channel, callOptions);
    }

    /**
     */
    public void getTransactionsByTimestamp(org.tron.api.GrpcAPI.TimePaginatedMessage request,
        io.grpc.stub.StreamObserver<org.tron.api.GrpcAPI.TransactionList> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetTransactionsByTimestampMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getTransactionsByTimestampCount(org.tron.api.GrpcAPI.TimeMessage request,
        io.grpc.stub.StreamObserver<org.tron.api.GrpcAPI.NumberMessage> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetTransactionsByTimestampCountMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getTransactionsFromThis(org.tron.api.GrpcAPI.AccountPaginated request,
        io.grpc.stub.StreamObserver<org.tron.api.GrpcAPI.TransactionList> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetTransactionsFromThisMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getTransactionsToThis(org.tron.api.GrpcAPI.AccountPaginated request,
        io.grpc.stub.StreamObserver<org.tron.api.GrpcAPI.TransactionList> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetTransactionsToThisMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getTransactionsFromThisCount(org.tron.protos.Protocol.Account request,
        io.grpc.stub.StreamObserver<org.tron.api.GrpcAPI.NumberMessage> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetTransactionsFromThisCountMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getTransactionsToThisCount(org.tron.protos.Protocol.Account request,
        io.grpc.stub.StreamObserver<org.tron.api.GrpcAPI.NumberMessage> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetTransactionsToThisCountMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class WalletExtensionBlockingStub extends io.grpc.stub.AbstractStub<WalletExtensionBlockingStub> {
    private WalletExtensionBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private WalletExtensionBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected WalletExtensionBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new WalletExtensionBlockingStub(channel, callOptions);
    }

    /**
     */
    public org.tron.api.GrpcAPI.TransactionList getTransactionsByTimestamp(org.tron.api.GrpcAPI.TimePaginatedMessage request) {
      return blockingUnaryCall(
          getChannel(), getGetTransactionsByTimestampMethod(), getCallOptions(), request);
    }

    /**
     */
    public org.tron.api.GrpcAPI.NumberMessage getTransactionsByTimestampCount(org.tron.api.GrpcAPI.TimeMessage request) {
      return blockingUnaryCall(
          getChannel(), getGetTransactionsByTimestampCountMethod(), getCallOptions(), request);
    }

    /**
     */
    public org.tron.api.GrpcAPI.TransactionList getTransactionsFromThis(org.tron.api.GrpcAPI.AccountPaginated request) {
      return blockingUnaryCall(
          getChannel(), getGetTransactionsFromThisMethod(), getCallOptions(), request);
    }

    /**
     */
    public org.tron.api.GrpcAPI.TransactionList getTransactionsToThis(org.tron.api.GrpcAPI.AccountPaginated request) {
      return blockingUnaryCall(
          getChannel(), getGetTransactionsToThisMethod(), getCallOptions(), request);
    }

    /**
     */
    public org.tron.api.GrpcAPI.NumberMessage getTransactionsFromThisCount(org.tron.protos.Protocol.Account request) {
      return blockingUnaryCall(
          getChannel(), getGetTransactionsFromThisCountMethod(), getCallOptions(), request);
    }

    /**
     */
    public org.tron.api.GrpcAPI.NumberMessage getTransactionsToThisCount(org.tron.protos.Protocol.Account request) {
      return blockingUnaryCall(
          getChannel(), getGetTransactionsToThisCountMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class WalletExtensionFutureStub extends io.grpc.stub.AbstractStub<WalletExtensionFutureStub> {
    private WalletExtensionFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private WalletExtensionFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected WalletExtensionFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new WalletExtensionFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.tron.api.GrpcAPI.TransactionList> getTransactionsByTimestamp(
        org.tron.api.GrpcAPI.TimePaginatedMessage request) {
      return futureUnaryCall(
          getChannel().newCall(getGetTransactionsByTimestampMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.tron.api.GrpcAPI.NumberMessage> getTransactionsByTimestampCount(
        org.tron.api.GrpcAPI.TimeMessage request) {
      return futureUnaryCall(
          getChannel().newCall(getGetTransactionsByTimestampCountMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.tron.api.GrpcAPI.TransactionList> getTransactionsFromThis(
        org.tron.api.GrpcAPI.AccountPaginated request) {
      return futureUnaryCall(
          getChannel().newCall(getGetTransactionsFromThisMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.tron.api.GrpcAPI.TransactionList> getTransactionsToThis(
        org.tron.api.GrpcAPI.AccountPaginated request) {
      return futureUnaryCall(
          getChannel().newCall(getGetTransactionsToThisMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.tron.api.GrpcAPI.NumberMessage> getTransactionsFromThisCount(
        org.tron.protos.Protocol.Account request) {
      return futureUnaryCall(
          getChannel().newCall(getGetTransactionsFromThisCountMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.tron.api.GrpcAPI.NumberMessage> getTransactionsToThisCount(
        org.tron.protos.Protocol.Account request) {
      return futureUnaryCall(
          getChannel().newCall(getGetTransactionsToThisCountMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_TRANSACTIONS_BY_TIMESTAMP = 0;
  private static final int METHODID_GET_TRANSACTIONS_BY_TIMESTAMP_COUNT = 1;
  private static final int METHODID_GET_TRANSACTIONS_FROM_THIS = 2;
  private static final int METHODID_GET_TRANSACTIONS_TO_THIS = 3;
  private static final int METHODID_GET_TRANSACTIONS_FROM_THIS_COUNT = 4;
  private static final int METHODID_GET_TRANSACTIONS_TO_THIS_COUNT = 5;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final WalletExtensionImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(WalletExtensionImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_TRANSACTIONS_BY_TIMESTAMP:
          serviceImpl.getTransactionsByTimestamp((org.tron.api.GrpcAPI.TimePaginatedMessage) request,
              (io.grpc.stub.StreamObserver<org.tron.api.GrpcAPI.TransactionList>) responseObserver);
          break;
        case METHODID_GET_TRANSACTIONS_BY_TIMESTAMP_COUNT:
          serviceImpl.getTransactionsByTimestampCount((org.tron.api.GrpcAPI.TimeMessage) request,
              (io.grpc.stub.StreamObserver<org.tron.api.GrpcAPI.NumberMessage>) responseObserver);
          break;
        case METHODID_GET_TRANSACTIONS_FROM_THIS:
          serviceImpl.getTransactionsFromThis((org.tron.api.GrpcAPI.AccountPaginated) request,
              (io.grpc.stub.StreamObserver<org.tron.api.GrpcAPI.TransactionList>) responseObserver);
          break;
        case METHODID_GET_TRANSACTIONS_TO_THIS:
          serviceImpl.getTransactionsToThis((org.tron.api.GrpcAPI.AccountPaginated) request,
              (io.grpc.stub.StreamObserver<org.tron.api.GrpcAPI.TransactionList>) responseObserver);
          break;
        case METHODID_GET_TRANSACTIONS_FROM_THIS_COUNT:
          serviceImpl.getTransactionsFromThisCount((org.tron.protos.Protocol.Account) request,
              (io.grpc.stub.StreamObserver<org.tron.api.GrpcAPI.NumberMessage>) responseObserver);
          break;
        case METHODID_GET_TRANSACTIONS_TO_THIS_COUNT:
          serviceImpl.getTransactionsToThisCount((org.tron.protos.Protocol.Account) request,
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

  private static abstract class WalletExtensionBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    WalletExtensionBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return org.tron.api.GrpcAPI.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("WalletExtension");
    }
  }

  private static final class WalletExtensionFileDescriptorSupplier
      extends WalletExtensionBaseDescriptorSupplier {
    WalletExtensionFileDescriptorSupplier() {}
  }

  private static final class WalletExtensionMethodDescriptorSupplier
      extends WalletExtensionBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    WalletExtensionMethodDescriptorSupplier(String methodName) {
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
      synchronized (WalletExtensionGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new WalletExtensionFileDescriptorSupplier())
              .addMethod(getGetTransactionsByTimestampMethod())
              .addMethod(getGetTransactionsByTimestampCountMethod())
              .addMethod(getGetTransactionsFromThisMethod())
              .addMethod(getGetTransactionsToThisMethod())
              .addMethod(getGetTransactionsFromThisCountMethod())
              .addMethod(getGetTransactionsToThisCountMethod())
              .build();
        }
      }
    }
    return result;
  }
}
