package client.interfaces;

import client.Client;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class Lobby extends Interface {

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    private boolean isPlayer1;
    private boolean isConnected;

    private ArrayList<String> messages;

    private TextField chatInput;

    public Lobby(Socket socket, boolean isPlayer1) {
        this.chatInput = new TextField("Type here to chat");
        this.chatInput.setFont(Font.font("Helvetica", FontWeight.BOLD, 20));
        this.chatInput.setTranslateX(20);
        this.chatInput.setTranslateY(540);
        this.chatInput.setPrefSize(200, 50);
        this.chatInput.setOnAction(event -> {
            if (this.chatInput.getText().contains("\n")) {
                this.messages.add(this.chatInput.getText());
                sendMessageToServer(this.out, this.chatInput.getText());
                this.chatInput.setText("");
            }
        });

        Client.getMainPane().getChildren().add(this.chatInput);

        this.socket = socket;
        this.isPlayer1 = isPlayer1;
        this.isConnected = true;
        this.messages = new ArrayList<>();

        try {
            this.in = new DataInputStream(this.socket.getInputStream());
            this.out = new DataOutputStream(this.socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Thread receiveThread = new Thread(() -> {
            receiveDataFromSocket(this.in);
        }); receiveThread.start();
    }

    @Override
    public void draw(FXGraphics2D graphics) {
        int y = 500;
        for (String text : this.messages) {
            graphics.drawString(text, 20, y);
            y -= 20;
        }
    }

    @Override
    public void update(ResizableCanvas canvas) {

    }

    private void receiveDataFromSocket(DataInputStream in) {
        String received = "";
        while (this.isConnected) {
            try {
                received = in.readUTF();

                if (this.isPlayer1) {
                    this.messages.add("<Player 1> " + received);
                } else {
                    this.messages.add("<Player 2> " + received);
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
