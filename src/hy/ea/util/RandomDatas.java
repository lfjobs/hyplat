package hy.ea.util;

import java.util.Random;
import java.util.UUID;

public class RandomDatas {

	private static Random random = new Random();

	private static final char[] passkey = { '2', '3', '4', '5', '6', '7', '8',
			'9', 'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'P', 'A', 'S', 'D',
			'F', 'G', 'H', 'J', 'K', 'Z', 'X', 'C', 'V', 'B', 'N', 'M' };

	private static final char[] numberpasskey = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9' };

	public static String getRandomNumber(int len) {
		char[] c = new char[len];
		c[0] = numberpasskey[random.nextInt(9) + 1];
		for (int i = 1; i < len; i++) {
			int ir = random.nextInt(10);
			c[i] = numberpasskey[ir];
		}
		return new String(c);
	}

	public static String getRandomString(int len) {
		char[] c = new char[len];

		for (int i = 0; i < len; i++) {
			int ir = random.nextInt(32);
			c[i] = passkey[ir];
		}
		return new String(c);
	}

	// 通过UUID生成随机数
	public static String getUUID() {
		UUID uuid = UUID.randomUUID();
		String str = uuid.toString();
		// 去掉"-"符号
		String temp = str.substring(0, 8) + str.substring(9, 13)
				+ str.substring(14, 18) + str.substring(19, 23)
				+ str.substring(24);
		return temp;
	}

}
