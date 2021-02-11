package nettyTest.server;

import java.io.*;
import java.util.Scanner;

public class Listener implements Runnable {
    @Override
    public void run() {
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        while (true) {
            try {
                String command = scanner.nextLine();
                executeCommand(command);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private void executeCommand(String command) {
        Data.eventLoop.execute(new CommandExecutor(command));
    }
}
