package main.entities.other;

import main.entities.enemies.Enemy;

import java.awt.Point;

/**
 * Class representing the spawn location and type of enemy.
 */
public record EnemySpawn(Class<? extends Enemy> type, Point position) {
}