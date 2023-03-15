package secondweek.sequencegenerator;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SequenceGeneratorImplTest {

    private final SequenceGenerator sequenceGenerator = new SequenceGeneratorImpl();
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
    void aSuccess() {
        String expectedStringValue = "2\r\n4\r\n6\r\n8\r\n10\r\n";
        sequenceGenerator.a(5);
        assertEquals(expectedStringValue, outputStreamCaptor.toString());
    }

    @Test
    void bSuccess() {
        String expectedStringValue = "1\r\n3\r\n5\r\n7\r\n9\r\n";
        sequenceGenerator.b(5);
        assertEquals(expectedStringValue, outputStreamCaptor.toString());
    }

    @Test
    void cSuccess() {
        String expectedStringValue = "1\r\n4\r\n9\r\n16\r\n25\r\n";
        sequenceGenerator.c(5);
        assertEquals(expectedStringValue, outputStreamCaptor.toString());
    }

    @Test
    void dSuccess() {
        String expectedStringValue = "1\r\n8\r\n27\r\n64\r\n125\r\n";
        sequenceGenerator.d(5);
        assertEquals(expectedStringValue, outputStreamCaptor.toString());
    }

    @Test
    void eSuccess() {
        String expectedStringValue = "1\r\n-1\r\n1\r\n-1\r\n1\r\n-1\r\n";
        sequenceGenerator.e(6);
        assertEquals(expectedStringValue, outputStreamCaptor.toString());
    }

    @Test
    void fSuccess() {
        String expectedStringValue = "1\r\n-2\r\n3\r\n-4\r\n5\r\n";
        sequenceGenerator.f(5);
        assertEquals(expectedStringValue, outputStreamCaptor.toString());
    }

    @Test
    void gSuccess() {
        String expectedStringValue = "1\r\n-4\r\n9\r\n-16\r\n25\r\n";
        sequenceGenerator.g(5);
        assertEquals(expectedStringValue, outputStreamCaptor.toString());
    }

    @Test
    void hSuccess() {
        String expectedStringValue = "1\r\n0\r\n2\r\n0\r\n3\r\n0\r\n";
        sequenceGenerator.h(6);
        assertEquals(expectedStringValue, outputStreamCaptor.toString());
    }

    @Test
    void iSuccess() {
        String expectedStringValue = "1\r\n2\r\n6\r\n24\r\n120\r\n720\r\n";
        sequenceGenerator.i(6);
        assertEquals(expectedStringValue, outputStreamCaptor.toString());
    }

    @Test
    void jSuccess() {
        String expectedStringValue = "1\r\n1\r\n2\r\n3\r\n5\r\n8\r\n13\r\n";
        sequenceGenerator.j(7);
        assertEquals(expectedStringValue, outputStreamCaptor.toString());
    }
}