import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private static Server server = null;
    private static Logger log = null;
    private final List<Thread> userThreads = new ArrayList<>();
    private final List<ServerToClientProcessing> serverList = new ArrayList<>();
    private final List<String> userNames = new ArrayList<>();

    private Server(){
    }

    public static Server getServer(){
        if(server == null){
            server = new Server();
        }
        log = Logger.getInstance();
        return server;
    }

    public Server connectToServer(int port) {
        Socket[] clientSockets = new Socket[50];
        int i = 0;
        try (ServerSocket serverSocket = new ServerSocket(port)){
            while(true){
                try {
                    clientSockets[i] = serverSocket.accept();
                    serverList.add(new ServerToClientProcessing(clientSockets[i++], server, log));
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return this;
    }



    /**
     * Stores username of the newly connected client.
     */
    public void addUserName(String userName) {
        userNames.add(userName);
    }

    /**
     * When a client is disconneted, removes the associated username and UserThread
     */
    public void removeUser(String userName, Thread aUser) {
        boolean removed = userNames.remove(userName);
        if (removed) {
            userThreads.remove(aUser);
            System.out.println("The user " + userName + " quitted");
        }
    }

    public List<String> getUserNames() {
        return userNames;
    }

    public boolean hasUsers(){
        return !userNames.isEmpty();
    }

    public void broadcast(String message, Thread excludeUser, PrintWriter out) {
        for (Thread user : userThreads) {
            if (user != excludeUser) {
                out.write(message);
            }
        }
    }
}
