package thirdweek.passwordvalidator;

public class WrongPasswordException extends Exception {
    public WrongPasswordException() {
    }

    public WrongPasswordException(String description) {
        super(description);
    }
}
