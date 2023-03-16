package secondweek.sequencegenerator;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SequenceGeneratorImplTest {

    private final SequenceGenerator sequenceGenerator = new SequenceGeneratorImpl();
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    private final String aStringFormatForFiveNumbersSequence = "%d%n%d%n%d%n%d%n%d%n";
    private final String aStringFormatForSixNumbersSequence = aStringFormatForFiveNumbersSequence + "%d%n";
    private final String aStringFormatForSevenNumbersSequence = aStringFormatForSixNumbersSequence + "%d%n";

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    void a_Sequence_Success() {
        String expectedStringValue = String.format(aStringFormatForFiveNumbersSequence, 2, 4, 6, 8, 10);
        sequenceGenerator.a(5);
        assertEquals(expectedStringValue, outputStreamCaptor.toString());
    }

    @Test
    void b_Sequence_Success() {
        String expectedStringValue = String.format(aStringFormatForFiveNumbersSequence, 1, 3, 5, 7, 9);
        sequenceGenerator.b(5);
        assertEquals(expectedStringValue, outputStreamCaptor.toString());
    }

    @Test
    void c_Sequence_Success() {
        String expectedStringValue = String.format(aStringFormatForFiveNumbersSequence, 1, 4, 9, 16, 25);
        sequenceGenerator.c(5);
        assertEquals(expectedStringValue, outputStreamCaptor.toString());
    }

    @Test
    void d_Sequence_Success() {
        String expectedStringValue = String.format(aStringFormatForFiveNumbersSequence, 1, 8, 27, 64, 125);
        sequenceGenerator.d(5);
        assertEquals(expectedStringValue, outputStreamCaptor.toString());
    }

    @Test
    void e_Sequence_Success() {
        String expectedStringValue = String.format(aStringFormatForSixNumbersSequence, 1, -1, 1, -1, 1, -1);
        sequenceGenerator.e(6);
        assertEquals(expectedStringValue, outputStreamCaptor.toString());
    }

    @Test
    void f_Sequence_Success() {
        String expectedStringValue = String.format(aStringFormatForFiveNumbersSequence, 1, -2, 3, -4, 5);
        sequenceGenerator.f(5);
        assertEquals(expectedStringValue, outputStreamCaptor.toString());
    }

    @Test
    void g_Sequence_Success() {
        String expectedStringValue = String.format(aStringFormatForFiveNumbersSequence, 1, -4, 9, -16, 25);
        sequenceGenerator.g(5);
        assertEquals(expectedStringValue, outputStreamCaptor.toString());
    }

    @Test
    void h_Sequence_Success() {
        String expectedStringValue = String.format(aStringFormatForSixNumbersSequence, 1, 0, 2, 0, 3, 0);
        sequenceGenerator.h(6);
        assertEquals(expectedStringValue, outputStreamCaptor.toString());
    }

    @Test
    void i_Sequence_Success() {
        String expectedStringValue = String.format(aStringFormatForSixNumbersSequence, 1, 2, 6, 24, 120, 720);
        sequenceGenerator.i(6);
        assertEquals(expectedStringValue, outputStreamCaptor.toString());
    }

    @Test
    void j_Sequence_Success() {
        String expectedStringValue = String.format(aStringFormatForSevenNumbersSequence, 1, 1, 2, 3, 5, 8, 13);
        sequenceGenerator.j(7);
        assertEquals(expectedStringValue, outputStreamCaptor.toString());
    }
}