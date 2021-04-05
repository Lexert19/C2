package netty.server.commands;

import netty.server.Connection;
import netty.server.Data;

public class Bots {
    public static void run() {
        for (Connection bot : Data.bots.values()) {
            bot.showInfo();
        }
    }
}
