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

2. Generate jar and run test:

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

3. Configuration

`lever/src/main/resources/config.conf`：

```shell
grpc.address = "127.0.0.1:50051" # target gRPC server address
```

`lever/src/main/resources/jmeter-setting.jmx`：

10 threads run 1800 seconds：

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
