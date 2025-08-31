package com.deepak.patientservice.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(EmailAlreadyExistException.class)
    public ResponseEntity<?> handleEmailAlreadyExistException(EmailAlreadyExistException exception) {
        Map<String, String> errors = new HashMap<>();
        if(exception.getMessage() == null) {
            errors.put("email", "Email already exist");
            return ResponseEntity.badRequest().body(errors);
        }
        errors.put("email", exception.getMessage());
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<?> handlePatientNotFoundException(PatientNotFoundException exception) {
        Map<String, String> errors = new HashMap<>();
        if(exception.getMessage() == null) {
            errors.put("patientId", "Patient not found");
            return ResponseEntity.badRequest().body(errors);
        }
        errors.put("patientId", exception.getMessage());
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(KafkaPatientProduceException.class)
    public ResponseEntity<?> handleKafkaPatientProduceException(KafkaPatientProduceException exception) {
        Map<String, String> errors = new HashMap<>();
        if(exception.getMessage() == null) {
            errors.put("error", "Error sending PatientCreated event");
        }
        else {
            errors.put("error", exception.getMessage());
        }
        return ResponseEntity.badRequest().body(errors);
    }
}
