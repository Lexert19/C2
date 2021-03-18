package netty.server;

import io.netty.channel.EventLoop;

import java.util.*;

public class Data {
    public static LinkedHashMap<String, Connection> connections = new LinkedHashMap<>();
    public static EventLoop eventLoop;
    public static Connection activeConnection;
    public static int lastId=0;
    public static int numberOfConnection = 0;
}
