package com.example.demo.advice;

import java.lang.reflect.InvocationTargetException;

import org.springframework.core.convert.ConversionFailedException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.rest.core.RepositoryConstraintViolationException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.data.rest.webmvc.support.ETagDoesntMatchException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.log4j.Log4j2;

@ControllerAdvice
@Log4j2
public class SpringDataRestExceptionHandler {

    @ExceptionHandler({
            ResourceNotFoundException.class,
            HttpMessageNotReadableException.class,
            InvocationTargetException.class,
            IllegalArgumentException.class,
            ConversionFailedException.class,
            NullPointerException.class,
            RepositoryConstraintViolationException.class,
            OptimisticLockingFailureException.class,
            DataIntegrityViolationException.class,
            HttpRequestMethodNotSupportedException.class,
            ETagDoesntMatchException.class
    })
    final ResponseEntity<?> handleException(Exception e) {
        if (e instanceof ResourceNotFoundException ex) {
            return handleNotFound(ex);
        } else if (e instanceof HttpMessageNotReadableException ex) {
            return handleBadRequest(ex);
        } else if (e instanceof InvocationTargetException ex) {
            return handleInternalServerError(ex);
        } else if (e instanceof IllegalArgumentException ex) {
            return handleInternalServerError(ex);
        } else if (e instanceof ConversionFailedException ex) {
            return handleInternalServerError(ex);
        } else if (e instanceof NullPointerException ex) {
            return handleInternalServerError(ex);
        } else if (e instanceof RepositoryConstraintViolationException ex) {
            return handleBadRequest(ex);
        } else if (e instanceof OptimisticLockingFailureException ex) {
            return handleConflict(ex);
        } else if (e instanceof DataIntegrityViolationException ex) {
            return handleConflict(ex);
        } else if (e instanceof HttpRequestMethodNotSupportedException ex) {
            return handleBadRequest(ex);
        } else if (e instanceof ETagDoesntMatchException ex) {
            return handleBadRequest(ex);
        }
        return handleInternalServerError(e);
    }

    private ResponseEntity<?> handleNotFound(ResourceNotFoundException e) {
        log.info(e.getMessage());
        return ResponseEntity.notFound().build();
    }

    private ResponseEntity<?> handleBadRequest(Exception e) {
        log.info(e.getMessage());
        return ResponseEntity.badRequest().body("BadRequest");
    }

    private ResponseEntity<?> handleConflict(Exception e) {
        log.info(e.getMessage());
        return ResponseEntity.status(409).body("Conflict");
    }

    private ResponseEntity<?> handleInternalServerError(Exception e) {
        log.error(e.getMessage(), e.getCause());
        return ResponseEntity.internalServerError().body("Internal Server Error");
    }
}
