package client.interfaces;

import client.Client;
import client.ResourceLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.WindowEvent;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class MainMenu extends Interface {

    private Button hostButton;
    private Button joinButton;
    private Button exitButton;
    private VBox vBox;

    public MainMenu() {
        this.vBox = new VBox();

        this.hostButton = new Button("Host");
        this.hostButton.setPrefSize(200, 50);

        this.joinButton = new Button("Join");
        this.joinButton.setPrefSize(200, 50);

        this.exitButton = new Button("Exit");
        this.exitButton.setPrefSize(200, 50);
        this.exitButton.setOnAction(event -> {
            Client.getStage().fireEvent(new WindowEvent(Client.getStage(), WindowEvent.WINDOW_CLOSE_REQUEST));
        });

        this.vBox.getChildren().addAll(this.hostButton, this.joinButton, this.exitButton);
        this.vBox.setTranslateX((1920 / 2) - 200 / 2);
        this.vBox.setTranslateY(1080 / 4 * 2.5);
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
}
