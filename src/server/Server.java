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

    private HashMap<String, Socket> clients = new HashMap();

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

            while (this.clients.size() != 2) {

                System.out.println("Waiting for clients...");
                Socket socket = this.serverSocket.accept();

                System.out.println("Client connected via address: " + socket.getInetAddress().getHostAddress());

                if (this.clients.size() == 0) {
                    this.clients.put("player1", socket);
                } else {
                    this.clients.put("player2", socket);
                }

                System.out.println("Connected clients: " + this.clients.size());
            }

            System.out.println("all connected");
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
        for (String player : this.clients.keySet()) {
            try {
                DataOutputStream out = new DataOutputStream(this.clients.get(player).getOutputStream());
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