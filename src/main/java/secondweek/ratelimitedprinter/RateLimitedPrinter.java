package secondweek.ratelimitedprinter;

/**
 * Represents a RateLimitedPrinter.
 *
 * @author Timur Khasmamedov
 * @version 1.0
 */
public class RateLimitedPrinter {
    public static final int ZERO = 0;

    private final int interval;
    private long lastTimeConsolePrint;

    /**
     * Constructor for class RateLimitedPrinter
     * uses interval and if it's less than zero, it's zero; otherwise saves interval
     * also set the lastTimeConsolePrint variable to zero
     *
     * @param interval of type int
     */
    public RateLimitedPrinter(int interval) {
        this.interval = Math.max(interval, ZERO);
        this.lastTimeConsolePrint = ZERO;
    }

    public int getInterval() {
        return interval;
    }

    /**
     * Prints the given message only if interval is passed
     *
     * @param message of type String to print
     */
    public void print(String message) {
        if (System.currentTimeMillis() - lastTimeConsolePrint >= interval) {
            System.out.println(message);
            lastTimeConsolePrint = System.currentTimeMillis();
        }
    }
}
