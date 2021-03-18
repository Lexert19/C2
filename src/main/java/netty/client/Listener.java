package netty.client;

import java.io.InputStreamReader;
import java.util.Scanner;

public class Listener implements Runnable{
    @Override
    public void run() {
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        while (true){
            System.out.printf("> ");
            String input = scanner.nextLine();
            try {
                Data.messagesToSend.put(input);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
