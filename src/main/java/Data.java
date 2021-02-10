import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;

public class Data {
    public static List<BlockingQueue<String>> connectionsCommands = new ArrayList<BlockingQueue<String>>(128);
    public static int currentConnectionId =0;
    public static int connectionNumber = 0;
}
