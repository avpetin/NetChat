import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private static Server server = null;
    private final Logger log = Logger.getInstance();
    private List<Thread> userThreads = new ArrayList<>();
    private List<String> userNames = new ArrayList<>();

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
            try (Socket clientSocket = serverSocket.accept();
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
                 BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
            ){
                assert log != null;
                log.log("New connection accepted");

                sendUserList(out); // посылаем новому юзеру список участноков чата

                String userName = in.readLine();
                server.addUserName(userName);

                String serverMessage = "New user connected: " + userName;
                server.broadcast(serverMessage, Thread.currentThread());

                String clientMessage;

                do {
                    clientMessage = in.readLine();
                    serverMessage = "[" + userName + "]: " + clientMessage;
                    server.broadcast(serverMessage, Thread.currentThread());
                } while (!clientMessage.equals("/exit"));

                server.removeUser(userName, Thread.currentThread());
                clientSocket.close();

                serverMessage = userName + " has quitted.";
                server.broadcast(serverMessage, Thread.currentThread());
            }
            catch (IOException e){
                 e.printStackTrace();
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return this;
    }

    /**
     * Sends a list of online users to the newly connected user.
     */
    private void sendUserList(PrintWriter writer) {
        if (server.hasUsers()) {
            writer.println("Connected users: " + server.getUserNames());
        } else {
            writer.println("No other users connected");
        }
    }

    /**
     * Stores username of the newly connected client.
     */
    private void addUserName(String userName) {
        userNames.add(userName);
    }

    /**
     * When a client is disconneted, removes the associated username and UserThread
     */
    void removeUser(String userName, Thread aUser) {
        boolean removed = userNames.remove(userName);
        if (removed) {
            userThreads.remove(aUser);
            System.out.println("The user " + userName + " quitted");
        }
    }

    private List<String> getUserNames() {
        return userNames;
    }

    private boolean hasUsers(){
        return !userThreads.isEmpty();
    }

    void broadcast(String message, Thread excludeUser) {
        for (Thread user : userThreads) {
            if (user != excludeUser) {
                assert log != null;
                log.log(message);
            }
        }
    }
}
