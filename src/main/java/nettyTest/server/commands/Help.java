package nettyTest.server.commands;

public class Help {
    public static void run(String command){
        System.out.println("/sendToAll - send massage to all connections");
        System.out.println("/connections - show info about current connections");
        System.out.println("/sendTo - ");
        System.out.println("/showOutput - show connection's output");
        System.out.println("/connect /IP_ADDRESS:PORT - connect to a session");
        System.out.println("/upload SOURCE_FILE DESTINATION_FILE - upload a file (slow)");
        System.out.println("/background - hide a current session");
        System.out.println("/test - test");
        System.out.println("/loadModules - load tools, which helps in interaction with windows");
        System.out.println("/help - show commands");

        System.out.println("start download.bat SOURCE_ADDRESS FILENAME - download file (fast)");
    }
}
