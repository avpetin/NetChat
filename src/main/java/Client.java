import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Client client = null;
    private final String host = "127.0.0.1";
    private final Settings settings = new Settings();
    private String userName;
    private final String exit = "/exit";

    public Client(){
    }

    public String getUserName() {
        return userName;
    }

    public void createClient(){
        System.out.println("Выберите имя для отображения в чате");
        try (Socket clientSocket = new Socket(host, settings.port);
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        ) {
            Scanner scanner = new Scanner(System.in);
            userName = scanner.nextLine();
            out.println(userName);

            String users = in.readLine();
            System.out.println(users);

            String userResponse;
            do {
                System.out.println(in.readLine());
                userResponse = scanner.nextLine();
                out.write(userResponse);
            } while (!userResponse.equals("exit"));
        } catch (IOException e) {
             System.out.println(e.getMessage());
        }
    }
}
