package netty.server.commands;

import netty.server.Data;

public class CountBots {
    public static void run(){
        System.out.println(Data.bots.size());
    }
}
