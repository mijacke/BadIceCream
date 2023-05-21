package main.entities.other;

import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Class representing an IceBlock element in the game.
 */
public class IceBlock extends GameElement {
    private BufferedImage sprite;
    public IceBlock(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.loadIceBlockImage();
    }

    private void loadIceBlockImage() {
        try {
            this.sprite = ImageIO.read(new File("res/iceBlock.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(this.sprite, getX(), getY(), getWidth(), getHeight(), null);
    }
}
