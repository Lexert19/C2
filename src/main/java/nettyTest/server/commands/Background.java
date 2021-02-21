package nettyTest.server.commands;

import nettyTest.server.Data;

public class Background {
    public static void run(String command) {
        Data.activeConnection.setPrintOutput(false);
        Data.activeConnection = null;
    }
}
