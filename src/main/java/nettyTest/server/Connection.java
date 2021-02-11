package nettyTest.server;

import io.netty.channel.ChannelHandlerContext;
import lombok.Getter;
import lombok.Setter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

@Getter
@Setter
public class Connection {
    private ChannelHandlerContext ctx;
    private PrintWriter printWriter;
    private boolean printOutput = false;
    private boolean blockOutput = false;
    private int numberOfBlockedMsg = -1;
    private SystemType.type systemType = SystemType.type.Windows;

    public Connection(ChannelHandlerContext ctx) throws IOException {
        this.ctx = ctx;
        String fileName = "logs/"+((ctx.channel().remoteAddress().toString()+".txt").substring(1));
        this.printWriter = new PrintWriter(new FileWriter(fileName));
    }

    public void send(String msg) throws UnsupportedEncodingException, InterruptedException {
        this.ctx.channel().writeAndFlush(msg);
    }

    public void write(String msg){
        if(blockOutput){
            if(msg.length()==30) {
                System.out.println("Done!");
                blockOutput = false;
            }else {
                return;
            }
        }
        if(printOutput){
            try{
                System.out.printf(msg);
            }catch (Exception ignored){}
        }
        this.printWriter.write(msg);
        this.printWriter.flush();
    }

    public void showInfo(){
        System.out.println(ctx.channel().remoteAddress());
    }

    private byte[] convertUtfToWindows(String msg) throws UnsupportedEncodingException {
        byte[] originalBytes = msg.getBytes("Windows-1256"); // Here the sequence of bytes representing the UTF-8 encoded string
        byte[] newBytes = new String(originalBytes, "UTF8").getBytes("Windows-1256");

        /*for(int i=0; i<newBytes.length; i++){
            if(newBytes[i] == (byte)0x22){
                newBytes[i] = (byte)0x92;
            }
        }*/
        //return newBytes;
        return originalBytes;
    }
}
