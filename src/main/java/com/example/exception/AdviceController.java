package com.example.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AdviceController {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponseDto> baseExceptionHandler(BaseException ex) {
        ErrorResponseDto dto = new ErrorResponseDto();
        dto.setMessage(ex.getMessage());
        return ResponseEntity.status(ex.getHttpStatus().value()).body(dto);
    }

}