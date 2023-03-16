package secondweek.complexnumbers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ComplexNumberTest {

    private final ComplexNumber complexNumberFirst = new ComplexNumber(15.2, 3.3);
    private final ComplexNumber complexNumberSecond = new ComplexNumber(14.8, 6.7);
    private final ComplexNumber complexNumberThird = new ComplexNumber(0.4, -3.4);
    private final ComplexNumber complexNumberFourth = new ComplexNumber(1.0, 7.0);
    private final ComplexNumber complexNumberFifth = new ComplexNumber(15.3, 3.2);

    @Test
    void addition_Success() {
        ComplexNumber addedComplexNumber = complexNumberFirst.addition(complexNumberSecond);
        ComplexNumber expectedComplexNumber = new ComplexNumber(30.0, 10.0);
        assertEquals(0, Double.compare(expectedComplexNumber.getComplexRealPart(),
                addedComplexNumber.getComplexRealPart()));
    }

    @Test
    void addition_Fail() {
        ComplexNumber addedComplexNumber = complexNumberFirst.addition(complexNumberSecond);
        ComplexNumber expectedComplexNumber = new ComplexNumber(30.00001, 10.00001);
        assertNotEquals(0, Double.compare(expectedComplexNumber.getComplexRealPart(),
                addedComplexNumber.getComplexRealPart()));
    }

    @Test
    void subtraction_Success() {
        ComplexNumber subtractedComplexNumber = complexNumberFirst.subtraction(complexNumberThird);
        ComplexNumber expectedComplexNumber = new ComplexNumber(14.8, -0.1);
        assertEquals(0, Double.compare(expectedComplexNumber.getComplexRealPart(),
                subtractedComplexNumber.getComplexRealPart()));
    }

    @Test
    void subtraction_Fail() {
        ComplexNumber subtractedComplexNumber = complexNumberFirst.subtraction(complexNumberThird);
        ComplexNumber expectedComplexNumber = new ComplexNumber(0.00001, 0.00001);
        assertNotEquals(0, Double.compare(expectedComplexNumber.getComplexRealPart(),
                subtractedComplexNumber.getComplexRealPart()));
    }

    @Test
    void multiplication_Success() {
        ComplexNumber aComplexNumberForMultiplication = new ComplexNumber(3.0, 2.0);
        ComplexNumber multiplicatedComplexNumber = aComplexNumberForMultiplication.multiplication(complexNumberFourth);
        ComplexNumber expectedComplexNumber = new ComplexNumber(-11, 23);
        assertEquals(0, Double.compare(expectedComplexNumber.getComplexRealPart(),
                multiplicatedComplexNumber.getComplexRealPart()));
    }

    @Test
    void multiplication_Fail() {
        ComplexNumber multiplicatedComplexNumber = complexNumberFirst.multiplication(complexNumberFourth);
        ComplexNumber expectedComplexNumber = new ComplexNumber(-11.2, 23.2);
        assertNotEquals(0, Double.compare(expectedComplexNumber.getComplexRealPart(), multiplicatedComplexNumber.getComplexRealPart()));
    }

    @Test
    void modulus_Success() {
        assertEquals(15.6310588253004, complexNumberFifth.modulus());
    }

    @Test
    void modulus_Fail() {
        assertNotEquals(15.63, complexNumberFifth.modulus());
    }

    @Test
    void toString_Success() {
        assertEquals("15.2+3.3i", complexNumberFirst.toString());
        assertEquals("0.4-3.4i", complexNumberThird.toString());
    }
}