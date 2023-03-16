package secondweek.snilsvalidator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SnilsValidatorImplTest {
    private SnilsValidator snilsValidator = new SnilsValidatorImpl();

    @Test
    void validate_WrongSnilsIsProvided_Fail() {
        assertFalse(snilsValidator.validate(null));
        assertFalse(snilsValidator.validate("123321"));
        assertFalse(snilsValidator.validate(""));
        assertFalse(snilsValidator.validate("123aaa321aa"));
        assertFalse(snilsValidator.validate("-12332100000"));
        assertFalse(snilsValidator.validate("1233211233222"));
        assertFalse(snilsValidator.validate("@123123123!"));

        assertFalse(snilsValidator.validate("12332112332"));
        assertFalse(snilsValidator.validate("01468870570"));
    }

    @Test
    void validate_ProperSnilsIsProvided_Success() {
        assertTrue(snilsValidator.validate("89749771616"));
        assertTrue(snilsValidator.validate("01496810963"));
        assertTrue(snilsValidator.validate("85616736744"));
        assertTrue(snilsValidator.validate("84637194945"));
        assertTrue(snilsValidator.validate("09734469110"));
    }
}