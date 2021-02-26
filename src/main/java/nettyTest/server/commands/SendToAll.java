package nettyTest.server.commands;

import nettyTest.server.Connection;
import nettyTest.server.Data;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class SendToAll {
    public static void run(String command) throws UnsupportedEncodingException, InterruptedException {
        List<String> args = Command.getArgs(command);
        for (Connection connection : Data.connections.values()) {
            connection.send(args.get(0));
        }
    }
}
