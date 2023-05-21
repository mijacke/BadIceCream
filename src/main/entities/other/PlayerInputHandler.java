package main.entities.other;

import main.input.KeyInput;

/**
 * Represents the input handler for the player.
 */
public record PlayerInputHandler(KeyInput keyInput) {

    /**
     * Checks if the move up key is pressed.
     *
     * @return true if the move up key is pressed, false otherwise.
     */
    public boolean isMoveUpPressed() {
        return this.keyInput.isKeyDown('W');
    }
    /**
     * Checks if the move down key is pressed.
     *
     * @return true if the move down key is pressed, false otherwise.
     */
    public boolean isMoveDownPressed() {
        return this.keyInput.isKeyDown('S');
    }
    /**
     * Checks if the move left key is pressed.
     *
     * @return true if the move left key is pressed, false otherwise.
     */
    public boolean isMoveLeftPressed() {
        return this.keyInput.isKeyDown('A');
    }
    /**
     * Checks if the move right key is pressed.
     *
     * @return true if the move right key is pressed, false otherwise.
     */
    public boolean isMoveRightPressed() {
        return this.keyInput.isKeyDown('D');
    }
    /**
     * Retrieves the facing direction based on the pressed keys.
     *
     * @return the facing direction.
     */
    public Direction getFacingDirection() {
        if (this.isMoveUpPressed()) {
            return Direction.UP;
        } else if (this.isMoveDownPressed()) {
            return Direction.DOWN;
        } else if (this.isMoveLeftPressed()) {
            return Direction.LEFT;
        } else if (this.isMoveRightPressed()) {
            return Direction.RIGHT;
        } else {
            return Direction.NONE;
        }
    }
}