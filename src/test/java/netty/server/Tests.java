package netty.server;

import netty.server.commands.Command;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class Tests {

    @Test
    public void testGetArgs(String command){
        command = "/test \"one\" two three \"four\"";
        List<String> args = Command.getArgs(command);
        //Assertions.
    }
}
