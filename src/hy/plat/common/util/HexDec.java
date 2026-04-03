package hy.plat.common.util;

public class HexDec {
	private static char[] hexChar;

	static {
		hexChar = new char[16];
		hexChar[0] = '0';
		hexChar[1] = '1';
		hexChar[2] = '2';
		hexChar[3] = '3';
		hexChar[4] = '4';
		hexChar[5] = '5';
		hexChar[6] = '6';
		hexChar[7] = '7';
		hexChar[8] = '8';
		hexChar[9] = '9';
		hexChar[10] = 'A';
		hexChar[11] = 'B';
		hexChar[12] = 'C';
		hexChar[13] = 'D';
		hexChar[14] = 'E';
		hexChar[15] = 'F';
	}

	/**
	 * 构造子
	 * 
	 */
	public HexDec() {
	}

	/**
	 * 将字节数组转换成十六进制字符串
	 * 
	 * @param abyte0
	 * @return String
	 */
	public static String convertBytesToHexString(byte[] abyte0) {
		return convertBytesToHexString(abyte0, 0, abyte0.length);
	}

	/**
	 * 
	 * @param abyte0
	 * @param i
	 * @param j
	 * @return String
	 */
	public static String convertBytesToHexString(byte[] abyte0, int i, int j) {
		StringBuffer stringbuffer = new StringBuffer(j * 2);
		convertBytesToHexString(abyte0, i, j, stringbuffer);

		return stringbuffer.toString();
	}

	/**
	 * 
	 * @param abyte0
	 * @param i
	 * @param j
	 * @param stringbuffer
	 */
	public static void convertBytesToHexString(byte[] abyte0, int i, int j,
			StringBuffer stringbuffer) {
		int k = i + j;

		for (int l = i; l < k; l++) {
			stringbuffer.append(hexChar[(abyte0[l] >> 4) & 0xf]);
			stringbuffer.append(hexChar[abyte0[l] & 0xf]);
		}
	}

	/**
	 * 将十六进制字符串转换成字节数组
	 * 
	 * @param s
	 * @return byte[]
	 */
	public static byte[] convertHexStringToBytes(String s) {
		int i = s.length();
		byte[] abyte0 = new byte[i / 2];

		try {
			convertHexStringToBytes(s, abyte0, 0);
		} catch (Exception e) {
		}

		return abyte0;
	}

	/**
	 * 
	 * @param s
	 * @param abyte0
	 * @param i
	 * @return int
	 * @throws Exception
	 */
	public static int convertHexStringToBytes(String s, byte[] abyte0, int i)
			throws Exception {
		int j = s.length();

		for (int k = 0; k < j; k += 2) {
			char c = s.charAt(k);
			char c1 = s.charAt(k + 1);
			byte byte0 = 0;
			byte byte1 = 0;

			if ((c >= '0') && (c <= '9')) {
				byte0 = (byte) (c - 48);
			} else if ((c >= 'A') && (c <= 'F')) {
				byte0 = (byte) ((10 + c) - 65);
			} else if ((c >= 'a') && (c <= 'f')) {
				byte0 = (byte) ((10 + c) - 97);
			} else {
				throw new Exception("Invalid HEX character [char=" + c
						+ "], [string=" + s + "]");
			}

			if ((c1 >= '0') && (c1 <= '9')) {
				byte1 = (byte) (c1 - 48);
			} else if ((c1 >= 'A') && (c1 <= 'F')) {
				byte1 = (byte) ((10 + c1) - 65);
			} else if ((c1 >= 'a') && (c1 <= 'f')) {
				byte1 = (byte) ((10 + c1) - 97);
			} else {
				throw new Exception("Invalid HEX character [char=" + c1
						+ "], [string=" + s + "]");
			}

			abyte0[i++] = (byte) ((byte0 << 4) | byte1);
		}

		return i;
	}
}