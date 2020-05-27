package server;

import client.gameLogic.Player;

import java.io.*;
import java.net.Socket;

public class ServerClient implements Runnable {

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private ObjectInputStream objIn;
    private ObjectOutputStream objOut;
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
            this.objOut = new ObjectOutputStream(socket.getOutputStream());
            this.objIn = new ObjectInputStream(socket.getInputStream());
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
        Thread playerInfoThread = new Thread(this::receivePlayer);
        playerInfoThread.start();

        while (this.isConnected) {
            try {
                String received = this.in.readUTF();

                //System.out.println("received: " + received);

                if (received.contains("START1")) {
                    this.server.setPlayer1Ready(true);
                } else if (received.contains("START2")) {
                    this.server.setPlayer2Ready(true);
                }

//                if (received.contains("position")) {
//                    System.out.println("position received");
//                    this.server.writeStringToOtherSocket(this.socket, received + this.name);
//                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void receivePlayer() {
        while (this.isConnected) {
            try {
                Object o = this.objIn.readObject();
                this.server.writePlayerToOtherSocket(this.objOut, o);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public String getName() {
        return name;
    }

    public ObjectOutputStream getObjOut() {
        return objOut;
    }

    public ObjectInputStream getObjIn() {
        return objIn;
    }

    public Socket getSocket() {
        return socket;
    }
}
