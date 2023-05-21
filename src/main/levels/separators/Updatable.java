package main.levels.separators;

/**
 * The Updatable interface represents an object that can be updated.
 * Classes that implement this interface should provide an implementation for the update method
 * which specifies how the object should be updated.
 */
public interface Updatable {
    /**
     * Updates the object.
     * This method should be called to update the state or behavior of the object.
     */
    void update();
}