package main.levels.config;

import main.entities.other.EnemySpawn;
import main.entities.fruits.Fruit;
import main.entities.fruits.Watermelon;
import main.entities.fruits.Orange;
import main.entities.fruits.Grapes;
import main.entities.fruits.Lemon;
import main.entities.fruits.Banana;
import main.entities.enemies.Yeti;
import main.entities.enemies.Guard;
import main.entities.enemies.Ghost;
import main.entities.enemies.ThermalTitan;
import main.levels.Pair;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * The LevelConfigFactory class provides methods to create level configurations for different levels.
 */
public class LevelConfigFactory {
    /**
     * Creates a level configuration using the provided layout, enemy spawns, fruit pairs, and player starting position.
     *
     * @param layout                The layout of the level.
     * @param enemies               The list of enemy spawns.
     * @param fruits                The list of fruit pairs.
     * @param playerStartingPosition The starting position of the player.
     * @return The created level configuration.
     */
    public static LevelConfiguration createLevelConfig(int[][] layout, List<EnemySpawn> enemies, List<Pair<Class<? extends Fruit>, Integer>> fruits, Point playerStartingPosition) {
        return new LevelConfiguration(layout, enemies, fruits, playerStartingPosition);
    }

    /**
     * Creates a list of enemy spawns using the provided enemy spawn objects.
     *
     * @param enemySpawns The enemy spawn objects.
     * @return The created list of enemy spawns.
     */
    private static List<EnemySpawn> createEnemyList(EnemySpawn... enemySpawns) {
        return new ArrayList<>(Arrays.asList(enemySpawns));
    }

    /**
     * Creates a list of fruit pairs using the provided fruit pairs.
     *
     * @param fruitPairs The fruit pairs.
     * @return The created list of fruit pairs.
     */
    @SafeVarargs
    private static List<Pair<Class<? extends Fruit>, Integer>> createFruitList(Pair<Class<? extends Fruit>, Integer>... fruitPairs) {
        List<Pair<Class<? extends Fruit>, Integer>> fruits = new ArrayList<>();
        Collections.addAll(fruits, fruitPairs);
        return fruits;
    }

    /**
     * Creates a level configuration for level 1.
     *
     * @return The created level configuration for level 1.
     */
    public static LevelConfiguration createLevel1Config() {
        int[][] layout = DifferentLevels.LAYOUT_1;
        List<EnemySpawn> enemies = createEnemyList(
                new EnemySpawn(Yeti.class, new Point(16, 10)),
                new EnemySpawn(Yeti.class, new Point(6, 6))
        );
        List<Pair<Class<? extends Fruit>, Integer>> fruits = createFruitList(
                new Pair<>(Banana.class, 20),
                new Pair<>(Grapes.class, 16)
        );

        Point playerStartingPosition = new Point(9, 16);
        return createLevelConfig(layout, enemies, fruits, playerStartingPosition);
    }

    /**
     * Creates a level configuration for level 2.
     *
     * @return The created level configuration for level 2.
     */
    public static LevelConfiguration createLevel2Config() {
        int[][] layout = DifferentLevels.LAYOUT_2;
        List<EnemySpawn> enemies = createEnemyList(
                new EnemySpawn(Guard.class, new Point(2, 5)),
                new EnemySpawn(Yeti.class, new Point(17, 7)),
                new EnemySpawn(Yeti.class, new Point(3, 16))
        );
        List<Pair<Class<? extends Fruit>, Integer>> fruits = createFruitList(
                new Pair<>(Grapes.class, 7),
                new Pair<>(Watermelon.class, 10)
        );
        Point playerStartingPosition = new Point(8, 2);
        return createLevelConfig(layout, enemies, fruits, playerStartingPosition);
    }

    /**
     * Creates a level configuration for level 3.
     *
     * @return The created level configuration for level 3.
     */
    public static LevelConfiguration createLevel3Config() {
        int[][] layout = DifferentLevels.LAYOUT_3;
        List<EnemySpawn> enemies = createEnemyList(
                new EnemySpawn(Guard.class, new Point(3, 3)),
                new EnemySpawn(Guard.class, new Point(3, 16)),
                new EnemySpawn(Guard.class, new Point(16, 16)),
                new EnemySpawn(Guard.class, new Point(16, 3))
        );
        List<Pair<Class<? extends Fruit>, Integer>> fruits = createFruitList(
                new Pair<>(Banana.class, 26),
                new Pair<>(Orange.class, 24)
        );
        Point playerStartingPosition = new Point(5, 5);
        return createLevelConfig(layout, enemies, fruits, playerStartingPosition);
    }

    /**
     * Creates a level configuration for level 4.
     *
     * @return The created level configuration for level 4.
     */
    public static LevelConfiguration createLevel4Config() {
        int[][] layout = DifferentLevels.LAYOUT_4;
        List<EnemySpawn> enemies = createEnemyList(
                new EnemySpawn(Guard.class, new Point(4, 4)),
                new EnemySpawn(Ghost.class, new Point(6, 6)),
                new EnemySpawn(Guard.class, new Point(13, 8))
        );
        List<Pair<Class<? extends Fruit>, Integer>> fruits = createFruitList(
                new Pair<>(Lemon.class, 22),
                new Pair<>(Orange.class, 22)
        );
        Point playerStartingPosition = new Point(2, 2);
        return createLevelConfig(layout, enemies, fruits, playerStartingPosition);
    }

    /**
     * Creates a level configuration for level 5.
     *
     * @return The created level configuration for level 5.
     */
    public static LevelConfiguration createLevel5Config() {
        int[][] layout = DifferentLevels.LAYOUT_5;
        List<EnemySpawn> enemies = createEnemyList(
                new EnemySpawn(ThermalTitan.class, new Point(4, 4)),
                new EnemySpawn(Ghost.class, new Point(6, 6)),
                new EnemySpawn(Yeti.class, new Point(13, 8))
        );
        List<Pair<Class<? extends Fruit>, Integer>> fruits = createFruitList(
                new Pair<>(Banana.class, 16),
                new Pair<>(Grapes.class, 16),
                new Pair<>(Watermelon.class, 4)
        );
        Point playerStartingPosition = new Point(2, 2);
        return createLevelConfig(layout, enemies, fruits, playerStartingPosition);
    }

    // and so on for other levels...
}
