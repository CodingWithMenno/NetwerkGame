package server;

import java.io.*;
import java.net.Socket;

public class ServerClient implements Runnable {

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
//    private ObjectInputStream objIn;
//    private ObjectOutputStream objOut;
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
//            this.objOut = new ObjectOutputStream(this.socket.getOutputStream());
//            this.objIn = new ObjectInputStream(this.socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeUTF(String string) {
        try {
            this.out.writeUTF(string);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        boolean started = false;

        while (this.isConnected) {
            try {
                String received = this.in.readUTF();

                System.out.println("received: " + received);

                if (received.contains("START1")) {
                    this.server.setPlayer1Ready(true);
                    started = true;
                } else if (received.contains("START2")) {
                    this.server.setPlayer2Ready(true);
                    started = true;
                }

                if (started) {
                    receivePlayer();
                    return;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void receivePlayer() {
        boolean connected = true;

        while (connected) {
            try {
                String o = this.in.readUTF();
                this.server.writePlayerToOtherSocket(this, o);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getName() {
        return name;
    }

//    public ObjectOutputStream getObjOut() {
//        return objOut;
//    }
//
//    public ObjectInputStream getObjIn() {
//        return objIn;
//    }

    public Socket getSocket() {
        return socket;
    }
}
