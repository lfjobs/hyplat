package hy.ea.util.nfc;

import io.grpc.netty.shaded.io.netty.channel.Channel;

import java.util.concurrent.atomic.AtomicBoolean;

public class DeviceEntity {

    private volatile long before;


    private long time;
    private final long offset=10*1000;
    public boolean getLock(long now) {

        return now-before>offset;
    }

    public void setLock(long time) {
        this.before = time;
    }
    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

}
