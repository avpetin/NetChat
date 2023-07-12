import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private Client client = null;
    private final String host = "netology.homework";
    private final Settings settings = new Settings();
    private String name;
    private final String exit = "/exit";

    public Client(){
    }

    public String getName() {
        return name;
    }

    public void createClient(){
        String response;
        System.out.println("Выберите имя для отображения в чате");
        try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))){
            name = br.readLine();
        } catch (IOException e) {
            System.out.println("Имя введено не корректно");
        }

        while(true) {
            try (Socket clientSocket = new Socket(host, settings.port);
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
            ) {
                while(in.ready()){
                    response = in.readLine();
                    if(!response.equals(exit)){
                        if(response.equals("Connected To Server")){

                        }
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

    private String chooseNameToSend(){
        return null;
    }
}
