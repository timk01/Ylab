package thirdweek.datedmap;

public class DatedMapTest {
    private static String TEST_KEY = "key";
    private static String TEST_VALUE = "value";

    public static void main(String[] args) throws InterruptedException {
        DatedMapImpl datedMap = new DatedMapImpl();

        System.out.println(datedMap.get(TEST_KEY));
        System.out.println(datedMap.getKeyLastInsertionDate(TEST_KEY));
        System.out.println(datedMap.containsKey(TEST_KEY));
        datedMap.remove(TEST_KEY);
        System.out.println(datedMap.containsKey(TEST_KEY));
        System.out.println(datedMap.keySet());

        System.out.println();

        System.out.println(datedMap.get(null));
        System.out.println(datedMap.getKeyLastInsertionDate(null));
        System.out.println(datedMap.containsKey(null));
        datedMap.remove(null);
        System.out.println(datedMap.containsKey(null));
        System.out.println(datedMap.keySet());
        datedMap.put(null, null);
        System.out.println(datedMap.get(null));
        System.out.println(datedMap.getKeyLastInsertionDate(null));

        System.out.println();

        datedMap.put(TEST_KEY, TEST_VALUE);
        System.out.println(datedMap.getKeyLastInsertionDate(TEST_KEY));
        System.out.println(datedMap.keySet());
        System.out.println(datedMap.get(TEST_KEY));
        System.out.println(datedMap.containsKey(TEST_KEY));
        datedMap.remove(TEST_KEY);
        System.out.println(datedMap.containsKey(TEST_KEY));
        System.out.println(datedMap.getKeyLastInsertionDate(TEST_KEY));
        System.out.println(datedMap.keySet());

        System.out.println();

        datedMap.put(TEST_KEY, TEST_VALUE);
        datedMap.put("key1", TEST_VALUE);
        datedMap.put("key2", TEST_VALUE);
        System.out.println(datedMap.getKeyLastInsertionDate(TEST_KEY));
        datedMap.put("key3", "456");
        System.out.println(datedMap.keySet());

        Thread.sleep(1000);
        datedMap.put(TEST_KEY, TEST_VALUE + 1);
        System.out.println(datedMap.keySet());
        System.out.println(datedMap.get(TEST_KEY));
        System.out.println(datedMap.getKeyLastInsertionDate(TEST_KEY));

        System.out.println();

        datedMap.printDatedMap();
    }
}
