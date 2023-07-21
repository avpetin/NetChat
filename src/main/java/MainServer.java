import java.io.IOException;

public class MainServer {
    public static void main(String[] args) {
        Settings settings = new Settings();

        Server.getServer()
        .connectToServer(settings.setPortFromFile("settings.txt"));
    }
}
