package thirdweek.datedmap;

import thirdweek.trasnliterator.Transliterator;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Represents a DatedMap interface implementation.
 *
 * @author Khasmamedov T.
 * @version 1.2
 */
public class DatedMapImpl implements DatedMap {

    /**
     * Hashmap structure, for convenience use:
     * Key = String, Value = InnerMapClass (compare with <String, String> pair) - see also internal class description
     * used in class to provide additional information
     *
     */
    private final Map<String, InnerMapClass> aCurrentMap = new HashMap<>();

    /**
     * Internal class that encapsulates value of original map.
     * Contains 2 fields:
     * value of type String (ex-String) and Date of actual insertion
     *
     */
    private class InnerMapClass {
        private final String innerMapClassValue;
        private final Date innerMapClassInsertionTime;

        public InnerMapClass(String innerMapClassValue, Date innerMapClassInsertionTime) {
            this.innerMapClassValue = innerMapClassValue;
            this.innerMapClassInsertionTime = innerMapClassInsertionTime;
        }

        @Override
        public String toString() {
            return "Value: " +
                    "Value = '" + innerMapClassValue + '\'' +
                    ", timeOfInsertion = " + innerMapClassInsertionTime +
                    '}';
        }
    }

    @Override
    public void put(String key, String value) {
        aCurrentMap.put(key, new InnerMapClass(value, new Date()));
    }

    @Override
    public String get(String key) {
        return aCurrentMap.get(key) == null ? null : aCurrentMap.get(key).innerMapClassValue;
    }

    @Override
    public boolean containsKey(String key) {
        return aCurrentMap.containsKey(key);
    }

    @Override
    public void remove(String key) {
        aCurrentMap.remove(key);
    }

    @Override
    public Set<String> keySet() {
        return aCurrentMap.keySet();
    }

    /**
     * Gets date associated with the last added value (by key)
     *
     * @return Date
     * @implNote unlike his get(String key) analogue, returns the last insertion time
     */
    @Override
    public Date getKeyLastInsertionDate(String key) {
        return aCurrentMap.get(key) == null ? null : aCurrentMap.get(key).innerMapClassInsertionTime;
    }

    /**
     * Helper method to print DatedMap in more convenient way
     */
    public void printDatedMap() {
        aCurrentMap.entrySet().stream()
                .map(entry -> String.format("%s, %s", entry.getKey(), entry.getValue()))
                .forEach(System.out::println);
    }
}
