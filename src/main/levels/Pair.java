package main.levels;

/**
 * Represents a generic key-value pair.
 *
 * @param <K> the type of the key
 * @param <V> the type of the value
 */
public class Pair<K, V> {
    private final K key;
    private final V value;

    /**
     * Constructs a new Pair with the specified key and value.
     *
     * @param key   the key of the pair
     * @param value the value of the pair
     */
    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Returns the key of the pair.
     *
     * @return the key
     */
    public K getKey() {
        return this.key;
    }

    /**
     * Returns the value of the pair.
     *
     * @return the value
     */
    public V getValue() {
        return this.value;
    }
}
