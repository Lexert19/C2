package nettyTest.server.commands;

import nettyTest.server.Data;

import java.io.UnsupportedEncodingException;

public class SendToCurrentConnection {
    public static void run(String command) throws UnsupportedEncodingException, InterruptedException {
        if (Data.activeConnection != null) {
            Data.activeConnection.send(command + "\n");
        }
    }
}
