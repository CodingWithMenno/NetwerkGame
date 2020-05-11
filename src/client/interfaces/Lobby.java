package client.interfaces;

import client.Client;
import javafx.scene.layout.VBox;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import java.net.Socket;

public class Lobby extends Interface {

    private Socket socket;

    public Lobby(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void draw(FXGraphics2D graphics) {

    }

    @Override
    public void update(ResizableCanvas canvas) {

    }
}
