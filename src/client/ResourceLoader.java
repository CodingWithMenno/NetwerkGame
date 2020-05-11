package client;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class ResourceLoader {

    public static BufferedImage clouds;

    public void loadImages() {
        try {
            clouds = ImageIO.read(getClass().getResource("/cloudsBackground.png"));
        } catch (Exception e) {
            System.out.println("Er is iets fout gegaan tijdens het laden van de images");
            e.printStackTrace();
        }
    }
}
