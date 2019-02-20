package io.pivotal.leadservice.resource;

import io.pivotal.leadservice.model.Lead;
import io.pivotal.leadservice.model.LeadStatus;
import io.pivotal.leadservice.rest.LeadController;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

public class LeadResourceAssembler extends ResourceAssemblerSupport<Lead, LeadResource> {

    public LeadResourceAssembler() {
        super(LeadController.class, LeadResource.class);
    }

    @Override
    public LeadResource toResource(Lead lead) {

        LeadResource resource = createResourceWithId(lead.getLeadId(), lead);
        resource.setLeadId(lead.getLeadId());
        resource.setName(lead.getName());
        resource.setAddressLine1(lead.getAddressLine1());
        resource.setCity(lead.getCity());
        resource.setState(lead.getState());
        resource.setZip(lead.getZip());
        resource.setEmail(lead.getEmail());
        resource.setStatus(lead.getStatus());

        if(lead.getStatus().equals(LeadStatus.IN_PROGRESS)) {
            resource.add(ControllerLinkBuilder.linkTo(
                    ControllerLinkBuilder.methodOn(LeadController.class)
                            .approveLead(lead.getLeadId(), null)).withRel("approval"));
            resource.add(ControllerLinkBuilder.linkTo(
                    ControllerLinkBuilder.methodOn(LeadController.class)
                            .denyLead(lead.getLeadId(), null)).withRel("denial"));
        }
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(LeadController.class)
                        .getLeads()).withRel("allLeads"));
        return resource;

    }
}
