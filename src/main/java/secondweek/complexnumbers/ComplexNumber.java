package secondweek.complexnumbers;

/**
 * Represents a ComplexNumber.
 *
 * @author Timur Khasmamedov
 * @version 1.0
 */
public final class ComplexNumber {
    private final double complexRealPart;
    private final double complexImaginaryPart;
    private static final double ZERO = 0.0;

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
        return new ComplexNumber(
                complexRealPart + complexNumber.getComplexRealPart(),
                complexImaginaryPart + complexNumber.getComplexImaginaryPart()
        );
    }

    /**
     * Subtracts the current complexNumber from another one complexNumber.
     *
     * @param complexNumber number
     * @return new instance of ComplexNumber
     */
    public ComplexNumber subtraction(ComplexNumber complexNumber) {
        return new ComplexNumber(
                complexRealPart - complexNumber.getComplexRealPart(),
                complexImaginaryPart - complexNumber.getComplexImaginaryPart()
        );
    }

    /**
     * Multiplies the current complexNumber on another one complexNumber.
     *
     * @param complexNumber number
     * @return new instance of ComplexNumber
     */
    public ComplexNumber multiplication(ComplexNumber complexNumber) {
        return new ComplexNumber(
                complexRealPart * complexNumber.getComplexRealPart() - complexImaginaryPart * complexNumber.getComplexImaginaryPart(),
                complexImaginaryPart * complexNumber.getComplexRealPart() + complexRealPart * complexNumber.getComplexImaginaryPart()
        );
    }

    /**
     * Getting modulus of complexNumber.
     *
     * @return number of Double type
     */
    public Double modulus() {
        return Math.sqrt(Math.pow(complexRealPart, 2) + Math.pow(complexImaginaryPart, 2));
    }

    @Override
    public String toString() {
        StringBuilder aStringToShow = new StringBuilder().append(complexRealPart);
        if (Double.compare(complexImaginaryPart, ZERO) >= 0) {
            aStringToShow.append("+").append(complexImaginaryPart).append("i");
        } else {
            aStringToShow.append(complexImaginaryPart).append("i");
        }
        return aStringToShow.toString();
    }
}