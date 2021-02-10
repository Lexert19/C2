import nettyTest.server.Data;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static void main(String[] args) throws IOException {
        byte[] bytes = {00, 01, 02};
        String str = bytesToHex(bytes);
        System.out.println(str);
    /*    String msg = "\'\"ąśab";
        // "UTF8"
        // "Windows-1256"
        byte[] bytes;
        byte[] originalBytes = msg.getBytes(StandardCharsets.UTF_8); // Here the sequence of bytes representing the UTF-8 encoded string
        byte[] newBytes = new String(originalBytes, StandardCharsets.UTF_8).getBytes(StandardCharsets.ISO_8859_1);

        bytes = newBytes;
        for(int i=0; i<bytes.length; i++){
            System.out.println(bytes[i]);
        }*/

       /* FileInputStream file = new FileInputStream("files/text.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(file));

        StringBuilder sb = new StringBuilder();
        int ch;
        while((ch = reader.read()) != -1){
            sb.append((char) ch);
            if(sb.length() > 2000){

            }
            //line = reader.readLine();
        }
        if(sb.length() > 0){

        }
        System.out.println(sb.toString());*/

//        ServerSocket serverSocket = new ServerSocket(7676);
//        int lastId = 0;
//
//        Thread commandReader = new Thread(new InputListener());
//        commandReader.start();
//
//        while (true){
//            Socket socket = serverSocket.accept();
//            BlockingQueue<String> queue = new LinkedBlockingQueue<>(128);
//            Data.connectionsCommands.add(queue);
//            Thread connection = new Thread(new Connection(socket, lastId));
//            connection.start();
//            Data.connectionNumber++;
//            lastId++;
//        }
    }


}
