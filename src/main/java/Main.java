
public class Main {
    public static void main(String[] args) {
        Settings settings = new Settings();
        Server server = Server
                .getServer()
                .connectToServer(settings.setPortFromFile("settings.txt"));
    }
}
