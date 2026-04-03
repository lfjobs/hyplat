package com.batch.chinapay.util;

/**
 * @description 可见的ASC码和byte之间转换。
 * @author wang.tao
 * @date 2011-6-17 上午09:49:54
 * @since JDK 1.5.0
 */
public class AscHexSwitch {

	/**
	 * 将16进制byte按16进制代码转化为可见的ASC码。
	 * 
	 *@param len
	 *            只对输入的byte[]按前len位进行操作。
	 *@param in
	 *            输入的byte[]。
	 *@return 可见的ASC字符串。
	 */
	public static String Hex2Asc(int len, byte[] in) {

		int i;
		byte[] out;
		byte h, l;
		out = new byte[2 * len];

		for (i = 0; i < len; i++) {
			h = (byte) (in[i] >> 4);
			h = (byte) (h & 0x0F);
			l = (byte) (in[i] & 0x0F);
			out[i * 2] = (byte) ((h > 9) ? 'A' + h - 10 : '0' + h);
			out[i * 2 + 1] = (byte) ((l > 9) ? 'A' + l - 10 : '0' + l);
		}
		return (new String(out));
	}

	/**
	 * 将可见的ASC码按16进制代码转化为16进制byte。
	 * 
	 *@param len
	 *            只对输入的String按前len位进行操作，len必须为2的倍数。
	 *@param in
	 *            输入的可见的ASC码String。
	 *@return 16进制byte[]。
	 */
	public static byte[] Asc2Hex(int len, String in) {

		int i, j;
		byte b, h, l;
		char ch;
		in = in.toUpperCase();

		if (len % 2 != 0)
			return null;
		for (i = 0; i < len; i++) {
			ch = in.charAt(i);
			if ((ch < '0') || ((ch > '9') && (ch < 'A')) || (ch > 'Z'))
				return null;
		}
		byte[] out = new byte[len / 2];

		for (i = 0; i < len / 2; i++) {
			ch = in.charAt(i * 2);
			h = (byte) (((ch >= '0') && (ch <= '9')) ? ch - '0' : ch - 'A' + 10);
			ch = in.charAt(i * 2 + 1);
			l = (byte) (((ch >= '0') && (ch <= '9')) ? ch - '0' : ch - 'A' + 10);
			out[i] = (byte) (h * 16 + l);
		}
		return out;
	}
}
