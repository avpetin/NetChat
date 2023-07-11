
public class Main {
    public static void main(String[] args) {
        Settings settings = new Settings();

        Thread server = new Thread(() ->{
            Server
            .getServer()
            .connectToServer(settings.setPortFromFile("settings.txt"));
        });

        Thread client = new Thread(() -> {
            Client client1 = new Client();
            client1.createClient();
        });

        server.start();
        client.start();
    }
}
