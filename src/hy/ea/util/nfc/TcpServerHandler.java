package hy.ea.util.nfc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import hy.ea.office.action.CarMqttService;
import io.grpc.netty.shaded.io.netty.buffer.ByteBuf;
import io.grpc.netty.shaded.io.netty.buffer.Unpooled;
import io.grpc.netty.shaded.io.netty.channel.Channel;
import io.grpc.netty.shaded.io.netty.channel.ChannelHandlerContext;
import io.grpc.netty.shaded.io.netty.channel.SimpleChannelInboundHandler;
//import lombok.extern.log4j.Log4j;
import org.hibernate.sql.Update;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import java.util.concurrent.TimeUnit;


public class TcpServerHandler extends SimpleChannelInboundHandler<ByteBuf> {
    // 在线设备
    private static final Map<String, DeviceEntity> DEVICE_MAP = new ConcurrentHashMap<>();
    private final String COMBO_READ = "43 4D 02 03 03 00 00 00 00 00".trim();
    private final String HEART = "43 4D 06 03 0D 00 00 00".trim();
    private  CarMqttService carMqttService;
    public TcpServerHandler(CarMqttService carMqttService) {
        this.carMqttService=carMqttService;

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ctx.executor().schedule(new Runnable() {
            @Override
            public void run() {
                byte[] cmd = new byte[]{
                        (byte) 0x43, (byte) 0x4D,
                        (byte) 0x02, (byte) 0x02,
                        (byte) 0x02,
                        (byte) 0x00, (byte) 0x00,
                        (byte) 0x00, (byte) 0x00
                };
                ctx.writeAndFlush(Unpooled.wrappedBuffer(cmd));
            }
        }, 500, TimeUnit.MILLISECONDS);
        logger.info("调试信息");

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;

        byte[] data = new byte[buf.readableBytes()];
        buf.readBytes(data);
        String key = ctx.channel().remoteAddress().toString();
        String frame = toHex(data).trim();
        if (frame.equals(COMBO_READ)) {
            addDevice(key);
        } else if (frame.startsWith(HEART)) {
            logger.info("调试信息");
            updateTime(key);
        } else {
            if (DEVICE_MAP.containsKey(key)) {
                DeviceEntity deviceEntity = DEVICE_MAP.get(key);
                long now = System.currentTimeMillis();
                if (deviceEntity.getLock(now)) {
                    deviceEntity.setLock(now);

                    RfidFrame rfidFrame = RfidParser.parse(data);

                    if (rfidFrame.cardId.trim().equals("E28069950000500E8B06F89F".trim())||rfidFrame.cardId.trim().equals("E28069950000500E8B06F8A5".trim())){
                        logger.info("调试信息");
                        CarMqttService.isOpen("111111","3c74a221-c156e59a");//开门

                    }


                }
            }

        }
    }

    private void updateTime(String key) {
        if (DEVICE_MAP.containsKey(key)) {
            DeviceEntity deviceEntity = DEVICE_MAP.get(key);
            deviceEntity.setTime(System.currentTimeMillis());
        }

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) {
        // 打印接收到的消息长度
        byte[] data = new byte[msg.readableBytes()];
        msg.readBytes(data);


    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        DEVICE_MAP.values().remove(ctx.channel());
        logger.info("调试信息");
    }

    private String toHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(String.format("%02X ", bytes[i]));
        }
        return sb.toString();
    }

    private void addDevice(String key) {
        if (!DEVICE_MAP.containsKey(key)) {
            DeviceEntity deviceEntity = new DeviceEntity();
            deviceEntity.setLock(System.currentTimeMillis());
            DEVICE_MAP.put(key, deviceEntity);
        }

    }

    @Scheduled(fixedRate = 10 * 60 * 1000) // 10分钟 = 600_000毫秒
    public void cleanup() {
        long now = System.currentTimeMillis();
        long timeout = 30 * 60 * 1000; // 30分钟

        Iterator<Map.Entry<String, DeviceEntity>> iterator = DEVICE_MAP.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, DeviceEntity> entry = iterator.next();
            DeviceEntity device = entry.getValue();
            if (now - device.getTime() > timeout) {
                iterator.remove(); // 安全删除
                logger.info("清理设备：: {}", entry.getKey());
            }
        }
    }

}
