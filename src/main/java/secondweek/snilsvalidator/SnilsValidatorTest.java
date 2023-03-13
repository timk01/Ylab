package secondweek.snilsvalidator;

public class SnilsValidatorTest {
    public static void main(String[] args) {
        SnilsValidator snilsValidator = new SnilsValidatorImpl();

        System.out.println(snilsValidator.validate(null));
        System.out.println(snilsValidator.validate("123321"));
        System.out.println(snilsValidator.validate(""));
        System.out.println(snilsValidator.validate("123aaa321aa"));
        System.out.println(snilsValidator.validate("-12332100000"));
        System.out.println(snilsValidator.validate("1233211233222"));
        System.out.println(snilsValidator.validate("@123123123!"));

        System.out.println();

        System.out.println(snilsValidator.validate("12332112332")); //false
        System.out.println(snilsValidator.validate("89749771616")); //true
        System.out.println(snilsValidator.validate("01496810963")); //true
        System.out.println(snilsValidator.validate("85616736744")); //true
        System.out.println(snilsValidator.validate("01468870570")); //false
        System.out.println(snilsValidator.validate("84637194945")); //true
        System.out.println(snilsValidator.validate("09734469110")); //true

    }
}
