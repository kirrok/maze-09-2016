package ru.mail.park.exceptions;

public final class ErrorResponse {
    public static final String SERVER_ERROR_MSG = "Ёмаё, ошибка какая-то ;(";
    public static final String VALIDATION_ERROR_MSG = "Проверьте корректность вводимых дынных";
    public static final String USER_ALREADY_EXISTS_MSG = "Такой пользователь существует";
    public static final String AUTHORIZATION_ERROR_MSG = "Неверный логин или пароль";
    public static final String NOT_LOGGED_IN_MSG = "Вы не авторизованы";
    public static final String NOT_FOUND_MSG = "Плохой запрос(метод или урл)";
    public static final String USER_NOT_EXIST = "Такого пользователя, увы, нет";

    private final String msg;
    private final String error;

    public ErrorResponse(String error, String msg) {
        this.msg = msg;
        this.error = error;
    }

    public String getMsg() {
        return msg;
    }

    public String getError() {
        return error;
    }
}
