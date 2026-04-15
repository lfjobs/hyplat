package main.java.com.speech.protocol;

import lombok.Getter;

@Getter
public enum SerializationBits {
    Raw((byte) 0),
    JSON((byte) 0b1),
    Thrift((byte) 0b11),
    Custom((byte) 0b1111),
    ;

    private final byte value;

    SerializationBits(byte b) {
        this.value = b;
    }
    // 核心方法：返回枚举值
    public byte getValue() {
        return this.value;
    }

    public static SerializationBits fromValue(int value) {
        for (SerializationBits type : SerializationBits.values()) {
            if (type.value == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown SerializationBits value: " + value);
    }
}
