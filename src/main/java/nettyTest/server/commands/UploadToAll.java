package nettyTest.server.commands;

import nettyTest.server.Connection;
import nettyTest.server.Data;

import java.io.IOException;
import java.util.List;

public class UploadToAll {
    public static void run(String command) throws IOException, InterruptedException {
        List<String> args = Command.getArgs(command);

        for (Connection connection : Data.connections.values()) {
            if(connection.getId() == Data.lastId-1){
                connection.setBlockOutput(true);
            }
            UploadWindows.run(args.get(0), args.get(1), connection);
        }
    }
}
