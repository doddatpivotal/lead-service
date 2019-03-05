package io.pivotal.leadservice.resource;

import io.pivotal.leadservice.model.Lead;
import io.pivotal.leadservice.model.LeadStatus;
import io.pivotal.leadservice.rest.LeadController;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LeadResourceAssembler implements ResourceAssembler<Lead, Resource<Lead>> {

    @Override
    public Resource<Lead> toResource(Lead lead) {


        Resource<Lead> resource = new Resource<>(lead);

        if (lead.getStatus().equals(LeadStatus.IN_PROGRESS)) {
            resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(LeadController.class)
                    .approveLead(lead.getLeadId(), null)).withRel("approval"));
            resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(LeadController.class)
                    .denyLead(lead.getLeadId(), null)).withRel("denial"));
        }
        resource.add(ControllerLinkBuilder.linkTo(
            ControllerLinkBuilder.methodOn(LeadController.class)
                .getLeadByLeadId(lead.getLeadId())).withSelfRel());

        resource.add(ControllerLinkBuilder.linkTo(
            ControllerLinkBuilder.methodOn(LeadController.class)
                .getLeads()).withRel("allLeads"));

        return resource;
    }

    public Resources<Resource<Lead>> toResources(List<Lead> leads) {

        List<Resource<Lead>> leadResources = new ArrayList<>();
        leads.forEach(lead -> {
            leadResources.add(this.toResource(lead));
        });

        return new Resources<>(leadResources);
    }

}
