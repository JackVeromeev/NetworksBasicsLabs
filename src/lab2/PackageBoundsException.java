package lab2;

/**
 * Created by Jack on 06.10.2016.
 */
public class PackageBoundsException extends Exception {
    private String message;
    public PackageBoundsException(String msg) {
        message = msg;
    }
    public String getMessage() {
        return message;
    }
}
