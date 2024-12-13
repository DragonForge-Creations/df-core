package me.quickscythe.dragonforge.exceptions;


public class QuickException extends Exception {
    public QuickException(String message) {
        super(message);
    }

    public QuickException(String message, Object... args) {
        this(String.format(message, args));
    }

    public QuickException(String message, Throwable cause) {
        super(message, cause);
    }

    public QuickException(String message, Throwable cause, Object... args) {
        this(String.format(message, args), cause);
    }
}