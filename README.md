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

2. Generate jar:

```shell
> ./gradlew build
> ./gradlew clean shadowJar -PmainClass=org.tron.program.RpcHunterSendCoin
> cp build/libs/org.tron.program.RpcHunterSendCoin jmeter/lib/ext/org.tron.program.RpcHunterSendCoin
> jmeter/bin/jmeter

```

3. Configuration

Jmeter:

[File] -> [Open] -> Select lever/src/main/resources/jmeter-setting.jmx

Modify [Thread Group] -> [Java Request]

1. **toAddress**: Receiver address.
2. **amount**: Number of transfers(SUN).
3. **privateKey**: Transferor private key.

Show:

<img src="/github/images/test-result.png?raw=true">