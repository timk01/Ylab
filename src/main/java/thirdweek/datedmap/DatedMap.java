package thirdweek.datedmap;

import java.util.Date;
import java.util.Set;

/**
 * Represents DatedMap.
 * This is rather standard implementation similar to HasHMap, with exception of getKeyLastInsertionDate method
 *
 * @author Ylab
 * @version 1.0
 */
public interface DatedMap {

    /**
     * Puts key-pair value in map
     *
     * @param key of String type
     * @param value of String type
     */
    void put(String key, String value);

    /**
     * Gets value by it's key
     *
     * @param key of String type
     * @return value of String type
     */
    String get(String key);

    /**
     * Checks if map contains the particular key
     *
     * @param key of String type
     * @return boolean value
     */
    boolean containsKey(String key);

    /**
     * Removes the mapping for the specified key from this map if present
     *
     * @param key of String type
     */
    void remove(String key);

    /**
     * Returns a {@link Set} view of the keys contained in this map
     *
     * @return a set view of the keys contained in this map
     */
    Set<String> keySet();

    /**
     * Gets date associated with the last added value (by key)
     *
     * @return Date
     */
    Date getKeyLastInsertionDate(String key);
}