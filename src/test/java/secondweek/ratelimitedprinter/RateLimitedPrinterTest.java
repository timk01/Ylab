package secondweek.ratelimitedprinter;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class RateLimitedPrinterTest {

    private RateLimitedPrinter rateLimitedPrinter;
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    void print_Success() {
        rateLimitedPrinter = new RateLimitedPrinter(1000);
        String expectedValue = String.format("%d%n", 123);
        for (int i = 0; i < 100; i++) {
            rateLimitedPrinter.print("123");
        }
        assertEquals(expectedValue, outputStreamCaptor.toString());
    }

    @Test
    void print_Fail() throws InterruptedException {
        rateLimitedPrinter = new RateLimitedPrinter(1000);
        String expectedValue = String.format("%d%n", 123);
        for (int i = 0; i < 100; i++) {
            rateLimitedPrinter.print("123");
        }
        Thread.sleep(1000);
        for (int i = 0; i < 100; i++) {
            rateLimitedPrinter.print("123");
        }
        assertNotEquals(expectedValue, outputStreamCaptor.toString());
    }
}