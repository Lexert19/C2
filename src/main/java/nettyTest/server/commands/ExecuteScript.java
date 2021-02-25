package nettyTest.server.commands;

import java.io.IOException;
import java.util.List;

public class ExecuteScript {
    public static void run(String command) throws IOException, InterruptedException {
        List<String> args = Command.getArgs(command);

        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec("python3 scripts/"+args.get(0));
        process.waitFor();
    }
}
