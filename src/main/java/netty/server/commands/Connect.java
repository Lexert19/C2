package netty.server.commands;

import netty.server.Connection;
import netty.server.Data;

import java.util.List;

public class Connect {
    public static void run(String command){
        List<String> args = Command.getArgs(command);

        Connection connection;
        try {
            connection = Data.connections.get(args.get(0));
            if(!connection.isAlive()){
                throw new Exception();
            }
            connection.setPrintOutput(true);
            Data.activeConnection = connection;
            System.out.println("connected to " + args.get(0));
        } catch (Exception e) {
            try {
                connection = Data.connections.values().iterator().next();
                if(!connection.isAlive()){
                    throw new Exception();
                }
                connection.setPrintOutput(true);
                Data.activeConnection = connection;
                System.out.println("Connected to the first connection");
            } catch (Exception ee) {
                System.out.println("Lack of connections");
            }
        }
    }
}
