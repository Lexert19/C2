package nettyTest.server.commands;

import nettyTest.server.Connection;
import nettyTest.server.Data;

public class Connections {
    public static void run(String command) {
        for (Connection connection : Data.connections.values()) {
            connection.showInfo();
        }
    }
}
