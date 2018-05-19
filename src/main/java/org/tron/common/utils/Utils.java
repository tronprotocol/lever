package org.tron.common.utils;

import org.tron.protos.Protocol;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.tron.service.WalletClient;

public class Utils {
  private static SecureRandom random = new SecureRandom();

  public static SecureRandom getRandom() {
    return random;
  }

  public static byte[] getBytes (char[] chars) {
    Charset cs = Charset.forName ("UTF-8");
    CharBuffer cb = CharBuffer.allocate (chars.length);
    cb.put (chars);
    cb.flip ();
    ByteBuffer bb = cs.encode (cb);

    return bb.array();
  }

  private char[] getChars (byte[] bytes) {
    Charset cs = Charset.forName ("UTF-8");
    ByteBuffer bb = ByteBuffer.allocate (bytes.length);
    bb.put (bytes);
    bb.flip ();
    CharBuffer cb = cs.decode (bb);

    return cb.array();
  }

  public static String printAccount(Protocol.Account account) {
    String result = "";
    result += "address: ";
    result += WalletClient.encode58Check(account.getAddress().toByteArray());
    result += "\n";
    if (account.getAccountName() != null && account.getAccountName().size() != 0) {
      result += "account_name: ";
      result += new String(account.getAccountName().toByteArray(), Charset.forName("UTF-8"));
      result += "\n";
    }
    result += "type: ";
    result += account.getTypeValue();
    result += "\n";
    result += "balance: ";
    result += account.getBalance();
    result += "\n";
    if (account.getFrozenCount() > 0) {
      for (Protocol.Account.Frozen frozen : account.getFrozenList()) {
        result += "frozen";
        result += "\n";
        result += "{";
        result += "\n";
        result += "  frozen_balance: ";
        result += frozen.getFrozenBalance();
        result += "\n";
        result += "  expire_time: ";
        result += new Date(frozen.getExpireTime());
        result += "\n";
        result += "}";
        result += "\n";
      }
    }
    result += "bandwidth: ";
    result += account.getBandwidth();
    result += "\n";
    if (account.getCreateTime() != 0) {
      result += "create_time: ";
      result += new Date(account.getCreateTime());
      result += "\n";
    }
    if (account.getVotesCount() > 0) {
      for (Protocol.Vote vote : account.getVotesList()) {
        result += "votes";
        result += "\n";
        result += "{";
        result += "\n";
        result += "  vote_address: ";
        result += WalletClient.encode58Check(vote.getVoteAddress().toByteArray());
        result += "\n";
        result += "  vote_count: ";
        result += vote.getVoteCount();
        result += "\n";
        result += "}";
        result += "\n";
      }
    }
    if (account.getAssetCount() > 0) {
      for (String name : account.getAssetMap().keySet()) {
        result += "asset";
        result += "\n";
        result += "{";
        result += "\n";
        result += "  name: ";
        result += name;
        result += "\n";
        result += "  balance: ";
        result += account.getAssetMap().get(name);
        result += "\n";
        result += "}";
        result += "\n";
      }
    }
    if (account.getFrozenSupplyCount() > 0) {
      for (Protocol.Account.Frozen frozen : account.getFrozenSupplyList()) {
        result += "frozen_supply";
        result += "\n";
        result += "{";
        result += "\n";
        result += "  amount: ";
        result += frozen.getFrozenBalance();
        result += "\n";
        result += "  expire_time: ";
        result += new Date(frozen.getExpireTime());
        result += "\n";
        result += "}";
        result += "\n";
      }
    }
    result += "latest_opration_time: ";
    result += new  Date(account.getLatestOprationTime());
    result += "\n";

    result += "allowance: ";
    result += account.getAllowance();
    result += "\n";

    result += "latest_withdraw_time: ";
    result += new  Date(account.getLatestWithdrawTime());
    result += "\n";

//    result += "code: ";
//    result += account.getCode();
//    result += "\n";
//
//    result += "is_witness: ";
//    result += account.getIsWitness();
//    result += "\n";
//
//    result += "is_committee: ";
//    result += account.getIsCommittee();
//    result += "\n";

    return result;
  }

  /**
   * yyyy-MM-dd
   *
   * @param strDate
   * @return
   */
  public static Date strToDateLong(String strDate) {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    ParsePosition pos = new ParsePosition(0);
    Date strtodate = formatter.parse(strDate, pos);
    return strtodate;
  }
}
