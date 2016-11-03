package ru.mail.park.exceptions;

public final class ErrorResponse {
    public static final String SERVER_ERROR_MSG = "Server error";
    public static final String VALIDATION_ERROR_MSG = "Invalid input";
    public static final String USER_ALREADY_EXISTS_MSG = "User already exists";
    public static final String AUTHORIZATION_ERROR_MSG = "Wrong login or password";
    public static final String NOT_LOGGED_IN_MSG = "Not logged in";
    public static final String NOT_FOUND_MSG = "Incorrect request (semantic)";
    public static final String USER_NOT_EXIST = "User does not exist";

    private final String msg;
    private final String error;

    public ErrorResponse(String error, String msg) {
        this.msg = msg;
        this.error = error;
    }
}
