import java.io.IOException;
import java.net.ServerSocket;

public class Server {

    private int port;
    private ServerSocket serverSocket;

    public static void main(String[] args) {
        Server server = new Server(500);
        server.connect();
        server.close();
    }

    public Server(int port) {
        this.port = port;
    }

    public void connect() {
        if (this.serverSocket.isClosed()) {
            try {
                this.serverSocket = new ServerSocket(this.port);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void close() {
        if (!this.serverSocket.isClosed()) {
            try {
                this.serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
