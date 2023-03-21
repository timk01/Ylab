package thirdweek.passwordvalidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static thirdweek.passwordvalidator.PasswordValidator.isDataValid;

public class PasswordValidatorTest {
    public static void main(String[] args) {
        System.out.println(isDataValid(null, "123", "123"));
        System.out.println(isDataValid("123", null, "123"));
        System.out.println(isDataValid("123", "123", null));
        System.out.println(isDataValid("123", null, null));
        System.out.println(isDataValid(null, null, null));

        System.out.println();

        System.out.println(isDataValid("  ", "123", "123"));
        System.out.println(isDataValid("123", "  ", "123"));
        System.out.println(isDataValid("123", "123", " "));
        System.out.println(isDataValid("123", "  ", " "));
        System.out.println(isDataValid("  ", "  ", ""));

        System.out.println();

        System.out.println(isDataValid("", "123", "123"));
        System.out.println(isDataValid("123", "", "123"));
        System.out.println(isDataValid("123", "123", ""));
        System.out.println(isDataValid("123", "", ""));
        System.out.println(isDataValid("", "", ""));

        System.out.println();

        System.out.println(isDataValid("+АБВasdasdas", "123", "123"));
        System.out.println(isDataValid("123", "+asdasdas,,,,", "123"));
        System.out.println(isDataValid("123", "123", "__________ЦЦЦЦ"));
        System.out.println(isDataValid("123", "ABCАБЦ", "ABCАБЦ"));
        System.out.println(isDataValid("asdasasdasdadЫ", "asdasasdasdadЫ", "asdasasdasdadЫ+"));

        System.out.println();

        System.out.println(isDataValid("12345678901234567890", "123", "123"));
        System.out.println(isDataValid("", "123", "123"));
        System.out.println(isDataValid("123", "12345678901234567890", "123"));
        System.out.println(isDataValid("123", "123", "12345678901234567890"));
        System.out.println(isDataValid("123", "", ""));
        System.out.println(isDataValid("", "12345678901234567890", "12345678901234567890_"));
        System.out.println(isDataValid("", "12345678901234567890", "12345678901234567890"));

        System.out.println();

        System.out.println(isDataValid("asdfasdfasd_A", "123abc_", "123abc_"));
        System.out.println(isDataValid("asdfasdfasd_A", "123Abc_", "123abc_"));
        System.out.println(isDataValid("asdfasdfasd_A", "123abc_Ц", "123abc_"));
    }
}
