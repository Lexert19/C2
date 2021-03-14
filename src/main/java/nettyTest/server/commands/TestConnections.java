package nettyTest.server.commands;

import nettyTest.server.Connection;
import nettyTest.server.Data;

import java.io.UnsupportedEncodingException;

public class TestConnections {
    public static void run() throws UnsupportedEncodingException, InterruptedException {
        for (Connection connection : Data.connections.values()) {
            connection.send(":\n");
        }
    }
}
