package nettyTest.server.commands;

import nettyTest.server.Connection;
import nettyTest.server.Data;

import java.io.UnsupportedEncodingException;

public class SendToAll {
    public static void run(String command) throws UnsupportedEncodingException, InterruptedException {
        for (Connection connection : Data.connections.values()) {
            connection.send(command);
        }
    }
}
