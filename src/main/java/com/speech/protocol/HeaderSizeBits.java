package main.java.com.speech.protocol;

import lombok.Getter;

@Getter
public enum HeaderSizeBits {
    HeaderSize4((byte) 1),
    HeaderSize8((byte) 2),
    HeaderSize12((byte) 3),
    HeaderSize16((byte) 4),
    ;

    private final byte value;

    HeaderSizeBits(byte b) {
        this.value = b;
    }
    // 核心方法：返回枚举值
    public byte getValue() {
        return this.value;
    }


    public static HeaderSizeBits fromValue(int value) {
        for (HeaderSizeBits type : HeaderSizeBits.values()) {
            if (type.value == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown HeaderSizeBits value: " + value);
    }
}
