package client.gameLogic;

import client.Client;
import client.interfaces.GameInterface;
import javafx.scene.input.KeyCode;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Player extends GameObject {

    private Rectangle2D shape;
    private Color color;

    private int horizontalSpeed;
    private double verticalSpeed;

    public Player(Point2D position, Rectangle2D shape, Color color) {
        super(position, shape);
        this.shape = shape;
        this.color = color;

        this.horizontalSpeed = 5;
        this.verticalSpeed = 0;
    }


    public void draw(FXGraphics2D graphics) {
        graphics.setColor(this.color);
        graphics.drawRect((int) (this.shape.getX() - GameInterface.getCamera().getxOffset()), (int) (this.shape.getY() - GameInterface.getCamera().getyOffset()), (int) this.shape.getWidth(), (int) this.shape.getHeight());
    }

    public void update(ResizableCanvas canvas) {
        calculateMovement();
        this.shape = new Rectangle2D.Double(super.getPosition().getX(), super.getPosition().getY(), this.shape.getWidth(), this.shape.getHeight());
    }

    private void calculateMovement() {
        KeyCode keyCode = Client.keyCode;

        if (keyCode == null) {
            keyCode = KeyCode.DIGIT0;
        }

        boolean isOnGround = onGround();
        boolean collisionToTheRight = checkRightCollision();

        switch (keyCode) {
            case W:
                if (isOnGround) {
                    setVerticalSpeed(-10);
                }
                break;
            case A:
                if (!collisionToTheRight) {
                    setHorizontalSpeed(-5);
                }
                break;
        }

        if (!collisionToTheRight) {
            setHorizontalSpeed(7);
        }

        if (!isOnGround) {
            fall();
        }
    }

    private boolean checkRightCollision() {
        Line2D rightSide = new Line2D.Double(getPosition().getX() + this.shape.getWidth(), getPosition().getY(),
                getPosition().getX() + this.shape.getWidth(), getPosition().getY() + (this.shape.getHeight() / 2));

        for (GameObject gameObject : GameObject.getGameObjects()) {
            if (rightSide.intersects(gameObject.getShape())) {
                return true;
            }
        }
        return false;
    }

    private boolean onGround() {
        Line2D feet = new Line2D.Double(getPosition().getX(), getPosition().getY() + this.shape.getHeight(),
                getPosition().getX() + this.shape.getWidth(), getPosition().getY() + this.shape.getHeight());

        for (GameObject gameObject : GameObject.getGameObjects()) {
            if (feet.intersects(gameObject.getShape())) {
                super.setPosition(new Point2D.Double(super.getPosition().getX(), gameObject.getPosition().getY() - this.shape.getHeight() / 2));
                return true;
            }
        }
        return false;
    }

    private void fall() {
        double gravity = 0.6;

        if (this.verticalSpeed + gravity > 5) {
            setVerticalSpeed(this.verticalSpeed);
        }

        setVerticalSpeed(this.verticalSpeed + gravity);
    }

    public void setHorizontalSpeed(int horizontalSpeed) {
        this.horizontalSpeed = horizontalSpeed;
        super.setPosition(new Point2D.Double(super.getPosition().getX() + horizontalSpeed, super.getPosition().getY()));
    }

    public void setVerticalSpeed(double verticalSpeed) {
        this.verticalSpeed = verticalSpeed;
        super.setPosition(new Point2D.Double(super.getPosition().getX(), super.getPosition().getY() + verticalSpeed));
    }
}
