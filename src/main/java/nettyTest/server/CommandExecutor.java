package nettyTest.server;

import lombok.AllArgsConstructor;
import nettyTest.server.commands.*;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;

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
        if (command.length() > 1) {
            if (command.charAt(0) != '/') {
                SendToCurrentConnection.run(command);
                return;
            }
        }
        if (command.contains("/sendToAll")) {
            SendToAll.run(command);
        } else if (command.contains("/connections")) {
            Connections.run(command);
        } else if (command.contains("/sendTo")) {
            sendTo();
        } else if (command.contains("/showOutput")) {
            ShowOutput.run(command);
        } else if (command.contains("/connect")) {
            Connect.run(command);
        } else if (command.contains("/upload")) {
            Upload.run(command);
        } else if (command.contains("/background")) {
            Background.run(command);
        } else if (command.contains("/test")) {
            Test.run(command);
        } else if (command.contains("/loadTools")) {
            LoadTools.run(command);
        } else if (command.contains("/help")) {
            Help.run(command);
        } else if (command.contains("/uploadToAll")) {
            UploadToAll.run(command);
        } else if (command.contains("/clear")) {
            Clear.run(command);
        } else if (command.contains("/exit")) {
            Exit.run(command);
        }
    }


    private List<String> getArgs(String command) {
        String cuttedCommand = cutCommand(command);
        List<String> args = new LinkedList<>();
        StringBuilder sb = new StringBuilder();
        boolean spacesCleared = false;
        char ch;
        for (int i = 0; i < cuttedCommand.length(); i++) {
            ch = cuttedCommand.charAt(i);
            if (spacesCleared && ch == ' ') {
                args.add(sb.toString());
                sb = new StringBuilder();
                spacesCleared = false;
            } else if (ch == ' ') {
                continue;
            } else {
                spacesCleared = true;
                sb.append(ch);
            }
        }
        if (sb.length() > 0) {
            args.add(sb.toString());
        }
        return args;
    }

    private String cutCommand(String command) {
        for (int i = 0; i < command.length(); i++) {
            if (command.charAt(i) == ' ') {
                command = command.substring(i + 1);
                break;
            }
        }
        return command;
    }


    private void sendTo() throws UnsupportedEncodingException, InterruptedException {
        List<String> args = getArgs(command);

        Data.connections.get(args.get(0)).send("QWERTYQWERTY");
    }


    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

}
