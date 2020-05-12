package client.gameLogic;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class GameObject {

    private Point2D position;
    private Shape shape;
    private int width;
    private int height;
    private static ArrayList<GameObject> gameObjects = new ArrayList<>(); // All the game objects

    public GameObject(Point2D position, Shape shape, int width, int height) {
        this.position = position;
        this.shape = shape;
        this.width = width;
        this.height = height;
    }

    public Point2D getPosition() {
        return position;
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }

    public Shape getShape() {
        return shape;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public static ArrayList<GameObject> getGameObjects() {
        return gameObjects;
    }


}
