public class MainClient {
    public static void main(String[] args) {
        Thread client = new Thread(() -> {
            Client client1 = new Client();
            client1.createClient();
        });

        client.start();
    }
}
