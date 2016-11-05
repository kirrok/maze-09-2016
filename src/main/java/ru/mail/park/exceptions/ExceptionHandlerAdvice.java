package ru.mail.park.exceptions;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * Created by viacheslav on 03.10.16.
 */

@ControllerAdvice
public class ExceptionHandlerAdvice {

    private static final Logger logger = LogManager.getLogger("main");

    @ExceptionHandler({MethodArgumentNotValidException.class, HttpMessageNotReadableException.class,
            MissingServletRequestParameterException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMsg handleValidationException(Exception e) {
        logger.error("ПРИШЛИ ПЛОХИЕ ДАННЫЕ.", e);
        return new ErrorMsg(ErrorMsg.VALIDATION_ERROR_MSG);
    }

    @ExceptionHandler({NoHandlerFoundException.class, HttpRequestMethodNotSupportedException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorMsg handle404(Exception e) {
        logger.warn(e);
        return new ErrorMsg(ErrorMsg.NOT_FOUND_MSG);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMsg handleGlobalException(Exception e) {
        logger.error("ЧТО ТО ПОШЛО НЕ ТАК.", e);
        return new ErrorMsg(ErrorMsg.SERVER_ERROR_MSG);
    }

}