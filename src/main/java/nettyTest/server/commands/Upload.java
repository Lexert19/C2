package nettyTest.server.commands;

import nettyTest.server.Data;
import nettyTest.server.SystemType;

import java.io.IOException;
import java.util.List;

public class Upload {
    public static void run(String command) throws IOException, InterruptedException {
        List<String> args = Command.getArgs(command);

        //Data.activeConnection.setBlockOutput(true);

        if (Data.activeConnection.getSystemType() == SystemType.type.Windows) {
            UploadWindows.run(args.get(0), args.get(1), Data.activeConnection);
        } else if (Data.activeConnection.getSystemType() == SystemType.type.Linux) {
            UploadLinux.run(args.get(0), args.get(1), Data.activeConnection);
        }
    }
}
