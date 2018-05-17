package org.tron.program;

import com.beust.jcommander.JCommander;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;
import lombok.Data;
import org.tron.common.utils.Base58;
import org.tron.common.utils.Sha256Hash;
import org.tron.protos.Protocol;
import org.tron.service.WalletClient;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class CheckAddress {

  public static void main(String[] args) {
    List<String> address = ImmutableList.of(
        "a0904fe896536f4bebc64c95326b5054a2c3d27df6",
        "a0807337f180b62a77576377c1d0c9c24df5c0dd62",
        "a05430a3f089154e9e182ddd6fe136a62321af22a7",
        "a08beaa1a8e2d45367af7bae7c490b9932a4fa4301",
        "a0b070b2b58f4328e293dc9d6012f59c263d3a1df6",
        "a00a9309758508413039e4bc5a3d113f3ecc55031d",
        "a06a17a49648a8ad32055c06f60fa14ae46df94cc1",
        "a0ec6525979a351a54fa09fea64beb4cce33ffbb7a",
        "a0fab5fbf6afb681e4e37e9d33bddb7e923d6132e5",
        "a014eebe4d30a6acb505c8b00b218bdc4733433c

        .map(s -> Base58.encode58Check(s.getBytes()))
        .forEach(System.out::println);
  }


}
