package hy.ea.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密
 * @author yaloo
 * 
 */
public class MD5Util {
 @SuppressWarnings("unused")
private static String str;

 public static String md5s(String plainText) {
  try {
   MessageDigest md = MessageDigest.getInstance("MD5");
   md.update(plainText.getBytes());
   byte b[] = md.digest();

   int i;

   StringBuffer buf = new StringBuffer("");
   for (int offset = 0; offset < b.length; offset++) {
    i = b[offset];
    if (i < 0)
     i += 256;
    if (i < 16)
     buf.append("0");
    buf.append(Integer.toHexString(i));
   }
   str = buf.toString();
   return buf.toString();//32位的加密
  } catch (NoSuchAlgorithmException e) {
   e.printStackTrace();

  }
  return "";
 }

 public static void main(String agrs[]) { 
	 String md5Password =  MD5Util.md5s("12345");
	 System.out.println(md5Password);
 }

}