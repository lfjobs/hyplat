package hy.plat.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.IOException;
import java.rmi.dgc.VMID;

public class GUID {
	private static final Logger logger = LoggerFactory.getLogger(GUID.class);

	private static final int sDataLength = 32;
	private byte[] mBytes;
	private Integer mHashCode;
	private String mStr;
	

	public static synchronized GUID getInstance() {		
		return new GUID();	 
	}

	/**
	 * 构造子
	 * 
	 */
	public GUID() {
		mBytes = new byte[sDataLength];
		mHashCode = new Integer(0);
		mStr = null;

		VMID vmid = new VMID();
		vmid2GUID(vmid.toString());
		computeHashCode();
	}

	/**
	 * 
	 * @param abyte0
	 */
	public GUID(byte[] abyte0) {
		mBytes = new byte[sDataLength];
		mHashCode = new Integer(0);
		mStr = null;
		mBytes = abyte0;
		mStr = null;
		computeHashCode();
	}

	/**
	 * 
	 * @param abyte0
	 */
	public void setGuid(byte[] abyte0) {
		System.arraycopy(abyte0, 0, mBytes, 0, sDataLength);
		mStr = null;
		computeHashCode();
	}

	/**
	 * 
	 * @return byte[]
	 */
	public byte[] getGuid() {
		return mBytes;
	}

	/**
	 * 
	 * @return Integer
	 */
	public Integer getHashCode() {
		return mHashCode;
	}

	/**
	 * 
	 * @param guid
	 * @return boolean
	 */
	public boolean equals(GUID guid) {
		for (int i = 0; i < sDataLength; i++)
			if (mBytes[i] != guid.mBytes[i]) {
				return false;
			}

		return true;
	}

	/**
     * 
     *
     */
	private void computeHashCode() {
		int k = (mBytes[0] >= 0) ? ((int) (mBytes[0])) : (mBytes[0] + 256);
		int i1 = (mBytes[1] >= 0) ? ((int) (mBytes[1])) : (mBytes[1] + 256);
		int k1 = (mBytes[2] >= 0) ? ((int) (mBytes[2])) : (mBytes[2] + 256);
		int i2 = (mBytes[3] >= 0) ? ((int) (mBytes[3])) : (mBytes[3] + 256);
		int i = (k << 24) | (i1 << 16) | (k1 << 8) | i2;

		for (int k2 = 4; k2 < sDataLength; k2 += 4) {
			int l = (mBytes[k2 + 0] >= 0) ? ((int) (mBytes[k2 + 0]))
					: (mBytes[k2 + 0] + 256);
			int j1 = (mBytes[k2 + 1] >= 0) ? ((int) (mBytes[k2 + 1]))
					: (mBytes[k2 + 1] + 256);
			int l1 = (mBytes[k2 + 2] >= 0) ? ((int) (mBytes[k2 + 2]))
					: (mBytes[k2 + 2] + 256);
			int j2 = (mBytes[k2 + 3] >= 0) ? ((int) (mBytes[k2 + 3]))
					: (mBytes[k2 + 3] + 256);
			int j = (l << 24) | (j1 << 16) | (l1 << 8) | j2;
			i ^= j;
		}

		mHashCode = new Integer(i);
	}

	/**
	 * 
	 * @return int
	 */
	public int getSize() {
		return sDataLength;
	}

	/**
	 * 
	 * @param abyte0
	 */
	protected void copy(byte[] abyte0) {
		System.arraycopy(abyte0, 0, mBytes, 0, sDataLength);
		mStr = null;
		computeHashCode();
	}

	/**
	 * 
	 * @param abyte0
	 * @param i
	 * @return int
	 * @throws Exception
	 */
	protected int serialize(byte[] abyte0, int i) throws Exception {
		System.arraycopy(mBytes, 0, abyte0, i, sDataLength);

		return i + sDataLength;
	}

