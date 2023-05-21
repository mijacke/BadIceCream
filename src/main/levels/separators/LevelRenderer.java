package main.levels.separators;

import main.levels.Level;

import java.awt.Graphics;

/**
 * The LevelRenderer class is responsible for rendering the level, including walls, enemies, fruits, and ice blocks.
 * It implements the Renderable interface to define the rendering behavior.
 */
public class LevelRenderer implements Renderable {
    private final Level level;

    /**
     * Constructs a LevelRenderer object with the specified level.
     *
     * @param level The level to render.
     */
    public LevelRenderer(Level level) {
        this.level = level;
    }

    /**
     * Renders the level by drawing walls, enemies, fruits, and ice blocks on the Graphics object.
     *
     * @param g The Graphics object to render on.
     */
    @Override
    public void render(Graphics g) {
        int cellWidth = this.level.getCellWidth();
        int cellHeight = this.level.getCellHeight();

        // Render walls
        for (int y = 0; y < this.level.getLevelLayout().layout().length; y++) {
            for (int x = 0; x < this.level.getLevelLayout().layout()[0].length; x++) {
                if (this.level.getLevelLayout().layout()[y][x] == Level.WALL) {
                    g.drawImage(this.level.getSprite(), x * cellWidth, y * cellHeight, cellWidth, cellHeight, null);
                }
            }
        }

        // Render enemies, fruits, and ice blocks if present
        if (this.level.getEnemyManager().isPresent() && this.level.getFruitManager().isPresent() && this.level.getIceManager().isPresent()) {
            this.level.getEnemyManager().get().render(g);
            this.level.getFruitManager().get().render(g);
            this.level.getIceManager().get().render(g);
        }
    }
}
