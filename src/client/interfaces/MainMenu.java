package client.interfaces;

import client.Client;
import client.ResourceLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.WindowEvent;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class MainMenu extends Interface {

    private Button hostButton;
    private Button joinButton;
    private Button exitButton;
    private Button practiceButton;
    private VBox vBox;

    public MainMenu() {
        this.vBox = new VBox();

        this.practiceButton = new Button("Practice");
        this.practiceButton.setPrefSize(200, 50);
        this.practiceButton.setFont(Font.font("Helvetica", FontWeight.BOLD, 20));
        this.practiceButton.setOnAction(event -> {
            // Go practice offline
        });

        this.hostButton = new Button("Host");
        this.hostButton.setPrefSize(200, 50);
        this.hostButton.setFont(Font.font("Helvetica", FontWeight.BOLD, 20));
        this.hostButton.setOnAction(event -> {
            Client.getMainPane().getChildren().remove(this.vBox);
            Interface.setInterface(new HostMenu());
        });

        this.joinButton = new Button("Join");
        this.joinButton.setPrefSize(200, 50);
        this.joinButton.setFont(Font.font("Helvetica", FontWeight.BOLD, 20));
        this.joinButton.setOnAction(event -> {
            Client.getMainPane().getChildren().remove(this.vBox);
            Interface.setInterface(new JoinMenu());
        });

        this.exitButton = new Button("Exit");
        this.exitButton.setPrefSize(200, 50);
        this.exitButton.setFont(Font.font("Helvetica", FontWeight.BOLD, 20));
        this.exitButton.setOnAction(event -> {
            Client.getStage().fireEvent(new WindowEvent(Client.getStage(), WindowEvent.WINDOW_CLOSE_REQUEST));
        });

        this.vBox.getChildren().addAll(this.practiceButton, this.hostButton, this.joinButton, this.exitButton);
        this.vBox.setTranslateX((1920 / 2) - 200 / 2);
        this.vBox.setTranslateY(1080 / 4 * 2);
        this.vBox.setSpacing(50);

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
