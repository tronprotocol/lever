<img src="/github/images/lever.png?raw=true">

> Stress testing for java-tron's gRPC

## lever

lever is a stress testing tool for [java-tron].

[java-tron]:https://github.com/tronprotocol/java-tron

## How to use?

### Run a private blockchain of java-tron

Read the [Start Private Blockchain]. Two accounts need to be reserved, one for initiating the transfer and one for receiving the transfer amount.

[Start Private Blockchain]:http://wiki.tron.network/en/latest/start_private_blockchain.html

### Jmeter

1. Install Jmeter.

### lever

1. Generate jar and run test:

```shell
$ git clone https://github.com/tronprotocol/lever.git
$ cd lever
$ ./gradlew build
$ ./gradlew clean shadowJar -PmainClass=org.tron.program.RpcHunterSendCoin
$ cp build/libs/org.tron.program.RpcHunterSendCoin.jar jmeter/lib/ext/org.tron.program.RpcHunterSendCoin.jar
$ cp src/main/resources/jmeter-setting.jmx jmeter/bin/
$ cd jmeter/bin
$ ﻿./jmeter -n -t /jmeter-setting.jmx -l test_result.jtl -e -o /resultReport

```

2. Configuration

2.1 config.conf

`lever/src/main/resources/config.conf`：

```shell
grpc.address = "127.0.0.1:50051" # target gRPC server address
```

2.2 jmeter-setting.jmx

`lever/src/main/resources/jmeter-setting.jmx`：

10 threads running for 1800 seconds：

```xml
  <ThreadGroup guiclass="ThreadGroupGui" testclass="ThreadGroup" testname="Thread Group" enabled="true">
    <stringProp name="ThreadGroup.on_sample_error">continue</stringProp>
    <elementProp name="ThreadGroup.main_controller" elementType="LoopController" guiclass="LoopControlPanel" testclass="LoopController" testname="Loop Controller" enabled="true">
      <boolProp name="LoopController.continue_forever">false</boolProp>
      <intProp name="LoopController.loops">-1</intProp>
    </elementProp>
    <stringProp name="ThreadGroup.num_threads">10</stringProp>
    <stringProp name="ThreadGroup.ramp_time">1</stringProp>
    <boolProp name="ThreadGroup.scheduler">true</boolProp>
    <stringProp name="ThreadGroup.duration">1800</stringProp>
    <stringProp name="ThreadGroup.delay"></stringProp>
  </ThreadGroup>
```

Transaction arguments(put your arguments: `Argument.value` to this file):

```xml
<JavaSampler guiclass="JavaTestSamplerGui" testclass="JavaSampler" testname="Java Request" enabled="true">
  <elementProp name="arguments" elementType="Arguments" guiclass="ArgumentsPanel" testclass="Arguments" enabled="true">
    <collectionProp name="Arguments.arguments">
      <elementProp name="toAddress" elementType="Argument">
        <stringProp name="Argument.name">toAddress</stringProp>
        <stringProp name="Argument.value">27d3byPxZXKQWfXX7sJvemJJuv5M65F3vjS</stringProp>
        <stringProp name="Argument.metadata">=</stringProp>
      </elementProp>
      <elementProp name="amount" elementType="Argument">
        <stringProp name="Argument.name">amount</stringProp>
        <stringProp name="Argument.value">1</stringProp>
        <stringProp name="Argument.metadata">=</stringProp>
      </elementProp>
      <elementProp name="privateKey" elementType="Argument">
        <stringProp name="Argument.name">privateKey</stringProp>
        <stringProp name="Argument.value">effa55b420a2fe39e3f73d14b8c46824fd0d5ee210840b9c27b2e2f42a09f1f9</stringProp>
        <stringProp name="Argument.metadata">=</stringProp>
      </elementProp>
    </collectionProp>
  </elementProp>
  <stringProp name="classname">org.tron.program.RpcHunterSendCoin</stringProp>
</JavaSampler>
```

Throughout setting(60/s):

```xml
<ConstantThroughputTimer guiclass="TestBeanGUI" testclass="ConstantThroughputTimer" testname="Constant Throughput Timer" enabled="true">
  <doubleProp>
    <name>throughput</name>
    <value>60.0</value>
    <savedValue>0.0</savedValue>
  </doubleProp>
  <intProp name="calcMode">1</intProp>
</ConstantThroughputTimer>
```

You can use [generate-account-tool](https://github.com/sasaxie/generate-account-tool) to generate your account and put it to java-tron's config.conf: `genesis.block.assets`, `genesis.block.witnesses`, `localwitness`