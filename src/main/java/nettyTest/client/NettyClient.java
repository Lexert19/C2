package nettyTest.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class NettyClient {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public static void main(String[] args) throws IOException, InterruptedException {
        Thread listener = new Thread(new Listener());
        listener.start();

        NettyClient nettyClient = new NettyClient();
        nettyClient.startConnection("127.0.0.1",7676);
        while (true){
            nettyClient.receiveMessage();
            nettyClient.sendMessage();
            //Thread.sleep();
        }
    }

    private void startConnection(String ip, int port) throws IOException {
        socket = new Socket(ip, port);
        //socket.setSoTimeout(5000);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    private void sendMessage() throws InterruptedException {
        try{
            if(!Data.messagesToSend.isEmpty()){
                out.println(Data.messagesToSend.take());
            }
        }catch (Exception e){}
    }

    private void receiveMessage() throws IOException {
        try{
            if(in.ready()){
                StringBuilder line = new StringBuilder();
                while (in.ready()) {
                    line.append((char) in.read());
                }
                if(line.length() > 0){
                    System.out.println(line);
                }
            }

            /*if(in.ready()){
                System.out.println(in.read());
                //in.lines().forEach(System.out::println);
            }*/
        }catch (Exception e){}
    }

    private void stopConnection() throws IOException {
        in.close();
        out.close();
        socket.close();
    }
}
