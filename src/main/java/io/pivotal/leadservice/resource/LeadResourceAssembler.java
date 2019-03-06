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

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@Component
public class LeadResourceAssembler implements ResourceAssembler<Lead, Resource<Lead>> {

    @Override
    public Resource<Lead> toResource(Lead lead) {


        Resource<Lead> resource = new Resource<>(lead);

        if (lead.getStatus().equals(LeadStatus.IN_PROGRESS)) {
            resource.add(linkTo(methodOn(LeadController.class)
                    .approveLead(lead.getLeadId(), null)).withRel("approval"));
            resource.add(linkTo(methodOn(LeadController.class)
                    .denyLead(lead.getLeadId(), null)).withRel("denial"));
        }

        resource.add(linkTo(methodOn(LeadController.class)
                .getLeadByLeadId(lead.getLeadId())).withSelfRel());

        resource.add(linkTo(methodOn(LeadController.class)
                .getLeads()).withRel("allLeads"));

        return resource;
    }

    public Resources<Resource<Lead>> toResources(List<Lead> leads) {

        List<Resource<Lead>> leadResourceList = new ArrayList<>();

        leads.forEach(lead -> {
            leadResourceList.add(this.toResource(lead));
        });

        Resources<Resource<Lead>> resources = new Resources<>(leadResourceList);

        resources.add(linkTo(methodOn(LeadController.class)
                .createLead(null)).withRel("create"));

        resources.add(linkTo(methodOn(LeadController.class)
            .getLeads()).withSelfRel());

        return resources;
    }

}
