package nettyTest.server.commands;

import nettyTest.server.Data;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class SendTo {
    public static void run(String command) throws UnsupportedEncodingException, InterruptedException {
        List<String> args = Command.getArgs(command);

        Data.connections.get(args.get(0)).send("QWERTYQWERTY");
    }
}
