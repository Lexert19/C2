package nettyTest.server.commands;

import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;

class Command {
    public static void run(String command) {

    }

    public static List<String> getArgs(String command) {
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

    public static String cutCommand(String command) {
        for (int i = 0; i < command.length(); i++) {
            if (command.charAt(i) == ' ') {
                command = command.substring(i + 1);
                break;
            }
        }
        return command;
    }
}

