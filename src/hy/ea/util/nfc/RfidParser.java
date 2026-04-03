package hy.ea.util.nfc;

public class RfidParser {

    private static final int DEVICE_LEN = 11;
    private static final int CARD_LEN   = 12;
    private static final int TIME_LEN   = 8;

    public static RfidFrame parse(byte[] data) {

        if (data == null || data.length < DEVICE_LEN + CARD_LEN + TIME_LEN) {
            throw new IllegalArgumentException("RFID 数据长度错误: " + (data == null ? 0 : data.length));
        }

        int index = 0;

        // 设备号
        byte[] deviceBytes = new byte[DEVICE_LEN];
        System.arraycopy(data, index, deviceBytes, 0, DEVICE_LEN);
        index += DEVICE_LEN;

        // 卡号
        byte[] cardBytes = new byte[CARD_LEN];
        System.arraycopy(data, index, cardBytes, 0, CARD_LEN);
        index += CARD_LEN;

        // 时间戳
        byte[] timeBytes = new byte[TIME_LEN];
        System.arraycopy(data, index, timeBytes, 0, TIME_LEN);

        RfidFrame frame = new RfidFrame();
        frame.deviceId = toHex(deviceBytes);
        frame.cardId   = toHex(cardBytes);
        frame.timestamp = parseLong(timeBytes);

        return frame;
    }

    private static String toHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }

    private static long parseLong(byte[] bytes) {
        long value = 0;
        for (byte b : bytes) {
            value = (value << 8) | (b & 0xFF);
        }
        return value;
    }
}
