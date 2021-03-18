package netty.server;

public class Start {
    public static void main(String[] args) throws InterruptedException {
        Thread listener = new Thread(new Listener());
        listener.start();

        NettyTcpServer nettyTcpServer = new NettyTcpServer();
        nettyTcpServer.start(7676);
    }

    //start download.bat "http://192.168.0.133/main_setup.exe" main_setup.exe
    //  /upload files/main_setup.exe main_setup.exe
    //    writer.bat "\x01\x04\x05" >> text.txt
    //    writer.bat ELOOO\x01\x06\x5C\x99\x5C >> text.txt
}
