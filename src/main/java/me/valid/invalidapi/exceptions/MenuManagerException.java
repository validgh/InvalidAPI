package me.valid.invalidapi.exceptions;

public class MenuManagerException extends RuntimeException {

    public MenuManagerException() {
        super();
    }

    public MenuManagerException(String message) {
        super(message);
    }

    public MenuManagerException(String message, Throwable cause) {
        super(message, cause);
    }

    public MenuManagerException(Throwable cause) {
        super(cause);
    }
}
