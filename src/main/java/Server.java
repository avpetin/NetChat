import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static Server server = null;
    private final Logger log = Logger.getInstance();

    private Server(){
    }

    public static Server getServer(){
        if(server == null){
            server = new Server();
        }
        return server;
    }

    public Server connectToServer(int port){
        try (ServerSocket serverSocket = new ServerSocket()){
            try (Socket clientSocket = serverSocket.accept();
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
                 BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))){
                assert log != null;
                log.log("New connection accepted");
                clientSocket.
                while(true){
                    out.println("Write your name");
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return this;
    }
}
