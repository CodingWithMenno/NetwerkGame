package client.interfaces;

import client.ResourceLoader;
import client.interfaces.Interface;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class MainMenu extends Interface {

    @Override
    public void draw(FXGraphics2D graphics) {
        graphics.drawImage(ResourceLoader.clouds, 0, 0, 1920, 1080, null);
    }

    @Override
    public void update(ResizableCanvas canvas, double delta) {

    }
}
