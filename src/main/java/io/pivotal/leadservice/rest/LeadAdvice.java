package io.pivotal.leadservice.rest;

import lombok.extern.java.Log;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@RequestMapping(produces = "application/vnd.error+json")
public class LeadAdvice
    extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value
        = {LeadAlreadyProcessedException.class})
    protected ResponseEntity<VndErrors.VndError> handle(
        LeadAlreadyProcessedException ex) {
        return ResponseEntity
            .status(HttpStatus.METHOD_NOT_ALLOWED)
            .body(new VndErrors.VndError(HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase(), ex.getMessage()));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String joinedFieldErrors = ex.getBindingResult().getFieldErrors().stream()
            .map(fieldError -> "Field: '" + fieldError.getField() + "' has an invalid value of '" + fieldError.getRejectedValue() + "'.  Message: '" + fieldError.getDefaultMessage() + "'")
            .collect(Collectors.joining(", "));

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(new VndErrors.VndError(HttpStatus.BAD_REQUEST.getReasonPhrase(), joinedFieldErrors));
    }

}
