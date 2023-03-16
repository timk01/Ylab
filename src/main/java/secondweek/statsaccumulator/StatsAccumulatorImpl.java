package secondweek.statsaccumulator;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class StatsAccumulatorImpl implements StatsAccumulator {
    private static final int ROUNDING_PRECISION = 15;

    private int count;
    private int min;
    private int max;
    private double averageOfNumbers;

    public StatsAccumulatorImpl() {
        this.count = 0;
        this.min = 0;
        this.max = 0;
        this.averageOfNumbers = 0.0;
    }

    @Override
    public void add(int value) {
        BigDecimal sumBeforeAddingNewNumber = BigDecimal.valueOf(this.averageOfNumbers * this.count);
        this.count = Math.addExact(this.count, 1);
        this.min = this.count == 1 ? value : Integer.min(value, this.min);
        this.max = this.count == 1 ? value : Integer.max(value, this.max);
        BigDecimal sumAfterAddingNewNumber = sumBeforeAddingNewNumber.add(BigDecimal.valueOf(value));
        BigDecimal dividend = new BigDecimal(this.count);
        this.averageOfNumbers = sumAfterAddingNewNumber
                .divide(dividend, ROUNDING_PRECISION, RoundingMode.CEILING).doubleValue();
    }

    @Override
    public int getMin() {
        return this.min;
    }

    @Override
    public int getMax() {
        return this.max;
    }

    @Override
    public int getCount() {
        return this.count;
    }

    @Override
    public Double getAvg() {
        return this.averageOfNumbers;
    }
}
