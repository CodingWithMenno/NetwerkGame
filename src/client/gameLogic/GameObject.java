package client.gameLogic;

import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class GameObject {

    private Point2D position;
    private Rectangle2D shape;
    private static ArrayList<GameObject> gameObjects = new ArrayList<>(); // All the game objects

    public GameObject(Point2D position, Rectangle2D shape) {
        this.position = position;
        this.shape = shape;
    }

    public Point2D getPosition() {
        return position;
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }

    public Rectangle2D getShape() {
        return shape;
    }

    public static ArrayList<GameObject> getGameObjects() {
        return gameObjects;
    }

    public void draw(FXGraphics2D graphics) {

    }

    public void update(ResizableCanvas canvas) {

    }
}
