package client.interfaces;

import client.gameLogic.Camera;
import client.gameLogic.GameObject;
import client.gameLogic.Platform;
import client.gameLogic.Player;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class GameInterface extends Interface {

    private Player player;

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private ObjectInputStream objIn;
    private ObjectOutputStream objOut;

    private Player player2;
    private boolean isOnline;

    private static Camera camera;

    private Random random;


    public GameInterface() {
        this.isOnline = false;

        Point2D.Double position = new Point2D.Double(300, 200);
        int width = 20;
        int height = 20;
        this.player = new Player(position, new Rectangle2D.Double(position.getX(), position.getY(), width, height), Color.BLUE, true);

        camera = new Camera(0, 0);

        GameObject.getGameObjects().add(this.player);
        GameObject.getGameObjects().add(new Platform(new Point2D.Double(0, 590), new Rectangle2D.Double(0, 600, 1000, 40)));

        this.random = new Random();
    }

    public GameInterface(Point2D.Double position, Socket socket, ObjectInputStream objIn, ObjectOutputStream objOut) {
        System.out.println("Game has started");
        this.socket = socket;
        this.isOnline = true;

        try {
            this.in = new DataInputStream(this.socket.getInputStream());
            this.objIn = objIn;
            this.out = new DataOutputStream(this.socket.getOutputStream());
            this.objOut = objOut;
            this.objIn.reset();
            this.objOut.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int width = 20;
        int height = 20;
        this.player = new Player(position, new Rectangle2D.Double(position.getX(), position.getY(), width, height), Color.BLUE, true);

        camera = new Camera(0, 0);

        if (position.getX() == 300) {
            Point2D.Double player2Pos = new Point2D.Double(250, 200);
            this.player2 = new Player(player2Pos, new Rectangle2D.Double(player2Pos.getX(), player2Pos.getY(), width, height), Color.ORANGE, false);
        } else {
            Point2D.Double player2Pos = new Point2D.Double(300, 200);
            this.player2 = new Player(player2Pos, new Rectangle2D.Double(player2Pos.getX(), player2Pos.getY(), width, height), Color.ORANGE, false);
        }

        GameObject.getGameObjects().add(this.player);
        GameObject.getGameObjects().add(this.player2);
        GameObject.getGameObjects().add(new Platform(new Point2D.Double(0, 590), new Rectangle2D.Double(0, 600, 1000, 40)));

        this.random = new Random();

        Thread receiveThread = new Thread(() -> {
            receiveDataFromSocket(this.objIn);
        });
        receiveThread.start();
    }

    @Override
    public void draw(FXGraphics2D graphics) {
        for (GameObject gameObject : GameObject.getGameObjects()) {
            gameObject.draw(graphics);
        }
    }

    @Override
    public void update(ResizableCanvas canvas) {
        generateWorld();

        for (GameObject gameObject : GameObject.getGameObjects()) {
            gameObject.update(canvas);
        }

        if (isOnline) {
            //sendPlayerInfo(this.objOut, this.player);
        }

        camera.centerOn(this.player);
    }

    private void generateWorld() {
        ArrayList<GameObject> deleted = new ArrayList<>();

        for (GameObject gameObject : GameObject.getGameObjects()) {
            double differenceX = this.player.getPosition().getX() - gameObject.getPosition().getX();
            if (differenceX > 2000) {
                deleted.add(gameObject);
            }
        }

        for (GameObject gameObject : deleted) {
            GameObject.getGameObjects().remove(gameObject);
        }

        if (GameObject.getGameObjects().size() < 12) {
            GameObject latestObject = GameObject.getGameObjects().get(GameObject.getGameObjects().size() - 1);
            int randomX = (int) (latestObject.getPosition().getX() + latestObject.getShape().getWidth() + this.random.nextInt(150) + 5);
            int randomY = (int) (latestObject.getPosition().getY() + this.random.nextInt(175) - (175 / 2));
            int randomWidth = this.random.nextInt(500) + 100;
            GameObject.getGameObjects().add(new Platform(new Point2D.Double(randomX, randomY), new Rectangle2D.Double(randomX, randomY + 10, randomWidth, 40)));
        }
    }

    private void setPositionPlayer2(Point2D.Double player2) {
        this.player2.setPosition(player2);
    }

    private void receiveDataFromSocket(ObjectInputStream in) {
//        String received = "";
//        while (this.isOnline) {
//            try {
//                received = in.readUTF();
//                System.out.println("received: " + received);
//                if (received.contains("position:")) {
//                    String positionString = received.substring(9);
//                    String[] positions = positionString.split("\\s");
//
//                    if (positions[0].contains("n") || positions[1].contains("n")) { continue; }
//
//                    double x = Double.parseDouble(positions[0]);
//                    double y = Double.parseDouble(positions[1]);
//                    //System.out.println("currentX: " + player2.getPosition().getX() + " currentY: " + player2.getPosition().getY() + " X: " + x + " Y: " + y);
//
//                    setPositionPlayer2(new Point2D.Double(x, y));
//                }
//
//            } catch (IOException | NumberFormatException e) {
//                e.printStackTrace();
//            }
//        }
//        Thread.currentThread().interrupt();

        while (this.isOnline) {
            try {
                Point2D.Double player2 = (Point2D.Double) in.readObject();
                System.out.println(player2);
                setPositionPlayer2(player2);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        Thread.currentThread().interrupt();
    }

    private void sendPlayerInfo(ObjectOutputStream out, Player player) {
        try {
            out.writeObject(player.getPosition());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Camera getCamera() {
        return camera;
    }
}
