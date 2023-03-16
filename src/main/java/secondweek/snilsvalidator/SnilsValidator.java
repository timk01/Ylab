package secondweek.snilsvalidator;

/**
 * Represents a SnilsValidator.
 *
 * @author Ylab
 * @version 1.0
 */
public interface SnilsValidator {
    /**
     * Checks is SNILS in this String is a valid one
     *
     * @param snils SNILS
     * @return boolean result of checking
     */
    boolean validate(String snils);
}