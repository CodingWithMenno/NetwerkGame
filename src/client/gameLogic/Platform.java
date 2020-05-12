package client.gameLogic;

import client.interfaces.GameInterface;
import org.jfree.fx.FXGraphics2D;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Platform extends GameObject {


    public Platform(Point2D position, Rectangle2D shape) {
        super(position, shape);
    }

    @Override
    public void draw(FXGraphics2D graphics) {
        graphics.setColor(Color.RED);
        graphics.drawRect((int) (super.getPosition().getX() - GameInterface.getCamera().getxOffset()), (int) super.getPosition().getY(), (int) super.getShape().getWidth(), (int) super.getShape().getHeight());
    }
}
