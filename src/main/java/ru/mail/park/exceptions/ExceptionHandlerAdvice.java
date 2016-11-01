package ru.mail.park.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;
import ru.mail.park.Application;

/**
 * Created by viacheslav on 03.10.16.
 */
@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler({MethodArgumentNotValidException.class, HttpMessageNotReadableException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleValidationException(Exception e) {
        Application.logger.warn(e);
        return new ErrorResponse(HttpStatus.BAD_REQUEST.toString(), ErrorResponse.VALIDATION_ERROR_MSG);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorResponse handleGlobalException(Exception e) {
        Application.logger.warn(e);
        return new ErrorResponse(HttpStatus.BAD_REQUEST.toString(), ErrorResponse.SERVER_ERROR_MSG);
    }

    @ExceptionHandler({NoHandlerFoundException.class, HttpRequestMethodNotSupportedException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handle404() {
        return new ErrorResponse(HttpStatus.NOT_FOUND.toString(), ErrorResponse.NOT_FOUND_MSG);
    }
}
