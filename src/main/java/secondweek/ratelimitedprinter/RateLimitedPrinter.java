package secondweek.ratelimitedprinter;

/**
 * Represents a RateLimitedPrinter.
 *
 * @author Timur Khasmamedov
 * @version 1.0
 */
public class RateLimitedPrinter {
    private final int interval;
    private long lastTimeConsolePrint;
    private long currentTime;

    /**
     * Constructor for class RateLimitedPrinter
     * uses interval and if it's less than zero, it's zero; otherwise saves interval
     * also set the lastTimeConsolePrint variable to zero
     *
     * @param interval of type int
     */
    public RateLimitedPrinter(int interval) {
        this.interval = Math.max(interval, 0);
        this.lastTimeConsolePrint = 0;
    }

    /**
     * Prints the given message only if interval is passed
     *
     * @param message of type String to print
     */
    public void print(String message) {
        currentTime = System.currentTimeMillis();
        if (currentTime - lastTimeConsolePrint >= interval) {
            System.out.println(message);
            lastTimeConsolePrint = currentTime;
        }
    }
}
