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
import java.util.ArrayList;
import java.util.Random;

public class GameInterface extends Interface {

    private Player player;

    private Rectangle2D positionPlayer2;

    private static Camera camera;

    private Random random;


    public GameInterface() {
        Point2D position = new Point2D.Double(300, 200);
        int width = 20;
        int height = 20;
        this.player = new Player(position, new Rectangle2D.Double(position.getX(), position.getY(), width, height), Color.BLUE);

        camera = new Camera(0, 0);

        this.positionPlayer2 = new Rectangle2D.Double(0, 0, width, height);

        GameObject.getGameObjects().add(this.player);
        GameObject.getGameObjects().add(new Platform(new Point2D.Double(0, 590), new Rectangle2D.Double(0, 600, 1000, 40)));

        this.random = new Random();
    }

    @Override
    public void draw(FXGraphics2D graphics) {
        for (GameObject gameObject : GameObject.getGameObjects()) {
            gameObject.draw(graphics);
        }

        graphics.setColor(Color.GREEN);
        graphics.drawRect((int) (this.positionPlayer2.getX() - camera.getxOffset()), (int) (this.positionPlayer2.getY() - camera.getyOffset()),
                (int) this.positionPlayer2.getWidth(), (int) this.positionPlayer2.getHeight());
    }

    @Override
    public void update(ResizableCanvas canvas) {
        generateWorld();

        for (GameObject gameObject : GameObject.getGameObjects()) {
            gameObject.update(canvas);
        }

        getPositionPlayer2();
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

        if (GameObject.getGameObjects().size() < 10) {
            GameObject latestObject = GameObject.getGameObjects().get(GameObject.getGameObjects().size() - 1);
            int randomX = (int) (latestObject.getPosition().getX() + latestObject.getShape().getWidth() + this.random.nextInt(150) + 5);
            int randomY = (int) (latestObject.getPosition().getY() + this.random.nextInt(175) - (175 / 2));
            int randomWidth = this.random.nextInt(500) + 100;
            GameObject.getGameObjects().add(new Platform(new Point2D.Double(randomX, randomY), new Rectangle2D.Double(randomX, randomY + 10, randomWidth, 40)));
        }
    }

    private void getPositionPlayer2() {

    }

    public static Camera getCamera() {
        return camera;
    }
}
