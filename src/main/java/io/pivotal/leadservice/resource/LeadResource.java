package io.pivotal.leadservice.resource;

import io.pivotal.leadservice.model.LeadStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Relation(collectionRelation = "leads", value = "lead")
public class LeadResource extends ResourceSupport {

    private Long leadId;
    private String name;
    private String addressLine1;
    private String city;
    private String state;
    private String zip;
    private String email;
    private LeadStatus status;

}
