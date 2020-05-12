package client.gameLogic;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class PhysicsObject extends GameObject{

    private int maxSpeed;
    private int horizontalSpeed;
    private int verticalSpeed;
    private int maxAppliedForce;

    public PhysicsObject(Point2D position, Shape shape, int width, int height, int maxSpeed) {
        super(position, shape, width, height);

        this.maxSpeed = maxSpeed;
        this.horizontalSpeed = 0;
        this.verticalSpeed = 0;
        this.maxAppliedForce = 500;
    }

    public Point2D getPosition() {
        return super.getPosition();
    }

    public void doPhysics() {
        calculateHorizontalSpeed();
        calculateVerticalSpeed();
        moveX();
        moveY();
    }

    private void moveY() { // Move and watch out for collisions
        Rectangle2D.Double shape = new Rectangle2D.Double(super.getPosition().getX(),
                super.getPosition().getY() + this.verticalSpeed, super.getWidth(), super.getHeight());

        GameObject collidObject = shapeCollidesWithObject(shape);

        if (collidObject == null) {
            super.setPosition(new Point2D.Double(shape.getX(), shape.getY()));
        } else {
            if (this.verticalSpeed > 0) { // Moving down
                super.setPosition(new Point2D.Double(super.getPosition().getX(),
                        collidObject.getPosition().getY() - super.getHeight()));
            } else if (this.verticalSpeed < 0) { // Moving up
                super.setPosition(new Point2D.Double(super.getPosition().getX(),
                        collidObject.getPosition().getY() + collidObject.getHeight()));
            }
        }
    }

    private void moveX() { // Move and watch out for collisions
        Rectangle2D.Double shape = new Rectangle2D.Double(super.getPosition().getX() + this.horizontalSpeed,
                super.getPosition().getY(), super.getWidth(), super.getHeight());

        GameObject collidedObject = shapeCollidesWithObject(shape);

        if (collidedObject == null) {
            super.setPosition(new Point2D.Double(shape.getX(), shape.getY()));
        } else {
            if (this.horizontalSpeed > 0) { // Moving right
                super.setPosition(new Point2D.Double(collidedObject.getPosition().getX() - super.getWidth(),
                        super.getPosition().getY()));
            } else if (this.horizontalSpeed < 0) { // Moving left
                super.setPosition(new Point2D.Double(collidedObject.getPosition().getX() + collidedObject.getWidth(),
                        super.getPosition().getY()));
            }
        }
    }

    private GameObject shapeCollidesWithObject(Rectangle2D shape) {
        GameObject collidObject = null;
        for (GameObject object : GameObject.getGameObjects()) {
            if (shape.intersects(object.getPosition().getX(), object.getPosition().getY(), object.getWidth(), object.getHeight())) {
                collidObject = object;
                return collidObject;
            }
        }
        return collidObject;
    }

    private void calculateVerticalSpeed() {
        if (this.verticalSpeed >= 0) { // Falling down
            fall();
        } else { // Going up
            this.verticalSpeed *= 0.90;
        }

        if (this.verticalSpeed < 1 && this.verticalSpeed > -1) { // Damping out
            this.verticalSpeed = 0;
        }
    }

    private void calculateHorizontalSpeed() {
        this.horizontalSpeed *= 0.90;

        if (this.horizontalSpeed < 1 && this.horizontalSpeed > -1) {
            this.horizontalSpeed = 0;
        }
    }

    private void fall() {
        double gravity = 10;

        this.verticalSpeed += gravity;

        if (this.verticalSpeed > this.maxSpeed) {
            this.verticalSpeed = this.maxSpeed;
        }
    }

    public void addForceX(int force) {
        if (force > this.maxAppliedForce) {
            force = this.maxAppliedForce;
        } else if (force < -this.maxAppliedForce) {
            force = -this.maxAppliedForce;
        }

        this.horizontalSpeed = force;
    }

    public void addForceY(int force) {
        if (force > this.maxAppliedForce) {
            force = this.maxAppliedForce;
        } else if (force < -this.maxAppliedForce) {
            force = -this.maxAppliedForce;
        }

        this.verticalSpeed = force;
    }


}
