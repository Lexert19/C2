package netty.server.commands;

import netty.server.Data;

public class Count {
    public static void run(){
        System.out.println(Data.numberOfConnection);
    }
}
