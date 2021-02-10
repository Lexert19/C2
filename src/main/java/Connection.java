import java.io.*;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class Connection implements Runnable {
    private int connectionId;
    private Socket socket;
    private BufferedReader bufferedReader;
    private PrintWriter printWriter;

    public Connection(Socket socket, int connectionId) {
        this.socket = socket;
        this.connectionId = connectionId;
    }

    @Override
    public void run() {
        try {
            InputStream input = socket.getInputStream();
            this.bufferedReader = new BufferedReader(new InputStreamReader(input));

            OutputStream output = socket.getOutputStream();
            this.printWriter = new PrintWriter(output, true);

            while (true) {
                receiveMessage();
                sendMessage();
                Thread.sleep(100);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showInfo(){
        System.out.println("id: "+connectionId);
        System.out.println(socket.getPort());
        System.out.println(socket.getInetAddress());
    }

    private void sendMessage() throws InterruptedException {
        String command = Data.connectionsCommands.get(connectionId).poll(1, TimeUnit.MILLISECONDS);
        if(command != null){
            interpretCommand(command);
        }
    }

    private void receiveMessage() throws IOException {
        if (bufferedReader.ready()) {
            String line = bufferedReader.readLine();
            System.out.println(line);
        }
    }

    private void interpretCommand(String command){
        if(command.contains("show")){
            showInfo();
        }else {
            this.printWriter.println(command);
        }
    }
}
