package secondweek.complexnumbers;

public class ComplexNumberTest {
    public static void main(String[] args) {
        ComplexNumber firstComplexNumber = new ComplexNumber(15.3, 3.2);
        ComplexNumber secondComplexNumber = new ComplexNumber(15.3, 3.2);

        System.out.println(firstComplexNumber.addition(secondComplexNumber));

        System.out.println(firstComplexNumber.subtraction(secondComplexNumber));
        System.out.println(firstComplexNumber.subtraction(secondComplexNumber));
        System.out.println(firstComplexNumber.subtraction(new ComplexNumber(30, 3)));
        System.out.println(firstComplexNumber.subtraction(new ComplexNumber(0, 30)));

        System.out.println(firstComplexNumber.multiplication(secondComplexNumber));
        System.out.println(secondComplexNumber.multiplication(firstComplexNumber));
        System.out.println(firstComplexNumber.multiplication(new ComplexNumber(0, 30)));

        System.out.println(firstComplexNumber.modulus());
        System.out.println(secondComplexNumber.modulus());

        ComplexNumber thirdComplexNumber = new ComplexNumber(15.3);

        System.out.println(thirdComplexNumber);
        System.out.println(thirdComplexNumber.addition(firstComplexNumber));
        System.out.println(thirdComplexNumber.subtraction(secondComplexNumber));
        System.out.println(secondComplexNumber.modulus());
    }
}
