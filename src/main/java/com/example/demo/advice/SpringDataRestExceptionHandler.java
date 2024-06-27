package com.example.demo.advice;

import java.lang.reflect.InvocationTargetException;

import org.springframework.core.convert.ConversionFailedException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.rest.core.RepositoryConstraintViolationException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.data.rest.webmvc.support.ETagDoesntMatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.demo.dto.ErrorResponse;

@ControllerAdvice
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

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    private ResponseEntity<?> handleNotFound(ResourceNotFoundException e) {
        return ResponseEntity
                .ok(ErrorResponse
                        .builder()
                        .code("NOT_FOUND")
                        .message(e.getMessage())
                        .build());
    }

    private ResponseEntity<?> handleBadRequest(Exception e) {
        if (e instanceof RepositoryConstraintViolationException ex) {
            StringBuffer sb = new StringBuffer();
            for (FieldError fieldError : ex.getErrors().getFieldErrors()) {
                sb.append(fieldError.getDefaultMessage());
            }
            return ResponseEntity.badRequest()
                    .body(ErrorResponse.builder().code("BAD_REQUEST")
                            .message(
                                    sb.toString())
                            .build());
        } else {

            return ResponseEntity.badRequest()
                    .body(ErrorResponse.builder()
                            .code("BAD_REQUEST")
                            .message(e.getMessage())
                            .build());
        }
    }

    private ResponseEntity<?> handleConflict(Exception e) {
        return ResponseEntity.status(409).body(ErrorResponse.builder()
                .code("CONFLICT")
                .message(e.getMessage())
                .build());
    }

    private ResponseEntity<?> handleInternalServerError(Exception e) {
        return ResponseEntity.internalServerError()
                .body(ErrorResponse
                        .builder()
                        .code("INTERNAL_SERVER_ERROR")
                        .message(e.getMessage())
                        .build());
    }
}
