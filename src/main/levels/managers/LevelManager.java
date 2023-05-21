package main.levels.managers;

import main.levels.Level;
import java.util.ArrayList;

/**
 * The LevelManager class manages the levels in the game, including adding levels, accessing the current level,
 * progressing to the next level, and checking if it is the last level.
 */
public class LevelManager {
    private final ArrayList<Level> levels;
    private int currentLevelIndex;

    /**
     * Constructs a LevelManager object.
     */
    public LevelManager() {
        this.levels = new ArrayList<>();
        this.currentLevelIndex = 0;
    }

    /**
     * Adds a level to the manager.
     *
     * @param level The level to add.
     */
    public void addLevel(Level level) {
        this.levels.add(level);
    }

    /**
     * Retrieves the current level.
     *
     * @return The current level.
     * @throws RuntimeException if there is no current level (the game may be over).
     */
    public Level getCurrentLevel() {
        if (this.currentLevelIndex >= this.levels.size()) {
            throw new RuntimeException("No current level. The game may be over.");
        }
        return this.levels.get(this.currentLevelIndex);
    }

    /**
     * Progresses to the next level.
     * Only advances to the next level if the current level is not the last level.
     */
    public void nextLevel() {
        if (!this.isLastLevel()) {
            this.currentLevelIndex++;
        }
    }

    /**
     * Checks if the current level is the last level.
     *
     * @return {@code true} if it is the last level, {@code false} otherwise.
     */
    public boolean isLastLevel() {
        return this.currentLevelIndex == this.levels.size() - 1;
    }
}
