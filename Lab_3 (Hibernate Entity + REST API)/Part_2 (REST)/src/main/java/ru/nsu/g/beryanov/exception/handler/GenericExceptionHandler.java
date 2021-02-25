package ru.nsu.g.beryanov.exception.handler;

import lombok.extern.log4j.Log4j2;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import ru.nsu.g.beryanov.exception.NoComponentException;

@Log4j2
@ControllerAdvice
public class GenericExceptionHandler extends ResponseEntityExceptionHandler {
    private void logException(Exception exception) {
        log.error("Возникло исключение: ", exception);
    }

    @ExceptionHandler(NoComponentException.class)
    public ResponseEntity<String> handleNoComponentException(NoComponentException noComponentException) {
        logException(noComponentException);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(noComponentException.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(Exception exception) {
        logException(exception);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
    }
}
