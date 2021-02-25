package nettyTest.server.commands;

import nettyTest.server.Data;

public class Count {
    public static void run(){
        System.out.println(Data.numberOfConnection);
    }
}
