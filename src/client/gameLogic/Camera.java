package client.gameLogic;

public class Camera {

    private float xOffset;
    private float yOffset;

    public Camera(float xOffset, float yOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public void centerOn(GameObject gameObject) {
        this.xOffset = (float) (gameObject.getPosition().getX() - (1920 / 2) + gameObject.getShape().getWidth() / 2);
        this.yOffset = (float) (gameObject.getPosition().getY() - (1080 / 2) + gameObject.getShape().getHeight() / 2);
    }

    public float getxOffset() {
        return xOffset;
    }

    public float getyOffset() {
        return yOffset;
    }
}
