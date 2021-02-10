import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

public class InputListener implements Runnable{
    @Override
    public void run() {
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        while (true){
            System.out.printf("> ");
            String input = scanner.nextLine();
            interpretCommand(input);

        }
    }

    private void interpretCommand(String command){
        if(command.contains("/connection")){
            changeConnection(command);
        }else if(command.contains("/sendAll")){
            sendToAll(command);
        }else if(command.contains("/list")){
            listConnections();
        }else {
            sendToCurrentConnection(command);
        }
    }

    private void listConnections(){
        try {
            for(BlockingQueue queue : Data.connectionsCommands){
                queue.put("show");
            }
        }catch (Exception e){ }
    }

    private void sendToAll(String command){
        command = command.substring(8);
        try{
            while (command.charAt(0)==' '){
                command = command.substring(1);
            }
        }catch (Exception e){}

        try {
          for(BlockingQueue queue : Data.connectionsCommands){
              queue.put(command);
          }
        }catch (Exception e){ }
    }

    private void sendToCurrentConnection(String command){
        try {
            Data.connectionsCommands.get(Data.currentConnectionId).put(command);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void changeConnection(String command){
        command = command.substring(11);
        String connectionId = "";
        int position = 0;
        try{
            while (command.charAt(0)==' '){
                command = command.substring(1);
            }
            while (command.charAt(position)!=' '){
                connectionId+=command.charAt(position);
                position++;
            }
        }catch (Exception e){}

        try {
            int id = Integer.parseInt(connectionId);
            if(id < Data.connectionNumber && id >= 0){
                Data.currentConnectionId = Integer.parseInt(connectionId);
            }else {
                System.out.println("Incorrect connection id");
            }
        }catch (Exception e){ }

    }
}
