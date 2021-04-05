package netty.server.commands;

import netty.server.Connection;
import netty.server.Data;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;

public class TestBots {
    public static void run() throws UnsupportedEncodingException, InterruptedException {
        for (Connection bot : Data.bots.values()) {
            bot.setAlive(false);
            bot.getCtx().writeAndFlush("\n");
            bot.getCtx().executor().schedule(new Runnable() {
                @Override
                public void run() {
                    if(!bot.isAlive()){
                        bot.getCtx().close();
                    }
                }
            },7, TimeUnit.SECONDS);
        }

    }
}
