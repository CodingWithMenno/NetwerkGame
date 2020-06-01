package client.interfaces;

import client.Client;
import client.ResourceLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import java.io.*;
import java.net.Socket;

public class JoinMenu extends Interface {

    // Interface
    private VBox vBox;

    private Label title;

    private HBox hostNumberBox;
    private Label hostNumberLabel;
    private TextField hostNumberText;

    private Button joinButton;

    private Button backButton;

    // Client variables
    private String hostName;
    private int port = 500;
    private Socket socket;
    private boolean connected = false;

    public JoinMenu() {
        this.vBox = new VBox();
        this.vBox.setSpacing(30);

        this.title = new Label("Join a friend");
        this.title.setPrefSize(400, 100);
        this.title.setFont(Font.font("Helvetica", FontWeight.BOLD, 30));

        this.hostNumberBox = new HBox();
        this.hostNumberBox.setSpacing(10);
        this.hostNumberBox.setTranslateX(-(175 / 2));

        this.hostNumberLabel = new Label("Host number");
        this.hostNumberLabel.setPrefSize(175, 50);
        this.hostNumberLabel.setFont(Font.font("Helvetica", FontWeight.BOLD, 20));

        this.hostNumberText = new TextField();
        this.hostNumberText.setPrefSize(175, 50);
        this.hostNumberText.setFont(Font.font("Helvetica", FontWeight.BOLD, 20));

        this.hostNumberBox.getChildren().addAll(this.hostNumberLabel, this.hostNumberText);

        this.joinButton = new Button("Join game");
        this.joinButton.setPrefSize(200, 50);
        this.joinButton.setFont(Font.font("Helvetica", FontWeight.BOLD, 20));
        this.joinButton.setOnAction(event -> {
            if (this.hostNumberText.getText().isEmpty()) {
                this.hostNumberText.setText("required field");
                return;
            }

            this.hostName = this.hostNumberText.getText();

            connectToServer();
        });

        this.backButton = new Button("Back");
        this.backButton.setPrefSize(200, 50);
        this.backButton.setFont(Font.font("Helvetica", FontWeight.BOLD, 20));
        this.backButton.setOnAction(event -> {
            Client.getMainPane().getChildren().remove(this.vBox);
            Interface.setInterface(new MainMenu());
        });

        this.vBox.setTranslateX(1920 / 2 - 100);
        this.vBox.setTranslateY(1080 / 4 * 1.6);
        this.vBox.getChildren().addAll(this.title, this.hostNumberBox, this.joinButton, this.backButton);
        Client.getMainPane().getChildren().add(this.vBox);
    }

    private void connectToServer() {
        this.title.setText("Connecting to a friend");

        try {
            this.socket = new Socket(this.hostName, this.port);
            ObjectOutputStream out = new ObjectOutputStream(this.socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(this.socket.getInputStream());
            out.flush();
            if (this.socket != null) {
                this.title.setText("Connected to your friend");
                this.title.setTranslateX(-80);
                this.connected = true;

                Client.getMainPane().getChildren().remove(this.vBox);
                Interface.setInterface(new Lobby(this.socket, false, in, out));
            }


        } catch (IOException e) {
            this.title.setText("Failed to join your friend");
            this.title.setTranslateX(-60);
            e.printStackTrace();
        }
    }

    @Override
    public void draw(FXGraphics2D graphics) {
        graphics.drawImage(ResourceLoader.clouds, 0, 0, 1920, 1080, null);
    }

    @Override
    public void update(ResizableCanvas canvas) {

    }
}
