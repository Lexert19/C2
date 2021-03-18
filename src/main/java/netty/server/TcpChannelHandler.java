package netty.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class TcpChannelHandler extends SimpleChannelInboundHandler<String> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Connection connection = new Connection(ctx, Data.lastId, false);
        Data.lastId++;
        Data.connections.put(ctx.channel().remoteAddress().toString(), connection);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        /*if(msg.charAt(0)==':' && msg.length() == 1){
            if(msg.length() == 1){
                return;
            }else {
                msg = msg.substring(1);
            }
        }*/
        if(ctx.channel().remoteAddress().toString().contains("127.0.0.1")){
            String[] commands = msg.split(System.getProperty("line.separator"));
            for (String command : commands){
                Data.eventLoop.execute(new CommandExecutor(command));
            }
            return;
        }
        Connection connection = Data.connections.get(ctx.channel().remoteAddress().toString());
        connection.write(msg);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Data.connections.remove(ctx.channel().remoteAddress().toString());
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //super.exceptionCaught(ctx, cause);
        System.out.println(cause.getLocalizedMessage());
        ctx.close();
    }
}
