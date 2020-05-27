package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerClient implements Runnable {

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private String name;
    private Server server;
    private boolean isConnected = true;

    public ServerClient(Socket socket, String name, Server server) {
        this.socket = socket;
        this.name = name;
        this.server = server;

        try {
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void writeUTF(String text) {
        try {
            this.out.writeUTF(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (isConnected) {
            try {
                String received = this.in.readUTF();

                //System.out.println("received: " + received);

                if (received.contains("START1")) {
                    this.server.setPlayer1Ready(true);
                } else if (received.contains("START2")) {
                    this.server.setPlayer2Ready(true);
                }

                if (received.contains("position")) {
                    System.out.println("position received");
                    this.server.writeStringToOtherSocket(this.socket, received + this.name);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getName() {
        return name;
    }

    public DataOutputStream getOut() {
        return out;
    }

    public Socket getSocket() {
        return socket;
    }
}
