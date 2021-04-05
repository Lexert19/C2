package netty.server.commands;

import netty.server.Connection;
import netty.server.Data;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class UploadLinux {
    public static void run(String fileName, String fileDestination, Connection connection) throws IOException, InterruptedException {
       /* Data.activeConnection.getCtx().channel().writeAndFlush("> " + fileDestination + "" + "\n");

        FileInputStream file = new FileInputStream(fileName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(file));

        StringBuilder sb = new StringBuilder();
        int ch;
        while ((ch = reader.read()) != -1) {
            if ((char) ch == '\n') {
                Data.activeConnection.send("echo \"" + sb.toString() + "\" >> " + fileDestination + "" + "\n");
                sb = new StringBuilder();
                continue;
            }
            sb.append((char) ch);
            if (sb.length() > 300) {
                Data.activeConnection.send("echo -n \"" + sb.toString() + "\" >> " + fileDestination + "" + "\n");
                sb = new StringBuilder();
            }
        }
        if (sb.length() > 0) {
            Data.activeConnection.send("echo -n \"" + sb.toString() + "\" >> " + fileDestination + "" + "\n");
        }
        reader.close();*/
    }

}
