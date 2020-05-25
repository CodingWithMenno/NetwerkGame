package client.interfaces;

import client.Client;
import javafx.application.Platform;
import javafx.scene.AccessibleAction;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import java.awt.geom.Point2D;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Lobby extends Interface {

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    private boolean isPlayer1;
    private boolean isConnected;
    private boolean startGame;

    private Button startButton;

    private GameInterface gi = null;

    public Lobby(Socket socket, boolean isPlayer1) {
        this.startButton = new Button("Start");
        this.startButton.setFont(Font.font("Helvetica", FontWeight.BOLD, 20));
        this.startButton.setPrefSize(200, 50);
        this.startButton.setOnAction(event -> {
            if (this.isPlayer1) {
                sendMessageToServer(this.out, "START1");
                System.out.println("Sended start1");
            } else {
                sendMessageToServer(this.out, "START2");
                System.out.println("Sended start2");
            }
        });

        Client.getMainPane().getChildren().add(this.startButton);

        this.socket = socket;
        this.isPlayer1 = isPlayer1;
        this.isConnected = true;
        this.startGame = false;

        try {
            this.in = new DataInputStream(this.socket.getInputStream());
            this.out = new DataOutputStream(this.socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Thread receiveThread = new Thread(() -> {
            receiveDataFromSocket(this.in);
        });
        receiveThread.start();
    }

    @Override
    public void draw(FXGraphics2D graphics) {
    }

    @Override
    public void update(ResizableCanvas canvas) {
        if (this.startGame) {
            System.out.println("Javafx game started");
            Platform.runLater(() -> {
                if (this.isPlayer1 && this.gi == null) {
                    this.gi = new GameInterface(new Point2D.Double(300, 200), this.socket);
                    Client.getMainPane().getChildren().remove(this.startButton);
                    Interface.setInterface(this.gi);
                } else if (this.gi == null) {
                    this.gi = new GameInterface(new Point2D.Double(250, 200), this.socket);
                    Client.getMainPane().getChildren().remove(this.startButton);
                    Interface.setInterface(this.gi);
                }
                this.isConnected = false;

            });
        }
    }

    private void receiveDataFromSocket(DataInputStream in) {
        String received = "";
        while (this.isConnected) {
            try {
                received = in.readUTF();
                if (received.equals("start game")) {
                    System.out.println("startGame set to true");
                    this.startGame = true;
                }
            } catch (IOException e) {
            }
        }
        Thread.currentThread().interrupt();
    }

    private void sendMessageToServer(DataOutputStream out, String text) {
        try {
            out.writeUTF(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
