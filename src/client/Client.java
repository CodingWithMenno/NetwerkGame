package client;

import client.interfaces.Interface;
import client.interfaces.MainMenu;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Client extends Application {

    // Other variables
    private ResizableCanvas canvas;
    private static StackPane mainPane;
    private static Stage stage;

    private MainMenu mainMenu;
    private ResourceLoader resourceLoader;

    private long last;

    public static KeyCode keyCode;

    public Thread fxThread;

    public static void main(String[] args) {
        launch(Client.class);
    }

    public Client() {
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());

        ScheduledExecutorService updateThread = Executors
                .newSingleThreadScheduledExecutor();
        updateThread.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                update();
                draw(g2d);
            }
        }, 0, 1000 / 60, TimeUnit.MILLISECONDS);


        Scene scene = new Scene(mainPane);
        scene.setOnKeyPressed(keyEvent -> keyCode = keyEvent.getCode());
        scene.setOnKeyReleased(keyEvent -> keyCode = null);
        stage.setScene(scene);
        stage.setTitle("NetwerkGame");
        stage.setMinWidth(1920);
        stage.setMaxWidth(1920);
        stage.setMinHeight(1080);
        stage.setMaxHeight(1080);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.setOnCloseRequest(event -> {
            updateThread.shutdown();
        });

        stage.show();
    }

    public void init() {
        mainPane = new StackPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.getChildren().add(canvas);

        this.mainMenu = new MainMenu();
        Interface.setInterface(this.mainMenu);

        this.resourceLoader = new ResourceLoader();
        this.resourceLoader.loadImages();
    }

    private void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.WHITE);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        if (Interface.getCurrentInterface() != null) {
            Interface.getCurrentInterface().draw(graphics);
        }
        else{
            System.out.println("NO INTERFACE DETECTED");
        }
    }

    private void update() {
        if (Interface.getCurrentInterface() != null) {
            Interface.getCurrentInterface().update(this.canvas);
        }
    }

    public static StackPane getMainPane() {
        return mainPane;
    }

    public static Stage getStage() {
        return stage;
    }
}
