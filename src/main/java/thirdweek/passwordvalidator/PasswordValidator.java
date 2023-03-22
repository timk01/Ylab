package thirdweek.passwordvalidator;

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