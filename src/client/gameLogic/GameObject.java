package client.gameLogic;

import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class GameObject implements Serializable {

    private Point2D.Double position;
    private Rectangle2D.Double shape;
    private static ArrayList<GameObject> gameObjects = new ArrayList<>(); // All the game objects

    public GameObject(Point2D.Double position, Rectangle2D.Double shape) {
        this.position = position;
        this.shape = shape;
    }

    public Point2D getPosition() {
        return this.position;
    }

    public void setPosition(Point2D.Double position) {
        this.position = position;
    }

    public Rectangle2D getShape() {
        return shape;
    }

    public synchronized static ArrayList<GameObject> getGameObjects() {
        return gameObjects;
    }

    public void draw(FXGraphics2D graphics) {

    }

    public void update(ResizableCanvas canvas) {

    }
}
