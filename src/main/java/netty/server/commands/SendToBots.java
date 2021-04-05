package netty.server.commands;

import netty.server.Connection;
import netty.server.Data;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class SendToBots {
    public static void run(String command) throws UnsupportedEncodingException, InterruptedException {
        List<String> args = Command.getArgs(command);
        for (Connection bot : Data.bots.values()) {
            bot.send(args.get(0));
        }
    }
}
