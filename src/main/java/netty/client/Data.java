package netty.client;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Data {
    public static BlockingQueue<String> messagesToSend = new LinkedBlockingQueue<>();
}
