package hy.ea.util.nfc;

public class RfidFrame {
    public String deviceId;   // 设备号
    public String cardId;     // EPC 卡号
    public long timestamp;   // 时间戳

    @Override
    public String toString() {
        return "RfidFrame{" +
                "deviceId='" + deviceId + '\'' +
                ", cardId='" + cardId + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
