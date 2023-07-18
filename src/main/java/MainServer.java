
public class MainServer {
    public static void main(String[] args) {
        Settings settings = new Settings();

        Thread server = new Thread(() ->{
            Server.getServer()
            .connectToServer(settings.setPortFromFile("settings.txt"));
        });

        server.start();
    }
}
