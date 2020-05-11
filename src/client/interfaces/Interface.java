package client.interfaces;

import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public abstract class Interface {

    private static Interface currentInterface;

    public Interface() {
        currentInterface = null;
    }

    public static void setInterface(Interface i) {
        currentInterface = i;
    }

    public static Interface getCurrentInterface() {
        return currentInterface;
    }

    public abstract void draw(FXGraphics2D graphics);
    public abstract void update(ResizableCanvas canvas);
}
