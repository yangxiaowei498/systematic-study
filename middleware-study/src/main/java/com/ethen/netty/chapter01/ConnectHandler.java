package com.ethen.netty.chapter01;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * code 1-2 => 当一个新的连接被建立时，channelHandler的channelActive()回调方法将被调用
 */
public class ConnectHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.err.println("客户端 " + ctx.channel().remoteAddress() + "已经建立了连接！");
    }
}
