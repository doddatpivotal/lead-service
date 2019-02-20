package io.pivotal.leadservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Lead {

    @Id
    @GeneratedValue
    private Long leadId;
    private String name;
    private String addressLine1;
    private String city;
    private String state;
    private String zip;
    private String email;
    private LeadStatus status;

}
