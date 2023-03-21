package thirdweek.passwordvalidator;

import com.sun.jdi.connect.Connector;

import java.util.Objects;
import java.util.regex.Pattern;

public class PasswordValidator {
    private final static Pattern PATTERN_FOR_ONLY_NUMBERS_LATINS_UNDERSCORE = Pattern.compile("^[a-zA-Z0-9_]+$");
    private final static Pattern PATTERN_FOR_WHITE_SPACES_ONLY = Pattern.compile("\\S+");
    private final static String STRING_CANNOT_BE_NULL = "не может быть null-типа";
    private final static String STRING_PATTERN_IS_NOT_VALID = "содержит недопустимые символы";
    private final static String STRING_IS_TOO_LONG = "слишком длинный";
    private final static String LOGIN_KEYWORD = "Логин ";
    private final static String PASSWORD_KEYWORD = "Пароль ";


    public static boolean isDataValid(String login, String password, String confirmPassword) {
        boolean isValid = false;

        try {
            if (login == null) {
                throw new IllegalArgumentException(LOGIN_KEYWORD + STRING_CANNOT_BE_NULL);
            } else if (login.isEmpty()) {
                isValid = true;
            } else if (!PATTERN_FOR_ONLY_NUMBERS_LATINS_UNDERSCORE.matcher(login).matches() ||
                    !PATTERN_FOR_WHITE_SPACES_ONLY.matcher(login).matches()) {
                throw new WrongLoginException(LOGIN_KEYWORD + STRING_PATTERN_IS_NOT_VALID);
            } else if (login.length() >= 20) {
                throw new WrongLoginException(LOGIN_KEYWORD + STRING_IS_TOO_LONG);
            }

            if (password == null) {
                throw new IllegalArgumentException(PASSWORD_KEYWORD + STRING_CANNOT_BE_NULL);
            } else if (password.isEmpty()) {
                isValid = true;
            } else if (!PATTERN_FOR_ONLY_NUMBERS_LATINS_UNDERSCORE.matcher(password).matches() ||
                    !PATTERN_FOR_WHITE_SPACES_ONLY.matcher(password).matches()) {
                isValid = false;
                throw new WrongLoginException(PASSWORD_KEYWORD + STRING_PATTERN_IS_NOT_VALID);
            } else if (password.length() >= 20) {
                isValid = false;
                throw new WrongLoginException(PASSWORD_KEYWORD + STRING_IS_TOO_LONG);
            }

            if (confirmPassword == null) {
                isValid = false;
                throw new IllegalArgumentException("Подтверждение пароля " + STRING_CANNOT_BE_NULL);
            } else if (!Objects.equals(confirmPassword, password)) {
                isValid = false;
                throw new WrongLoginException(PASSWORD_KEYWORD + "и подтверждение не совпадают");
            }

            isValid = true;

        } catch (IllegalArgumentException | WrongLoginException msg) {
            System.out.println(msg);
        }

        return isValid;
    }
}
