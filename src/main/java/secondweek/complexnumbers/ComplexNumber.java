package secondweek.complexnumbers;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * Represents a ComplexNumber.
 *
 * @author Timur Khasmamedov
 * @version 1.0
 */
public final class ComplexNumber {
    private static final int ROUNDING_PRECISION = 15;

    private final double complexRealPart;
    private final double complexImaginaryPart;

    /**
     * First constructor for class ComplexNumber
     * uses complexRealPart as main param (see below) and complexImaginaryPart of value 0
     *
     * @param complexRealPart of type double
     */
    public ComplexNumber(double complexRealPart) {
        this.complexRealPart = complexRealPart;
        this.complexImaginaryPart = 0;
    }

    /**
     * Second constructor for class ComplexNumber
     * needs two params: complexRealPart and complexImaginaryPart
     *
     * @param complexRealPart      of type double
     * @param complexImaginaryPart of type double
     */
    public ComplexNumber(double complexRealPart, double complexImaginaryPart) {
        this.complexRealPart = complexRealPart;
        this.complexImaginaryPart = complexImaginaryPart;
    }

    public double getComplexRealPart() {
        return complexRealPart;
    }

    public double getComplexImaginaryPart() {
        return complexImaginaryPart;
    }


    /**
     * Adds the current complexNumber to another one complexNumber.
     *
     * @param complexNumber number
     * @return new instance of ComplexNumber
     */
    public ComplexNumber addition(ComplexNumber complexNumber) {
        double complexPartAddition = (BigDecimal.valueOf(complexRealPart)
                .add(BigDecimal.valueOf(complexNumber.getComplexRealPart()))).doubleValue();
        double imaginaryPartAddition = (BigDecimal.valueOf(complexImaginaryPart)
                .add(BigDecimal.valueOf(complexNumber.getComplexImaginaryPart()))).doubleValue();
        return new ComplexNumber(complexPartAddition, imaginaryPartAddition);
    }

    /**
     * Subtracts the current complexNumber from another one complexNumber.
     *
     * @param complexNumber number
     * @return new instance of ComplexNumber
     */
    public ComplexNumber subtraction(ComplexNumber complexNumber) {
        double complexPartAddition = (BigDecimal.valueOf(complexRealPart)
                .subtract(BigDecimal.valueOf(complexNumber.getComplexRealPart()))).doubleValue();
        double imaginaryPartAddition = (BigDecimal.valueOf(complexImaginaryPart)
                .subtract(BigDecimal.valueOf(complexNumber.getComplexImaginaryPart()))).doubleValue();
        return new ComplexNumber(complexPartAddition, imaginaryPartAddition);
    }

    /**
     * Multiplies the current complexNumber on another one complexNumber.
     *
     * @param complexNumber number
     * @return new instance of ComplexNumber
     */
    public ComplexNumber multiplication(ComplexNumber complexNumber) {
        BigDecimal twoComplexPartsMultiplication = BigDecimal.valueOf(complexRealPart)
                .multiply(BigDecimal.valueOf(complexNumber.getComplexRealPart()));
        BigDecimal twoImaginaryPartsMultiplication = BigDecimal.valueOf(complexImaginaryPart)
                .multiply(BigDecimal.valueOf(complexNumber.getComplexImaginaryPart()));
        double complexRealPartForComplexNumber = (twoComplexPartsMultiplication
                .subtract(twoImaginaryPartsMultiplication))
                .doubleValue();


        BigDecimal complexImaginaryPartOnComplexRealPartMultiplication = BigDecimal.valueOf(complexImaginaryPart)
                .multiply(BigDecimal.valueOf(complexNumber.getComplexRealPart()));
        BigDecimal complexRealPartOnComplexImaginaryPartMultiplication = BigDecimal.valueOf(complexRealPart)
                .multiply(BigDecimal.valueOf(complexNumber.getComplexImaginaryPart()));
        double complexImaginaryPartForComplexNumber = complexImaginaryPartOnComplexRealPartMultiplication
                .add(complexRealPartOnComplexImaginaryPartMultiplication)
                .doubleValue();

        return new ComplexNumber(complexRealPartForComplexNumber, complexImaginaryPartForComplexNumber);
    }

    /**
     * Getting modulus of complexNumber.
     *
     * @return number of Double type
     */
    public Double modulus() {
        BigDecimal complexRealPartPow = BigDecimal.valueOf(complexRealPart).pow(2);
        BigDecimal complexImaginaryPartPow = BigDecimal.valueOf(complexImaginaryPart).pow(2);

        MathContext mc = new MathContext(ROUNDING_PRECISION, RoundingMode.HALF_UP);
        return (complexRealPartPow.add(complexImaginaryPartPow)).sqrt(mc).doubleValue();
    }

    @Override
    public String toString() {
        StringBuilder aStringToShow = new StringBuilder().append(complexRealPart);
        if (Double.compare(complexImaginaryPart, 0) >= 0) {
            aStringToShow.append("+").append(complexImaginaryPart).append("i");
        } else {
            aStringToShow.append(complexImaginaryPart).append("i");
        }
        return aStringToShow.toString();
    }
}