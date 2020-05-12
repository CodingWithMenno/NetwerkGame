package client.interfaces;

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


    public GameInterface() {
        Point2D position = new Point2D.Double(200, 200);
        int width = 20;
        int height = 20;
        this.player = new Player(position, new Rectangle2D.Double(position.getX(), position.getY(), width, height), Color.BLUE);

        this.positionPlayer2 = new Rectangle2D.Double(0, 0, width, height);

        GameObject.getGameObjects().add(new Platform(new Point2D.Double(0, 600), new Rectangle2D.Double(0, 600, 500, 40)));

        GameObject.getGameObjects().add(this.player);
    }

    @Override
    public void draw(FXGraphics2D graphics) {
        for (GameObject gameObject : GameObject.getGameObjects()) {
            gameObject.draw(graphics);
        }

        graphics.setColor(Color.red);
        graphics.fill(this.positionPlayer2);
    }

    @Override
    public void update(ResizableCanvas canvas) {
        for (GameObject gameObject : GameObject.getGameObjects()) {
            gameObject.update(canvas);
        }

        getPositionPlayer2();
    }

    private void getPositionPlayer2() {

    }
}
