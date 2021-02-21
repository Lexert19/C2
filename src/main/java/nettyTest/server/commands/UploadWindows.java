package nettyTest.server.commands;

import nettyTest.server.Connection;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.file.Files;

public class UploadWindows{
    public static void run(String fileName, String fileDestination, Connection connection) throws IOException, InterruptedException {
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
        connection.send("echo !Done\u00ff\n");
        reader.close();
    }
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    private static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

}
