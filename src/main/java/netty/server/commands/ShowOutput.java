package netty.server.commands;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class ShowOutput {
    public static void run(String command) throws IOException, InterruptedException {
        List<String> args = Command.getArgs(command);

        BufferedReader reader = new BufferedReader(new FileReader("logs/" + args.get(0) + ".txt"));
        while (reader.ready()) {
            System.out.println(reader.readLine());
        }
        reader.close();
    }
}
