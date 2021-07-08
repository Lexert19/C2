package netty.server.commands;

import netty.server.Connection;
import netty.server.Data;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;

public class TestConnections {
    public static void run() throws UnsupportedEncodingException, InterruptedException {
        for (Connection connection : Data.connections.values()) {
            connection.setAlive(false);
            connection.getCtx().writeAndFlush("\r\n");
            connection.getCtx().executor().schedule(new Runnable() {
                @Override
                public void run() {
                    if(!connection.isAlive()){
                        connection.getCtx().close();
                    }
                }
            },7,TimeUnit.SECONDS);
        }

    }
}
