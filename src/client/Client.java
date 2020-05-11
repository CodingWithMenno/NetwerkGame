package client;

import client.interfaces.Interface;
import client.interfaces.MainMenu;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client extends Application {

    // Server related
    private String hostname;
    private int port;
    private boolean isConnected = true;
    private Socket socket;

    // Other variables
    private ResizableCanvas canvas;

    private MainMenu mainMenu;
    private ResourceLoader resourceLoader;

    private long last;

    private boolean isRunning = true;


    public static void main(String[] args) {
        launch(Client.class);
    }

    public Client() {
    }

    @Override
    public void start(Stage stage) throws Exception {

        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());

        last = -1;
        Thread updateThread = new Thread(() -> {
            while (this.isRunning) {
                long now = System.currentTimeMillis();
                if (last == -1)
                    last = now;
                if (last - now > 1.5) {
                    update((now - last) / 1000000000.0);
                    last = now;
                } else {
                    last += now;
                }
            }
        });
        updateThread.start();

        Thread drawThread = new Thread( () -> {
            while (this.isRunning) {
                draw(g2d);
            }
        });
        drawThread.start();


        stage.setScene(new Scene(mainPane));
        stage.setTitle("NetwerkGame");
        stage.setMinWidth(1920);
        stage.setMaxWidth(1920);
        stage.setMinHeight(1080);
        stage.setMaxHeight(1080);
        stage.setResizable(false);
        stage.show();
        draw(g2d);
    }

    public void init() {
        this.mainMenu = new MainMenu();
        Interface.setInterface(this.mainMenu);

        this.resourceLoader = new ResourceLoader();
        this.resourceLoader.loadImages();
    }

    private void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.WHITE);
        graphics.clearRect(0, 0, (int)canvas.getWidth(), (int)canvas.getHeight());

        if (Interface.getCurrentInterface() != null) {
            Interface.getCurrentInterface().draw(graphics);
        }
    }

    private void update(double delta) {
        if (Interface.getCurrentInterface() != null) {
            Interface.getCurrentInterface().update(this.canvas, delta);
        }
    }

    public void connect() {
        System.out.println("Connecting to server: " + this.hostname + " on port " + this.port);

        Scanner scanner = new Scanner(System.in);

        try {
            this.socket = new Socket(this.hostname, this.port);

            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());


            System.out.print("Enter a nickname: ");
            String nickName = scanner.nextLine();
            out.writeUTF(nickName);

            System.out.println("You are now connected as " + nickName);

            String input = "";

            //boolean isRunning = true;

            Thread readSocketThread = new Thread( () -> {
                receiveDataFromSocket(in);
            });

            readSocketThread.start();

            while (!input.equals("\\quit")) {
                //System.out.print("(" +nickName + "): ");
                input = scanner.nextLine();
                out.writeUTF(input);
                //System.out.print("Sended: " + input);
            }

            this.isConnected = false;

            System.out.println("You are now disconnected");
            readSocketThread.interrupt();

            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void receiveDataFromSocket(DataInputStream in) {
        String received = "";
        while (this.isConnected) {
            try {
                received = in.readUTF();
                System.out.println(received);
            } catch (IOException e) {
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
}
