package client.gameLogic;

import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.Random;

public class Player {

    private Point2D position;
    private int speed;

    private Ellipse2D shape;
    private Color color;

    private int width;
    private int height;

    public Player() {
        this.position = new Point2D.Double(200, 200);
        this.speed = 10000;
        this.width = 50;
        this.height = 50;
        this.shape = new Ellipse2D.Double(this.position.getX(), this.position.getY(), this.width, this.height);

        Random random = new Random();

        this.color = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
    }

    public void draw(FXGraphics2D graphics) {

    }

    public void update(ResizableCanvas canvas) {

    }
}
