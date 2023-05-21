package main.levels.managers;

import main.entities.other.Player;
import main.entities.fruits.Fruit;
import main.entities.fruits.Banana;
import main.entities.fruits.Lemon;
import main.entities.fruits.Grapes;
import main.entities.fruits.Orange;
import main.entities.fruits.Watermelon;
import main.levels.Level;
import main.levels.Pair;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;
import java.util.List;
/**
 * The FruitManager class manages the fruits in a level.
 */
public class FruitManager {
    private final Level level;
    private final List<Fruit> fruits;
    private final List<Pair<Class<? extends Fruit>, Integer>> fruits2;
    private Pair<Class<? extends Fruit>, Integer> currentFruitPair;
    private int numRemainingFruits;

    /**
     * Constructs a new FruitManager object with the specified level and initial fruit configurations.
     *
     * @param level  The level object.
     * @param fruits The initial fruit configurations.
     */
    public FruitManager(Level level, List<Pair<Class<? extends Fruit>, Integer>> fruits) {
        this.level = level;
        this.fruits2 = fruits;
        this.fruits = new ArrayList<>();
        this.moveToNextFruitType();
        this.spawnFruitsOfType();
    }

    /**
     * Spawns the remaining fruits of the current type at random locations in the level.
     */
    public void spawnFruitsOfType() {
        while (this.numRemainingFruits > 0) {
            this.spawnFruitAtRandomLocation(this.currentFruitPair.getKey());
            this.numRemainingFruits--;
        }
    }

    /**
     * Spawns a fruit at a random location in the level.
     */
    public void spawnFruit() {
        if (!this.fruits.isEmpty()) {
            return;
        }
        if (this.numRemainingFruits > 0) {
            this.spawnFruitAtRandomLocation(this.currentFruitPair.getKey());
            this.numRemainingFruits--;
        } else if (!this.fruits2.isEmpty()) {
            this.moveToNextFruitType();
            this.spawnFruitsOfType();
        }
    }

    /**
     * Moves to the next fruit type and updates the number of remaining fruits.
     */
    private void moveToNextFruitType() {
        if (!this.fruits2.isEmpty()) {
            this.currentFruitPair = this.fruits2.remove(0);
            this.numRemainingFruits = this.currentFruitPair.getValue();
        } else {
            this.currentFruitPair = null;
            this.numRemainingFruits = 0;
        }
    }

    /**
     * Checks if the player has collected any fruits and updates the player's points accordingly.
     *
     * @param player The player object.
     */
    public void checkFruitCollection(Player player) {
        List<Fruit> collectedFruits = new ArrayList<>();

        for (Fruit fruit : this.fruits) {
            if (fruit.getBounds().intersects(player.getBounds())) {
                collectedFruits.add(fruit);
                player.addPoints(fruit.getPointValue());
            }
        }

        this.fruits.removeAll(collectedFruits);

        if (this.fruits.isEmpty() && !this.fruits2.isEmpty()) {
            this.spawnFruit();
        }
    }

    /**
     * Spawns a fruit of the specified type at a random location in the level.
     *
     * @param fruitType The class representing the type of the fruit.
     */
    private void spawnFruitAtRandomLocation(Class<? extends Fruit> fruitType) {
        Random rand = new Random();
        int x;
        int y;

        Rectangle playerBounds = null;
        Player player = this.level.getPlayer();
        if (player != null) {
            playerBounds = player.getBounds();
        }

        Rectangle fruitBounds;

        do {
            x = rand.nextInt(this.level.getLevelLayout().layout()[0].length);
            y = rand.nextInt(this.level.getLevelLayout().layout().length);

            fruitBounds = new Rectangle(x * this.level.getCellWidth(), y * this.level.getCellHeight(),
                    this.level.getCellWidth(), this.level.getCellHeight());

        } while (this.level.getLevelLayout().layout()[y][x] != Level.EMPTY || (playerBounds != null && playerBounds.intersects(fruitBounds)));

        Fruit fruit;
        if (fruitType == Grapes.class) {
            fruit = new Grapes(x * this.level.getCellWidth(), y * this.level.getCellHeight(),
                    this.level.getCellWidth(), this.level.getCellHeight(), this.level.getSpriteSheet());
        } else if (fruitType == Banana.class) {
            fruit = new Banana(x * this.level.getCellWidth(), y * this.level.getCellHeight(),
                    this.level.getCellWidth(), this.level.getCellHeight(), this.level.getSpriteSheet());
        } else if (fruitType == Lemon.class) {
            fruit = new Lemon(x * this.level.getCellWidth(), y * this.level.getCellHeight(),
                    this.level.getCellWidth(), this.level.getCellHeight(), this.level.getSpriteSheet());
        } else if (fruitType == Orange.class) {
            fruit = new Orange(x * this.level.getCellWidth(), y * this.level.getCellHeight(),
                    this.level.getCellWidth(), this.level.getCellHeight(), this.level.getSpriteSheet());
        } else if (fruitType == Watermelon.class) {
            fruit = new Watermelon(x * this.level.getCellWidth(), y * this.level.getCellHeight(),
                    this.level.getCellWidth(), this.level.getCellHeight(), this.level.getSpriteSheet());
        } else {
            throw new RuntimeException("Unknown fruit type");
        }

        this.fruits.add(fruit);
    }

    /**
     * Checks if all fruits have been collected.
     *
     * @return True if all fruits have been collected, false otherwise.
     */
    public boolean allFruitsCollected() {
        return this.fruits.isEmpty();
    }

    /**
     * Renders all the fruits.
     *
     * @param g The Graphics object to render with.
     */
    public void render(Graphics g) {
        for (Fruit fruit : this.fruits) {
            fruit.render(g);
        }
    }

    /**
     * Updates the state of all the fruits.
     */
    public void update() {
        for (Fruit fruit : this.fruits) {
            fruit.update();
        }
    }
}