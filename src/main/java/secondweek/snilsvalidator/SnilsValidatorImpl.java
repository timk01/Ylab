package secondweek.snilsvalidator;

public class SnilsValidatorImpl implements SnilsValidator {
    private final int neededLength = 11;
    private final int lesserNumbersSize = 2;
    private final int oneHundred = 100;
    private final int oneHundredOne = 101;
    private final String onlyNumbersRegex = "\\d+";

    public static final int ZERO = 0;

    @Override
    public boolean validate(String snils) {
        if (snils == null || snils.length() != neededLength || !snils.matches(onlyNumbersRegex)) {
            return false;
        }

        int sumOfNumbers = ZERO;
        int controlNumberLength = neededLength - lesserNumbersSize;
        for (int i = ZERO; i < neededLength - lesserNumbersSize; i++) {
            int parsedInt = Integer.parseInt(String.valueOf(snils.charAt(i)));
            sumOfNumbers = sumOfNumbers + parsedInt * controlNumberLength--;
        }

        int controlSum;
        if (sumOfNumbers < oneHundred) {
            controlSum = sumOfNumbers;
        } else if (sumOfNumbers == oneHundred) {
            controlSum = ZERO;
        } else {
            int remainder = sumOfNumbers % oneHundredOne;
            controlSum = remainder == oneHundred ? ZERO : remainder;
        }

        return controlSum == Integer.parseInt(snils.substring(neededLength - lesserNumbersSize, neededLength));
    }
}
