package secondweek.statsaccumulator;

public class StatsAccumulatorImpl implements StatsAccumulator {
    private static final int ZERO = 0;
    private static final int ONE = 1;

    private int sumOfNumbers;
    private int count;
    private int min;
    private int max;

    @Override
    public void add(int value) {
        this.sumOfNumbers += value;
        this.count++;
        this.min = this.count == ONE ? this.sumOfNumbers : Integer.min(value, this.min);
        this.max = this.count == ONE ? this.sumOfNumbers : Integer.max(value, this.max);
    }

    @Override
    public int getMin() {
        return min;
    }

    @Override
    public int getMax() {
        return max;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Double getAvg() {
        if (count == ZERO) {
            return (double) sumOfNumbers;
        }
        return this.count == ONE ? this.sumOfNumbers : (double) this.sumOfNumbers / this.count;
    }
}
