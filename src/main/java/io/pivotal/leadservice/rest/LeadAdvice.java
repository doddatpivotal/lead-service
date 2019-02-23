package io.pivotal.leadservice.rest;

import lombok.extern.java.Log;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RequestMapping(produces = "application/vnd.error+json")
@Log

public class LeadAdvice
    extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value
        = {LeadAlreadyProcessedException.class})
    protected ResponseEntity<VndErrors.VndError> handle(
        LeadAlreadyProcessedException ex) {
        String bodyOfResponse = "This should be application specific";
        return ResponseEntity
            .status(HttpStatus.METHOD_NOT_ALLOWED)
            .body(new VndErrors.VndError(HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase(), ex.getMessage()));
    }
}
