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
                    this.serverClients.add(new ServerClient(socket, "Player 1", this));
                } else {
                    this.serverClients.add(new ServerClient(socket, "Player 2", this));
                }

                System.out.println("Connected clients: " + this.serverClients.size());
            }

            this.status = 2;

            sendToAllClients("connected");

        } catch (IOException e) {
            System.out.println("Server closed");
        }
    }

    private void handleInformation() {
        boolean isRunning = true;
        while (isRunning) {
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


    public void writeStringToSocket(Socket socket, String text) {
        try {
            socket.getOutputStream().write(text.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeClient() {

    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public int getStatus() {
        return status;
    }
}