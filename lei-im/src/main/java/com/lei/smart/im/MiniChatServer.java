package com.lei.smart.im;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.AdaptiveRecvByteBufAllocator;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * @author dingjianlei
 */
public class MiniChatServer extends BaseServer {
    private ScheduledExecutorService executorService  = Executors.newScheduledThreadPool(2);
    final ThreadFactory threadFactory = new ThreadFactoryBuilder()
            .setNameFormat("netty-context-business-%d")
            .setDaemon(false)
            .build();
    NioEventLoopGroup business = new NioEventLoopGroup(200,threadFactory);



    @Override
//    @PostConstruct
    public void start(int port) {
        b.group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .localAddress(new InetSocketAddress(port))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(defLoopGroup,
                                new HttpServerCodec(),   //请求解码器
                                new HttpObjectAggregator(65536),//将多个消息转换成单一的消息对象
                                new ChunkedWriteHandler(),  //支持异步发送大的码流，一般用于发送文件流
                                new IdleStateHandler(60, 0, 0), //检测链路是否读空闲
                                new UserAuthHandler(), //处理握手和认证
                                new MessageHandler()    //处理消息的发送
                        );
                       ;
                    }
                });
//        使用对象池，重用缓冲区
        b.option(ChannelOption.RCVBUF_ALLOCATOR, AdaptiveRecvByteBufAllocator.DEFAULT);
        b.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
        b.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
//        b.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
        try {
            cf = b.bind().sync();
            InetSocketAddress addr = (InetSocketAddress) cf.channel().localAddress();
            logger.info("WebSocketServer start success, port is:{}", addr.getPort());

            // 定时扫描所有的Channel，关闭失效的Channel
            executorService.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    logger.info("scanNotActiveChannel --------");
                    UserInfoManager.scanNotActiveChannel();
                }
            }, 3, 60, TimeUnit.SECONDS);

            // 定时向所有客户端发送Ping消息
            executorService.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
//                    UserInfoManager.baseCastMessage();
                }
            }, 3, 50, TimeUnit.SECONDS);

        } catch (InterruptedException e) {
            logger.error("WebSocketServer start fail,", e);
        }
    }

    @Override
    public void shutdown() {
        if (executorService != null) {
            executorService.shutdown();
        }
        super.shutdown();
    }

    public ScheduledExecutorService getExecutorService() {
        return executorService;
    }

    public void setExecutorService(ScheduledExecutorService executorService) {
        this.executorService = executorService;
    }
}
