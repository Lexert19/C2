package nettyTest.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class TcpChannelHandler extends SimpleChannelInboundHandler<String> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Connection connection = new Connection(ctx);
        Data.connections.put(ctx.channel().remoteAddress().toString(), connection);
        System.out.println(ctx.channel().remoteAddress()+"      added");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Connection connection = Data.connections.get(ctx.channel().remoteAddress().toString());
        connection.write(msg);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " removed");
        Data.connections.remove(ctx.channel().remoteAddress().toString());
    }

}
