package main.levels.managers;

import main.entities.other.EnemySpawn;
import main.entities.other.Player;
import main.entities.enemies.Guard;
import main.entities.enemies.ThermalTitan;
import main.entities.enemies.Ghost;
import main.entities.enemies.Enemy;
import main.entities.enemies.Yeti;
import main.levels.Level;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

/**
 * The EnemyManager class manages the enemies in a level.
 */
public class EnemyManager {
    private final Level level;
    private final List<Enemy> enemies;
    private final List<EnemySpawn> enemies2;

    /**
     * Constructs a new EnemyManager object with the specified level and initial enemy spawns.
     *
     * @param level   The level object.
     * @param enemies The initial enemy spawns.
     */
    public EnemyManager(Level level, List<EnemySpawn> enemies) {
        this.level = level;
        this.enemies = new ArrayList<>();
        this.enemies2 = enemies;
    }

    /**
     * Spawns the enemies at their respective positions based on the cell dimensions.
     *
     * @param cellWidth  The width of a single cell.
     * @param cellHeight The height of a single cell.
     */
    public void spawnEnemies(int cellWidth, int cellHeight) {
        for (EnemySpawn enemySpawn : this.enemies2) {
            this.spawnEnemy(enemySpawn.type(), enemySpawn.position().x, enemySpawn.position().y, cellWidth, cellHeight);
        }
    }

    /**
     * Spawns a single enemy of the specified type at the given position based on the cell dimensions.
     *
     * @param enemyType   The class representing the type of the enemy.
     * @param x           The x-coordinate of the enemy's position.
     * @param y           The y-coordinate of the enemy's position.
     * @param cellWidth   The width of a single cell.
     * @param cellHeight  The height of a single cell.
     */
    public void spawnEnemy(Class<? extends Enemy> enemyType, int x, int y, int cellWidth, int cellHeight) {
        Enemy enemy;
        if (enemyType == Guard.class) {
            enemy = new Guard(x * cellWidth, y * cellHeight, cellWidth, cellHeight, 2, this.level);
        } else if (enemyType == Yeti.class) {
            enemy = new Yeti(x * cellWidth, y * cellHeight, cellWidth, cellHeight, 4, this.level);
        } else if (enemyType == Ghost.class) {
            enemy = new Ghost(x * cellWidth, y * cellHeight, cellWidth, cellHeight, 3, this.level);
        } else if (enemyType == ThermalTitan.class) {
            enemy = new ThermalTitan(x * cellWidth, y * cellHeight, cellWidth, cellHeight, 3, this.level, 1);
        } else {
            // add new enemies
            throw new RuntimeException("Unknown enemy type");
        }
        this.enemies.add(enemy);
    }

    /**
     * Checks if the player has collided with any of the enemies.
     *
     * @param player The player object.
     * @return {@code true} if there is a collision with any enemy, {@code false} otherwise.
     */
    public boolean checkPlayerCollision(Player player) {
        for (Enemy enemy : this.enemies) {
            if (enemy.hasCollidedWithPlayer(player)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if there is an enemy at the specified position.
     *
     * @param x The x-coordinate of the position.
     * @param y The y-coordinate of the position.
     * @return {@code true} if there is an enemy at the position, {@code false} otherwise.
     */
    public boolean isEnemyAt(int x, int y) {
        for (Enemy enemy : this.enemies) {
            if (enemy.getX() == x && enemy.getY() == y) {
                return true;
            }
        }
        return false;
    }

    /**
     * Renders all the enemies.
     *
     * @param g The Graphics object to render with.
     */
    public void render(Graphics g) {
        for (Enemy enemy : this.enemies) {
            enemy.render(g);
        }
    }

    /**
     * Updates the state of all the enemies.
     */
    public void update() {
        for (Enemy enemy : this.enemies) {
            enemy.update();
        }
    }

    /**
     * Returns the list of enemies managed by the EnemyManager.
     *
     * @return The list of enemies.
     */
    public List<Enemy> getEnemies() {
        return this.enemies;
    }
}