import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private Client client = null;
    String host = "netology.homework";
    Settings settings = new Settings();
    String exit = "/exit";

    public Client(){
        client = new Client();
    }

    public void createClient(){
        String response;
        while(true) {
            try (Socket clientSocket = new Socket(host, settings.port);
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
            ) {
                while(in.ready()){
                    response = in.readLine();
                    if(!response.equals(exit)){

                    }
                    else {
                        return;
                    }
                }

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
