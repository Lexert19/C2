package netty.server.commands;

import netty.server.Connection;
import netty.server.Data;

import java.io.UnsupportedEncodingException;

public class TestConnections {
    public static void run() throws UnsupportedEncodingException, InterruptedException {
        for (Connection connection : Data.connections.values()) {
            connection.send(":\n");
        }
    }
}
