package nettyTest.server.commands;

import java.util.List;

public class Test {
    public static void run(String command) {
        command = "/siema \"siema elo\" java chleb \"siema\"";
        List<String> args = Command.getArgs(command);
        System.out.println(args.get(0));
        System.out.println(args.get(1));
        System.out.println(args.get(2));
        System.out.println(args.get(3));
    }
}
