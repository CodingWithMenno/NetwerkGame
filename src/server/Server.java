package server;

import client.Client;

import java.awt.geom.Point2D;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Server {


    private int port;
    private ServerSocket serverSocket;

    private ArrayList<ServerClient> serverClients = new ArrayList<>();
    private HashMap<String, Thread> clientThreads = new HashMap<>();
    private ArrayList<Socket> sockets = new ArrayList<>();

    private boolean player1Ready = false;
    private boolean player2Ready = false;

    private int status; // 0 = not ready to accept clients, 1 = ready to accept clients, 2 = all possible clients accepted

    private Point2D player1;
    private Point2D player2;


    public Server(int port) {
        this.port = port;
        this.status = 0;
    }

    public void connect() {

        try {
            this.serverSocket = new ServerSocket(port);

            this.status = 1;

            while (this.serverClients.size() != 2) {

                System.out.println("Waiting for clients...");
                Socket socket = this.serverSocket.accept();

                System.out.println("Client connected via address: " + socket.getInetAddress().getHostAddress());

                if (this.serverClients.size() == 0) {
                    ServerClient serverClient = new ServerClient(socket, "Player 1", this);
                    this.serverClients.add(serverClient);
                    this.sockets.add(socket);
                    Thread t = new Thread(serverClient);
                    t.start();
                    this.clientThreads.put("Player 1", t);

                } else {
                    ServerClient serverClient = new ServerClient(socket, "Player 2", this);
                    this.serverClients.add(serverClient);
                    this.sockets.add(socket);
                    Thread t = new Thread(serverClient);
                    t.start();
                    this.clientThreads.put("Player 2", t);
                }

                System.out.println("Connected clients: " + this.serverClients.size());
            }

            this.status = 2;

            sendToAllClients("connected");

            while (!this.player1Ready || !this.player2Ready) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("wachten op ready's");
            }

            sendToAllClients("start game");

        } catch (IOException e) {
            System.out.println("Server closed");
        }
    }

    public void sendToAllClients(String text) {
        for (ServerClient client : this.serverClients) {
            try {
                DataOutputStream out = new DataOutputStream(client.getOut());
                out.writeUTF(text);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void writeStringToOtherSocket(Socket socket, String text) {
        try {
            DataOutputStream out = null;
            if (socket.equals(this.sockets.get(0))) {
                out = new DataOutputStream(this.sockets.get(1).getOutputStream());
            } else {
                out = new DataOutputStream(this.sockets.get(0).getOutputStream());
            }

            out.writeUTF(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public int getStatus() {
        return status;
    }

    public void setPlayer1Ready(boolean player1Ready) {
        System.out.println("player 1 ready");
        this.player1Ready = player1Ready;
    }

    public void setPlayer2Ready(boolean player2Ready) {
        System.out.println("player 2 ready");
        this.player2Ready = player2Ready;
    }
}