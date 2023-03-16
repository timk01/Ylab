package secondweek.statsaccumulator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatsAccumulatorImplTest {

    private StatsAccumulator statsAccumulator;

    @BeforeEach
    void setUp() {
        statsAccumulator = new StatsAccumulatorImpl();
    }

    @Test
    void add_NoneElementsInAccumulator_Success() {
        assertEquals(0, statsAccumulator.getMin());
        assertEquals(0, statsAccumulator.getMax());
        assertEquals(0, statsAccumulator.getCount());
        assertEquals(0.0, statsAccumulator.getAvg());
    }

    @Test
    void add_ZeroAsFirstElement_Success() {
        statsAccumulator.add(0);
        assertEquals(0, statsAccumulator.getMin());
        assertEquals(0, statsAccumulator.getMax());
        assertEquals(1, statsAccumulator.getCount());
        assertEquals(0.0, statsAccumulator.getAvg());
    }

    @Test
    void add_OneElement_Success() {
        statsAccumulator.add(100);
        assertEquals(100, statsAccumulator.getMin());
        assertEquals(100, statsAccumulator.getMax());
        assertEquals(1, statsAccumulator.getCount());
        assertEquals(100.0, statsAccumulator.getAvg());
    }

    @Test
    void add_SeveralElements_Success() {
        statsAccumulator.add(0);
        statsAccumulator.add(1);
        statsAccumulator.add(2);
        statsAccumulator.add(3);
        statsAccumulator.add(4);
        statsAccumulator.add(-4);
        assertEquals(-4, statsAccumulator.getMin());
        assertEquals(4, statsAccumulator.getMax());
        assertEquals(6, statsAccumulator.getCount());
        assertEquals(1.0, statsAccumulator.getAvg());
    }
}