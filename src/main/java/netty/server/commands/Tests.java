package netty.server.commands;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class Tests {
    public static void run(String command) throws UnsupportedEncodingException, InterruptedException {
        Tests tests = new Tests();
        tests.testGetArgs("/test \"one\" two three \"four\"");
    }

    private void testGetArgs(String command){
        List<String> args = Command.getArgs(command);
        if(!args.get(0).equals("one")){
            System.out.println("testGetArgs failed");
            return;
        }
        if(!args.get(1).equals("two")){
            System.out.println("testGetArgs failed");
            return;
        }
        if(!args.get(2).equals("three")){
            System.out.println("testGetArgs failed");
            return;
        }
        if(!args.get(3).equals("four")){
            System.out.println("testGetArgs failed");
            return;
        }
        System.out.println("testGetArgs passed");
    }
}
