package com.ethen.netty.chapter00;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

public class EchoServer {
    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    /**
     * 服务端启动方法
     *
     * @throws InterruptedException
     */
    public void start() throws InterruptedException {
        final EchoServerHandler serverHandler = new EchoServerHandler();
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(group, group)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(serverHandler);
                        }
                    });
            ChannelFuture future = bootstrap.bind().sync();
            System.err.println(EchoServer.class.getName() + "启动并且开始监听连接到端口" + future.channel().localAddress() + "的请求！");
            //获取channel的closeFuture，并且阻塞直到它完成
            future.channel().closeFuture().sync();
        } finally {
            //关闭EventLoopGroup释放所有的资源
            group.shutdownGracefully().sync();
        }
    }


    /**
     * 服务端启动引导
     *
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
        new EchoServer(9999).start();
    }
}
