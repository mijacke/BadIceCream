package main.levels.separators;

import main.levels.Level;

/**
 * The LevelUpdater class is responsible for updating the level, including updating enemies, fruits, and ice blocks.
 * It implements the Updatable interface to define the update behavior.
 */
public class LevelUpdater implements Updatable {
    private final Level level;

    /**
     * Constructs a LevelUpdater object with the specified level.
     *
     * @param level The level to update.
     */
    public LevelUpdater(Level level) {
        this.level = level;
    }

    /**
     * Updates the level by updating enemies, fruits, and ice blocks if present.
     */
    @Override
    public void update() {
        if (this.level.getEnemyManager().isPresent() && this.level.getFruitManager().isPresent() && this.level.getIceManager().isPresent()) {
            this.level.getEnemyManager().get().update();
            this.level.getFruitManager().get().update();
            this.level.getIceManager().get().update();
        }
    }
}
