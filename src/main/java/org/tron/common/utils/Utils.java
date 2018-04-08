package org.tron.common.utils;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

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
