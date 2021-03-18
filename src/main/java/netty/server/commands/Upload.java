package netty.server.commands;

import netty.server.Data;
import netty.server.ShellType;
import netty.server.SystemType;

import java.io.IOException;
import java.util.List;

public class Upload {
    public static void run(String command) throws IOException, InterruptedException {
        List<String> args = Command.getArgs(command);

        //Data.activeConnection.setBlockOutput(true);

        if (Data.activeConnection.getSystemType() == SystemType.type.Windows) {
            if(Data.activeConnection.getShellType() == ShellType.type.powershell){
                UploadPowershell.run(args.get(0), args.get(1), Data.activeConnection);
            }else if(Data.activeConnection.getShellType() == ShellType.type.cmd){
                UploadWindows.run(args.get(0), args.get(1), Data.activeConnection);
            }
        } else if (Data.activeConnection.getSystemType() == SystemType.type.Linux) {
            UploadLinux.run(args.get(0), args.get(1), Data.activeConnection);
        }
    }
}
