package secondweek.statsaccumulator;

public class StatsAccumulatorTest {
    public static void main(String[] args) {
        StatsAccumulator statsAccumulator = new StatsAccumulatorImpl();

        System.out.println(statsAccumulator.getAvg());
        System.out.println(statsAccumulator.getMin());
        System.out.println(statsAccumulator.getMax());
        System.out.println(statsAccumulator.getCount());

        statsAccumulator.add(1);
        System.out.println(statsAccumulator.getAvg());
        System.out.println(statsAccumulator.getMin());
        System.out.println(statsAccumulator.getMax());
        System.out.println(statsAccumulator.getCount());

        statsAccumulator.add(2);
        System.out.println(statsAccumulator.getAvg());
        statsAccumulator.add(0);
        System.out.println(statsAccumulator.getMin());
        statsAccumulator.add(3);
        statsAccumulator.add(8);
        System.out.println(statsAccumulator.getMax());
        System.out.println(statsAccumulator.getCount());

        System.out.println(statsAccumulator.getAvg());
    }
}
