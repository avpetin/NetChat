import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private static Server server = null;
    private final Logger log = Logger.getInstance();

    private List<Thread> userThreads = new ArrayList<>();

    private Server(){
    }

    public static Server getServer(){
        if(server == null){
            server = new Server();
        }
        return server;
    }

    public Server connectToServer(int port){
        try (ServerSocket serverSocket = new ServerSocket(port)){
            while(true) {
                try (Socket clientSocket = serverSocket.accept()){
                    assert log != null;
                    log.log("New connection accepted");
                    Thread thread = new Thread(() -> {
                        try(PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
                        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))){
                            sendUserList(out);
                            out.println("Write your name");
                        }
                        catch (IOException e){
                            e.printStackTrace();
                        }
                    });
                    userThreads.add(thread);
                    thread.start();
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return this;
    }

    /*
     * Sends a list of online users to the newly connected user.
     */
    private void sendUserList(PrintWriter writer) {
        if (server.hasUsers()) {
            writer.println("Connected users: " + server.getUserNames());
        } else {
            writer.println("No other users connected");
        }
    }

    private boolean hasUsers(){
        return !userThreads.isEmpty();
    }
}
