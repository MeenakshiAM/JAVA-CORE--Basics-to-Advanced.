public class InvalidAgeException extends Exception {
    public InvalidAgeException() {
        super("Age must be at least 13");
    }
}