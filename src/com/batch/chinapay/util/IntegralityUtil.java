package com.batch.chinapay.util;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class IntegralityUtil {

	private static MessageDigest alg;
	private static int length;
	private static int maxBytesReadPerTime = 8192;

	static {
		try {
			alg = MessageDigest.getInstance("MD5");
			length = alg.getDigestLength();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	public static byte[] getMD5(InputStream is, long fileLength)
			throws Exception {

		byte[] bytes = new byte[maxBytesReadPerTime];
		int bytesRead = 0;
		int maxReadTimesBeforeRedigest = 100;
		byte[] digestOfPart = new byte[length];
		byte[] digest = new byte[length * maxReadTimesBeforeRedigest];
		int timesRead = 0;
		byte[] digestToReturn = new byte[length];

		try {
			while (fileLength > 0) {
				bytesRead = is.read(bytes);
				digestOfPart = getMD5(bytes, 0, bytesRead);
				System.arraycopy(digestOfPart, 0, digest, timesRead * length,
						length);
				timesRead++;
				if (timesRead == maxReadTimesBeforeRedigest) {
					digestOfPart = getMD5(digest, 0, digest.length);
					System.arraycopy(digestOfPart, 0, digest, 0, length);
					timesRead = 1;
				}
				fileLength = fileLength - bytesRead;
			}

			if (timesRead > 1) {
				digestOfPart = getMD5(digest, 0, timesRead * length);
				System.arraycopy(digestOfPart, 0, digestToReturn, 0, length);
			} else {
				System.arraycopy(digest, 0, digestToReturn, 0, length);
			}

			if (is != null) {
				is.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return digestToReturn;
	}

	public static byte[] getMD5(final byte[] in) {
		alg.update(in);
		return alg.digest();
	}

	public static byte[] getMD5(final byte[] in, int from, int len) {
		alg.update(in, from, len);
		return alg.digest();
	}

	public static int getDigestLength() {
		return length;
	}

	public static byte[] getMd5(byte[] tmpValue) {

		alg.update(tmpValue);
		byte[] digest = alg.digest();
		return digest;
	}

	public static void setMaxBytesReadPerTime(int maxBytesReadPerTime) {
		IntegralityUtil.maxBytesReadPerTime = maxBytesReadPerTime;
	}

}
