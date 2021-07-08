package netty.server.commands;

public class Help {
    public static void run(String command){
        System.out.println("/sendToAll - send massage to all connections");
        System.out.println("/connections - show info about current connections");
        //System.out.println("/sendTo - ");
        System.out.println("/showOutput - show connection's output");
        System.out.println("/connect /IP_ADDRESS:PORT - connect to a session");
        //System.out.println("/upload SOURCE_FILE DESTINATION_FILE - upload a file (slow - cmd; fast - powershell)");
        System.out.println("/background - hide a current session");
        System.out.println("/test - test");
        //System.out.println("/loadModules - load tools, which helps in interaction with windows");
        System.out.println("/help - show commands");
        System.out.println("/executeScript FILE - execute python file ");
        System.out.println("/exit");
        System.out.println("/testConnections - find inactive connections");

        System.out.println("/download SOURCE DESTINATION - connector2 download file");
        System.out.println("/unzip ZIP DESTINATION - connector2 unzip file");

        System.out.println("/bots - list of bots");
        System.out.println("/cb - count bots");
        System.out.println("/testBots - ping bots");

    }
}
