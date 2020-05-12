package client.gameLogic;

import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.Random;

public class Player extends PhysicsObject {

    private Ellipse2D shape;
    private Color color;

    private int width;
    private int height;

    public Player(Point2D position, Shape shape, int width, int height, Color color, int maxSpeed) {
        super(position, shape, width, height, maxSpeed);
        this.shape = new Ellipse2D.Double(super.getPosition().getX(), super.getPosition().getY(), this.width, this.height);
        this.color = color;
        this.width = width;
        this.height = height;
    }


    public void draw(FXGraphics2D graphics) {
        this.shape = new Ellipse2D.Double(super.getPosition().getX(), super.getPosition().getY(), this.width, this.height);
        graphics.draw(this.shape);
    }

    public void update(ResizableCanvas canvas) {
        super.doPhysics();
    }
}