	/**
	 * 
	 * @param abyte0
	 * @param i
	 * @return int
	 */
	protected int deserialize(byte[] abyte0, int i) {
		System.arraycopy(abyte0, i, mBytes, 0, sDataLength);
		mStr = null;
		computeHashCode();

		return i + sDataLength;
	}

	/**
	 * 
	 * @param s
	 */
	private void vmid2GUID(String s) {
		int i = 0;
		int j = 0;

		for (; i < s.length(); i++) {
			if (i < sDataLength) {
				mBytes[j] = (byte) s.charAt(i);
			} else {
				mBytes[j] ^= (byte) s.charAt(i);
			}

			if (++j >= sDataLength) {
				j = 0;
			}
		}

		mStr = null;
	}

	/**
	 * @return String
	 */
	public String toString() {
		if (mStr == null) {
			mStr = generateString();
		}

		return mStr;
	}

	/**
	 * 
	 * @return String
	 */
	private String generateString() {
		/*
		 * String s = ""; s = s + HexDec.convertBytesToHexString(mBytes, 0, 4) +
		 * "-"; s = s + HexDec.convertBytesToHexString(mBytes, 4, 2) + "-"; s =
		 * s + HexDec.convertBytesToHexString(mBytes, 6, 2) + "-"; s = s +
		 * HexDec.convertBytesToHexString(mBytes, 8, 2) + "-"; s = s +
		 * HexDec.convertBytesToHexString(mBytes, 10, 6);
		 */
		StringBuffer s = new StringBuffer();
		s.append(HexDec.convertBytesToHexString(mBytes, 0, 4)).append("-");
		s.append(HexDec.convertBytesToHexString(mBytes, 4, 2)).append("-");
		s.append(HexDec.convertBytesToHexString(mBytes, 6, 2)).append("-");
		s.append(HexDec.convertBytesToHexString(mBytes, 8, 2)).append("-");
		s.append(HexDec.convertBytesToHexString(mBytes, 10, 6));
		return s.toString();

	}

	/**
	 * 
	 * @return String
	 */
	public String toHexString() {
		return HexDec.convertBytesToHexString(mBytes);
	}

	/**
	 * 
	 * @param s
	 */
	protected void fromHexString(String s) {
		mBytes = HexDec.convertHexStringToBytes(s);
		mStr = null;
		computeHashCode();
	}

	/**
	 * 
	 * @param datainputstream
	 * @param flag
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	protected void readFromOIS(DataInputStream datainputstream, boolean flag)
			throws IOException, ClassNotFoundException {
		if (flag) {
			datainputstream.skipBytes(67);
		} else {
			if (datainputstream.readByte() == 113) {
				datainputstream.skipBytes(3);

				return;
			}

			datainputstream.skipBytes(3);
		}

		int i = datainputstream.readInt();

		switch (i) {
		case 1: // '\001'
			deserialize1(datainputstream, flag);

			break;

		default:
			throw new IOException(
					"Fail to deserialize guid of datafile. Unknown verison.");
		}

		mStr = null;
		computeHashCode();
	}

	/**
	 * 
	 * @param datainputstream
	 * @param flag
	 * @throws IOException
	 */
	private void deserialize1(DataInputStream datainputstream, boolean flag)
			throws IOException {
		if (flag) {
			datainputstream.skipBytes(23);
		} else {
			datainputstream.skipBytes(10);
		}

		for (int i = 0; i < sDataLength; i++)
			mBytes[i] = datainputstream.readByte();
	}

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			for (int i = 0; i < 3; i++) {
				long l = System.currentTimeMillis();
				GUID guid = new GUID();
				logger.info("调试信息");
				logger.info("调试信息");
				logger.info("调试信息");
			}

			/*
			 * GUID guid1 = new GUID(); guid1.fromHexString(guid.toHexString());
			 * logger.info("from hex String =: {}", guid1.toHexString());
			 * byte abyte0[] = (new String("1111111111111101")).getBytes(); GUID
			 * guid2 = new GUID(abyte0); System.out.println("" +
			 * guid2.toHexString());
			 */
		} catch (Exception exception) {
			logger.error("操作异常", e);
		}
	}

}
