package main.graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**

 Represents a sprite sheet that contains multiple sprites.
 */
public class SpriteSheet {
    private BufferedImage sheet;

    /**
     * Constructs a SpriteSheet object with the specified image file path.
     *
     * @param path the file path of the sprite sheet image.
     */
    public SpriteSheet(String path) {
        try {
            this.sheet = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Retrieves a specific sprite from the sprite sheet based on the specified coordinates and dimensions.
     *
     * @param x the x-coordinate of the sprite on the sprite sheet.
     * @param y the y-coordinate of the sprite on the sprite sheet.
     * @param width the width of the sprite.
     * @param height the height of the sprite.
     * @return the BufferedImage representing the specified sprite.
     */
    public BufferedImage getSprite(int x, int y, int width, int height) {
        return this.sheet.getSubimage(x, y, width, height);
    }
}





