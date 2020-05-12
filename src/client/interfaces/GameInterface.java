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

public class GameInterface extends Interface {

    private Player player;

    private Rectangle2D positionPlayer2;

    private static Camera camera;


    public GameInterface() {
        Point2D position = new Point2D.Double(300, 200);
        int width = 20;
        int height = 20;
        this.player = new Player(position, new Rectangle2D.Double(position.getX(), position.getY(), width, height), Color.BLUE);

        camera = new Camera(0, 0);

        this.positionPlayer2 = new Rectangle2D.Double(0, 0, width, height);

        GameObject.getGameObjects().add(new Platform(new Point2D.Double(0, 590), new Rectangle2D.Double(0, 600, 500, 40)));
        GameObject.getGameObjects().add(new Platform(new Point2D.Double(500, 580), new Rectangle2D.Double(500, 590, 500, 40)));
        GameObject.getGameObjects().add(new Platform(new Point2D.Double(1000, 580), new Rectangle2D.Double(1010, 590, 500, 40)));
        GameObject.getGameObjects().add(this.player);
    }

    @Override
    public void draw(FXGraphics2D graphics) {
        for (GameObject gameObject : GameObject.getGameObjects()) {
            gameObject.draw(graphics);
        }

        graphics.setColor(Color.GREEN);
        graphics.drawRect((int) (this.positionPlayer2.getX() - camera.getxOffset()), (int) this.positionPlayer2.getY(),
                (int) this.positionPlayer2.getWidth(), (int) this.positionPlayer2.getHeight());
    }

    @Override
    public void update(ResizableCanvas canvas) {
        for (GameObject gameObject : GameObject.getGameObjects()) {
            gameObject.update(canvas);
        }

        getPositionPlayer2();
        camera.centerOn(this.player);
    }

    private void getPositionPlayer2() {

    }

    public static Camera getCamera() {
        return camera;
    }
}
