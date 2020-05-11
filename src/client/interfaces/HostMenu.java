package client.interfaces;

import client.Client;
import client.ResourceLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;
import server.Server;

import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.Arrays;

public class HostMenu extends Interface {

    private String hostname;
    private int port = 500;
    private Socket socket;
    private DataInputStream dataIn;
    private DataOutputStream dataOut;

    private Thread readSocketThread;

    private Server server;

    private boolean playerConnected = false;

    // Interface
    private VBox vBox;
    private Label hostLabel;
    private Button exitButton;
    private Label statusLabel;
    private Button updateButton;

    public HostMenu() {
        Thread serverThread = new Thread(() -> startNewServer());
        serverThread.start();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while (this.server.getStatus() == 0) {
            try {
                System.out.println("Waiting for server to start");
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        this.hostname = "localhost";
        connect();

        String ip = "";
        try (final DatagramSocket socket = new DatagramSocket()) {
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            ip = socket.getLocalAddress().getHostAddress();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        this.vBox = new VBox();

        this.statusLabel = new Label("Waiting on a friend...");
        this.statusLabel.setFont(Font.font("Helvetica", FontWeight.BOLD, 30));
        this.statusLabel.setPrefSize(400, 50);
        this.statusLabel.setTranslateX(-50);

        this.hostLabel = new Label("Host Number : " + ip);
        this.hostLabel.setFont(Font.font("Helvetica", FontWeight.BOLD, 20));
        this.hostLabel.setPrefSize(395, 100);
        this.hostLabel.setTranslateX(-30);

        this.updateButton = new Button("Update");
        this.updateButton.setPrefSize(200, 50);
        this.updateButton.setFont(Font.font("Helvetica", FontWeight.BOLD, 20));
        this.updateButton.setOnAction(event -> {
            if (this.playerConnected && this.updateButton.getText().equals("Update")) {
                this.statusLabel.setText("A friend connected");
                this.updateButton.setText("Go to the lobby");
            } else if (this.updateButton.getText().equals("Go to the lobby")) {
                Client.getMainPane().getChildren().remove(this.vBox);
                Interface.setInterface(new Lobby(this.socket));
            }
        });

        this.exitButton = new Button("Back");
        this.exitButton.setPrefSize(200, 50);
        this.exitButton.setFont(Font.font("Helvetica", FontWeight.BOLD, 20));
        this.exitButton.setOnAction(event -> {
            Client.getMainPane().getChildren().remove(this.vBox);
            closeServer();
            Interface.setInterface(new MainMenu());
        });

        this.vBox.getChildren().addAll(this.statusLabel, this.hostLabel, this.updateButton, this.exitButton);
        this.vBox.setTranslateX((1920 / 2) - 200 / 2);
        this.vBox.setTranslateY(1080 / 4 * 1.7);
        this.vBox.setSpacing(20);

        Client.getMainPane().getChildren().add(this.vBox);
    }

    @Override
    public void draw(FXGraphics2D graphics) {
        graphics.drawImage(ResourceLoader.clouds, 0, 0, 1920, 1080, null);
    }

    @Override
    public void update(ResizableCanvas canvas) {
    }

    private void startNewServer() {
        this.server = new Server(this.port);
        this.server.connect();
    }

    public void closeServer() {
        try {
            this.readSocketThread.interrupt();
            this.socket.close();
            this.server.getServerSocket().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void connect() {
        System.out.println("Connecting to server: " + this.hostname + " on port " + this.port);

        try {
            this.socket = new Socket(this.hostname, this.port);

            this.dataIn = new DataInputStream(socket.getInputStream());
            this.dataOut = new DataOutputStream(socket.getOutputStream());

            this.readSocketThread = new Thread(() -> {
                receiveDataFromSocket(this.dataIn);
            });
            this.readSocketThread.start();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void receiveDataFromSocket(DataInputStream in) {
        String received = "";
        try {
            received = in.readUTF();
            this.playerConnected = true;
        } catch (IOException e) {
        }
        Thread.currentThread().interrupt();
    }
}
