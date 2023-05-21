package main.levels.config;

import main.entities.other.EnemySpawn;
import main.entities.fruits.Fruit;
import main.levels.Pair;

import java.awt.Point;
import java.util.Arrays;
import java.util.List;

/**
 * The LevelConfiguration class represents the configuration of a level, including the layout, enemies, fruits, and player starting position.
 */
public class LevelConfiguration {
    private final int[][] layout;
    private final List<EnemySpawn> enemies;
    private final List<Pair<Class<? extends Fruit>, Integer>> fruits;
    private final Point playerStartingPosition;

    /**
     * Constructs a new LevelConfiguration object with the specified layout, enemies, fruits, and player starting position.
     *
     * @param layout               The layout of the level represented by a 2D array of integers.
     * @param enemies              The list of enemy spawns in the level.
     * @param fruits               The list of fruit types and their quantities in the level.
     * @param playerStartingPosition The starting position of the player in the level.
     */
    public LevelConfiguration(int[][] layout, List<EnemySpawn> enemies, List<Pair<Class<? extends Fruit>, Integer>> fruits, Point playerStartingPosition) {
        this.layout = deepCopy(layout);
        this.enemies = enemies;
        this.fruits = fruits;
        this.playerStartingPosition = playerStartingPosition;
    }

    /**
     * Returns the player's starting position in the level.
     *
     * @return The player's starting position.
     */
    public Point getPlayerStartingPosition() {
        return this.playerStartingPosition;
    }

    /**
     * Creates a deep copy of a 2D integer array.
     *
     * @param original The original 2D integer array.
     * @return The deep copy of the original array.
     */
    private static int[][] deepCopy(int[][] original) {
        if (original == null) {
            return null;
        }

        final int[][] result = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            result[i] = Arrays.copyOf(original[i], original[i].length);
        }
        return result;
    }

    /**
     * Returns the layout of the level.
     *
     * @return The layout of the level.
     */
    public int[][] getLayout() {
        return this.layout;
    }

    /**
     * Returns the list of enemy spawns in the level.
     *
     * @return The list of enemy spawns.
     */
    public List<EnemySpawn> getEnemies() {
        return this.enemies;
    }

    /**
     * Returns the list of fruit types and their quantities in the level.
     *
     * @return The list of fruit types and their quantities.
     */
    public List<Pair<Class<? extends Fruit>, Integer>> getFruits() {
        return this.fruits;
    }
}
