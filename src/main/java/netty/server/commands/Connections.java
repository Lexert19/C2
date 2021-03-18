package netty.server.commands;

import netty.server.Connection;
import netty.server.Data;

public class Connections {
    public static void run(String command) {
        for (Connection connection : Data.connections.values()) {
            connection.showInfo();
        }
    }
}
