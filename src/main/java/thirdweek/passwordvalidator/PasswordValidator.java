package thirdweek.passwordvalidator;

import thirdweek.trasnliterator.Transliterator;

import java.util.regex.Pattern;

/**
 * Represents a PasswordValidator.
 * The class validates the data of login, password and password confirmation and if it's wrong,
 * throws exception of appropriate type, plus returns false; if (and only if) data is ok, it returns true.
 *
 * @author Khasmamedov T.
 * @version 1.4
 * @see WrongLoginException (custom class)
 * @see WrongLoginException (custom class)
 */
public class PasswordValidator {
    private final static Pattern PATTERN_FOR_ONLY_NUMBERS_LATINS_UNDERSCORE = Pattern.compile("^[a-zA-Z0-9_]+$");
    private final static Pattern PATTERN_FOR_WHITE_SPACES_ONLY = Pattern.compile("\\S+");
    private final static String STRING_CANNOT_BE_NULL = "не может быть null-типа";
    private final static String STRING_PATTERN_IS_NOT_VALID = "содержит недопустимые символы";
    private final static String STRING_IS_TOO_LONG = "слишком длинный";
    private final static String LOGIN_KEYWORD = "Логин ";
    private final static String PASSWORD_KEYWORD = "Пароль ";

    /**
     * Original static method for data validation
     * It first checks login on null value, proper symbols (latin, numbers and _), length
     * Secondly, do the same with password
     * Lastly, confirms that password and it's re-enter are the same.
     *
     * @param login of type String
     * @param password of type String
     * @param confirmPassword of type String
     * @return boolean value (true only if data is ok)
     * @throws WrongLoginException if login is bad
     * @throws WrongPasswordException if password is bad or password is not equal to it's re-enter
     */
    public static boolean isDataValid(String login, String password, String confirmPassword) {
        try {
            checkLoginValidity(login);
            checkPasswordValidity(password, confirmPassword);
        } catch (WrongLoginException | WrongPasswordException msg) {
            System.out.println(msg.getMessage());
            return false;
        }

        return true;
    }

    private static void checkLoginValidity(String login) throws WrongLoginException {
        if (login == null) {
            throw new WrongLoginException(LOGIN_KEYWORD + STRING_CANNOT_BE_NULL);
        }

        if (!login.isEmpty()) {
            if (!PATTERN_FOR_ONLY_NUMBERS_LATINS_UNDERSCORE.matcher(login).matches() ||
                    !PATTERN_FOR_WHITE_SPACES_ONLY.matcher(login).matches()) {
                throw new WrongLoginException(LOGIN_KEYWORD + STRING_PATTERN_IS_NOT_VALID);
            }
        }

        if (login.length() >= 20) {
            throw new WrongLoginException(LOGIN_KEYWORD + STRING_IS_TOO_LONG);
        }
    }

    private static void checkPasswordValidity(String password, String confirmPassword) throws WrongPasswordException {
        if (password == null) {
            throw new WrongPasswordException(PASSWORD_KEYWORD + STRING_CANNOT_BE_NULL);
        }

        if (!password.isEmpty()) {
            if (!PATTERN_FOR_ONLY_NUMBERS_LATINS_UNDERSCORE.matcher(password).matches() ||
                    !PATTERN_FOR_WHITE_SPACES_ONLY.matcher(password).matches()) {
                throw new WrongPasswordException(PASSWORD_KEYWORD + STRING_PATTERN_IS_NOT_VALID);
            }
        }

        if (password.length() >= 20) {
            throw new WrongPasswordException(PASSWORD_KEYWORD + STRING_IS_TOO_LONG);
        }

        if (!password.equals(confirmPassword)) {
            throw new WrongPasswordException(PASSWORD_KEYWORD + "и подтверждение не совпадают");
        }
    }
}