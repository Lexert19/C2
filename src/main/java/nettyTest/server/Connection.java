package nettyTest.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import lombok.Getter;
import lombok.Setter;

import javax.sound.sampled.AudioFormat;
import java.io.*;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

@Getter
@Setter
public class Connection {
    private ChannelHandlerContext ctx;
    private PrintWriter printWriter;
    private boolean printOutput = false;
    private SystemType.type systemType = SystemType.type.Windows;

    public Connection(ChannelHandlerContext ctx) throws IOException {
        this.ctx = ctx;
        String fileName = "logs/"+((ctx.channel().remoteAddress().toString()+".txt").substring(1));
        this.printWriter = new PrintWriter(new FileWriter(fileName));
    }

    public void send(String msg) throws UnsupportedEncodingException {
       /* byte[] originalBytes = msg.getBytes(); // Here the sequence of bytes representing the UTF-8 encoded string
        byte[] newBytes = new String(originalBytes, "UTF8").getBytes("Windows-1256");*/
        byte[] bytes = convertUtfToWindows(msg);

      /*  byte[] iso88591Data = msg.getBytes("ISO-8859-1");
        byte[] kurwa = msg.getBytes(StandardCharsets.ISO_8859_1);
        String kurwa1 = new String(kurwa, StandardCharsets.ISO_8859_1);
        System.out.println(kurwa1);*/
        ByteBuf byteBuf = Unpooled.copiedBuffer(bytes);
        this.ctx.channel().writeAndFlush(byteBuf);
    }

    public void write(String msg){
        if(printOutput){
            System.out.printf(msg);
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
