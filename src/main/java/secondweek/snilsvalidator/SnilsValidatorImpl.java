package secondweek.snilsvalidator;

public class SnilsValidatorImpl implements SnilsValidator {
    private static final int SNILS_LENGTH = 11;
    private static final int LESSER_NUMBERS_SNILS_SIZE = 2;
    private static final String ONLY_NUMBERS_REGEX = "\\d+";

    @Override
    public boolean validate(String snils) {
        if (isSnilsBad(snils)) return false;

        int sumOfNumbers = getSumOfNumbers(snils);

        int controlSum = getControlSum(sumOfNumbers);

        return controlSum == Integer.parseInt(snils.substring(SNILS_LENGTH - LESSER_NUMBERS_SNILS_SIZE, SNILS_LENGTH));
    }

    private boolean isSnilsBad(String snils) {
        if (snils == null || snils.length() != SNILS_LENGTH || !snils.matches(ONLY_NUMBERS_REGEX)) {
            return true;
        }
        return false;
    }

    private int getControlSum(int sumOfNumbers) {
        int controlSum;
        int firstValueToCheck = 100;
        int secondValueToCheck = 101;
        if (sumOfNumbers < firstValueToCheck) {
            controlSum = sumOfNumbers;
        } else if (sumOfNumbers == firstValueToCheck) {
            controlSum = 0;
        } else {
            int remainder = sumOfNumbers % secondValueToCheck;
            controlSum = remainder == firstValueToCheck ? 0 : remainder;
        }
        return controlSum;
    }

    private int getSumOfNumbers(String snils) {
        int sumOfNumbers = 0;
        int controlNumberLength = SNILS_LENGTH - LESSER_NUMBERS_SNILS_SIZE;
        for (int i = 0; i < SNILS_LENGTH - LESSER_NUMBERS_SNILS_SIZE; i++) {
            int parsedInt = Integer.parseInt(String.valueOf(snils.charAt(i)));
            sumOfNumbers = sumOfNumbers + parsedInt * controlNumberLength;
            controlNumberLength--;
        }
        return sumOfNumbers;
    }
}
