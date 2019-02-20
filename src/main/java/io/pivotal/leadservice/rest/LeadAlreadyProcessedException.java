package io.pivotal.leadservice.rest;

import io.pivotal.leadservice.model.LeadStatus;

public class LeadAlreadyProcessedException extends RuntimeException{
    LeadAlreadyProcessedException(Long leadId, LeadStatus status) {
        super("Error processing lead + " + leadId + "You can't approve or deny a lead that is in the " + status + " status");
    }
}
