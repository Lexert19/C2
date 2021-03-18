package netty.server;

import io.netty.channel.EventLoop;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Data {
    public static ConcurrentHashMap<String, Connection> connections = new ConcurrentHashMap<>();
    public static EventLoop eventLoop;
    public static Connection activeConnection;
    public static int lastId=0;
}
