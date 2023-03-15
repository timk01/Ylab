package secondweek.ratelimitedprinter;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class RateLimitedPrinterTest {

    private final RateLimitedPrinter rateLimitedPrinter = new RateLimitedPrinter(1000);
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
    void printSuccess() {
        long start = System.currentTimeMillis();
        String expectedFifthValue = "123\r\n";
        for (int i = 0; i < 100000000L; i++) {
            rateLimitedPrinter.print("123");
        }
        long end = System.currentTimeMillis();
        assertTrue(end - start < rateLimitedPrinter.getInterval());
        assertEquals(expectedFifthValue, outputStreamCaptor.toString());
    }

    @Test
    void printFail() {
        long start = System.currentTimeMillis();
        String expectedFifthValue = "123\r\n";
        for (int i = 0; i < 1000000000L; i++) {
            rateLimitedPrinter.print("123");
        }
        long end = System.currentTimeMillis();
        assertFalse(end - start < rateLimitedPrinter.getInterval());
        assertNotEquals(expectedFifthValue, outputStreamCaptor.toString());
    }
}