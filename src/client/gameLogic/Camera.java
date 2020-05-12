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
    }

    public float getxOffset() {
        return xOffset;
    }
}
