package netty.server;

public class Start {
    public static void main(String[] args) {
        Thread listener = new Thread(new Listener());
        listener.start();

        NettyTcpServer nettyTcpServer = new NettyTcpServer();
        nettyTcpServer.start(7676);
    }
}
