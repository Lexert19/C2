package netty.server.commands;

import java.io.IOException;

public class Clear {
    public static void run(String command) throws IOException {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        Runtime.getRuntime().exec("clear");
        System.out.println("\f");
    }
}
