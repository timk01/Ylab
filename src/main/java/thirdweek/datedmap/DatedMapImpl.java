package thirdweek.datedmap;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DatedMapImpl implements DatedMap {
    private final Map<String, InnerMapClass> aCurrentMap = new HashMap<>();

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

    @Override
    public Date getKeyLastInsertionDate(String key) {
        return aCurrentMap.get(key) == null ? null : aCurrentMap.get(key).innerMapClassInsertionTime;
    }

    public void printDatedMap() {
        aCurrentMap.entrySet().stream()
                .map(entry -> String.format("%s, %s", entry.getKey(), entry.getValue()))
                .forEach(System.out::println);
    }
}
