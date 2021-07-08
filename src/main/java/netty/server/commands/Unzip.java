package netty.server.commands;

import netty.server.Data;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class Unzip {
    public static void run(String command) throws UnsupportedEncodingException, InterruptedException {
        List<String> args = Command.getArgs(command);

        Data.activeConnection.send("C:\\ProgramData\\connector\\unzip.py "+ args.get(0) +" "+ args.get(1)+"\n");
    }

}
