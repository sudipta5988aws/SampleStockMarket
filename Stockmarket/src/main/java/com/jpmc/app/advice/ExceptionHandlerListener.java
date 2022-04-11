package com.jpmc.app.advice;

import com.jpmc.app.exception.ApplicationException;
import com.jpmc.app.model.ErrorResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.PersistentObjectException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.NoSuchElementException;

/**
 * Controller Advice to Listen Application Exception.
 * In case Exception occurred it Listens and throws meaningful error dto to the client
 * @Author : Sudipta Das
 */

@ControllerAdvice
@Slf4j
public class ExceptionHandlerListener {


    @ExceptionHandler({PersistentObjectException.class, ConstraintViolationException.class})
    @ResponseBody
    public ErrorResponseDTO handleError(HibernateException e, HttpServletResponse response){
        return new ErrorResponseDTO(true,Integer.toString(HttpStatus.CONFLICT.value()),e.getMessage());
    }

    @ExceptionHandler({DataIntegrityViolationException.class, ConcurrencyFailureException.class})
    @ResponseBody
    public ErrorResponseDTO handleError(DataAccessException e, HttpServletResponse response){
        return new ErrorResponseDTO(true,Integer.toString(HttpStatus.CONFLICT.value()),e.getMessage());
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ErrorResponseDTO handleError(NoSuchElementException e, HttpServletResponse response){
        return new ErrorResponseDTO(true,Integer.toString(HttpStatus.NOT_FOUND.value()),e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResponseDTO handleError(IllegalArgumentException e, HttpServletResponse response){
        return new ErrorResponseDTO(true,Integer.toString(HttpStatus.BAD_REQUEST.value()),e.getMessage());
    }

    @ExceptionHandler(ApplicationException.class)
    public ErrorResponseDTO handleError(ApplicationException e, HttpServletResponse response){
        return new ErrorResponseDTO(true,e.getErrorCode().toString(),e.getErrorMessage());
    }

    @ExceptionHandler(Exception.class)
    public ErrorResponseDTO handleError(Exception e, HttpServletResponse response){
        return new ErrorResponseDTO(true,Integer.toString(HttpStatus.INTERNAL_SERVER_ERROR.value()),e.getMessage());
    }
}
