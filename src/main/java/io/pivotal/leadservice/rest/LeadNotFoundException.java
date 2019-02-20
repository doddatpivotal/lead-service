package io.pivotal.leadservice.rest;

public class LeadNotFoundException extends RuntimeException {
    LeadNotFoundException(Long leadId) {
        super("Could not find lead " + leadId);
    }
}
