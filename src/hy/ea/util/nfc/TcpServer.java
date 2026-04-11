package hy.ea.util.nfc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tiantai.wfj.util.MqttService;
import hy.ea.office.action.CarMqttService;
import io.grpc.netty.shaded.io.netty.bootstrap.ServerBootstrap;
import io.grpc.netty.shaded.io.netty.channel.*;
import io.grpc.netty.shaded.io.netty.channel.nio.NioEventLoopGroup;
import io.grpc.netty.shaded.io.netty.channel.socket.SocketChannel;
import io.grpc.netty.shaded.io.netty.channel.socket.nio.NioServerSocketChannel;
import io.grpc.netty.shaded.io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.grpc.netty.shaded.io.netty.util.concurrent.DefaultEventExecutorGroup;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class TcpServer {
	private static final Logger logger = LoggerFactory.getLogger(TcpServer.class);

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private Channel serverChannel;
    private  CarMqttService instance;
    private boolean isDebug=true;
    @PostConstruct
    public void start() {
        if (isDebug) return;
        instance = CarMqttService.getInstance();
        bossGroup = new NioEventLoopGroup(1);
        workerGroup = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline()
                                .addLast(new DefaultEventExecutorGroup(16),new TcpServerHandler(instance));
                    }
                });

        bootstrap.bind(9000).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    serverChannel = future.channel();

                    logger.info("TCP Server 启动成功，端口 9000");

                } else {
                    System.err.println("TCP Server 启动失败");
                    future.cause().printStackTrace();
                }
            }
        });
    }

    @PreDestroy
    public void stop() {
        logger.info("TCP Server 正在关闭...");

        if (serverChannel != null) {
            serverChannel.close();
        }

        if (bossGroup != null) {
            bossGroup.shutdownGracefully();
        }

        if (workerGroup != null) {
            workerGroup.shutdownGracefully();
        }
    }
}


