import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerToClientProcessing extends Thread {
    private Server server;
    private Logger log = Logger.getInstance();
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public ServerToClientProcessing(Socket socket, Server server, Logger log) throws IOException {
        this.socket = socket;
        this.server = server;
        this.log = log;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        start();
    }

    @Override
    public void run(){
        String userName = null;
        try {
            userName = in.readLine();

            server.addUserName(userName);

            sendUserList(out); // посылаем новому юзеру список участноков чата

            assert log != null;
            String serverMessage = "New user connected: " + userName;
            log.log(serverMessage);
            server.broadcast(serverMessage, Thread.currentThread(), out);

            String clientMessage;
            System.out.println("Сообщения от пользователей");
            do {
                out.write(" ");
                clientMessage = in.readLine();
                serverMessage = "[" + userName + "]: " + clientMessage;
                log.log(serverMessage);
            } while (!clientMessage.equals("/exit"));

            server.removeUser(userName, Thread.currentThread());
            socket.close();

            serverMessage = userName + " has quitted.";
            server.broadcast(serverMessage, Thread.currentThread(), out);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
}
