package secondweek.statsaccumulator;

/**
 * Represents a StatsAccumulator.
 *
 * @author Ylab
 * @version 1.0
 */
public interface StatsAccumulator {

    /**
     * Adds a number to accumulator. To be invoked several times later
     *
     * @param value number
     */
    void add(int value);

    /**
     * Returns min of all added numbers
     *
     * @return min of all added numbers
     */
    int getMin();

    /**
     * Returns max of all added numbers
     *
     * @return max of all added numbers
     */
    int getMax();

    /**
     * Returns quantity of all added numbers
     *
     * @return количество добавленных чисел
     */
    int getCount();

    /**
     * Returns arithmetical mean of all added numbers
     *
     * @return arithmetical mean of all added numbers
     */
    Double getAvg();
}