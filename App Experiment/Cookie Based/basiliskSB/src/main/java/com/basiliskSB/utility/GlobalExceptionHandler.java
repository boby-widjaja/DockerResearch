package com.basiliskSB.utility;

import com.basiliskSB.dto.utility.ExceptionDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice("com.basiliskSB.rest")
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDTO> handleRuntimeException(Exception exception) {
        var firstStack = exception.getStackTrace()[0];
        var dto = new ExceptionDTO(
            firstStack.getClassName(),
            firstStack.getMethodName(),
            exception.getMessage()
        );
        return ResponseEntity.status(500).body(dto);
    }
}
