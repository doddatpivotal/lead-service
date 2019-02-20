package io.pivotal.leadservice.rest;

import io.pivotal.leadservice.model.Lead;
import io.pivotal.leadservice.model.LeadApproval;
import io.pivotal.leadservice.model.LeadDenial;
import io.pivotal.leadservice.model.LeadStatus;
import io.pivotal.leadservice.repository.LeadRepository;
import io.pivotal.leadservice.resource.LeadResource;
import io.pivotal.leadservice.resource.LeadResourceAssembler;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/lead")
public class LeadController {


    private LeadRepository leadRepository;
    private LeadResourceAssembler assembler = new LeadResourceAssembler();

    public LeadController(LeadRepository leadRepository) {
        this.leadRepository = leadRepository;
    }

    @GetMapping(produces = "application/hal+json;charset=UTF-8")
    public ResponseEntity<Resources<LeadResource>> getLeads() {
        List<Lead> allLeads = this.leadRepository.findAll();
        Resources<LeadResource> resources = new Resources<>(assembler.toResources(allLeads));

        resources.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(LeadController.class)
                        .getLeads())
                .withSelfRel());

        return ResponseEntity.ok(resources);
    }

    @GetMapping(value = "/{leadId}")
    public ResponseEntity<LeadResource> getLeadByLeadId(@PathVariable long leadId) {
        Lead lead = this.leadRepository.getOne(leadId);
        return ResponseEntity.ok(assembler.toResource(lead));
    }

    @PostMapping()
    public ResponseEntity<LeadResource> createLead(@RequestBody Lead lead) {
        lead.setStatus(LeadStatus.IN_PROGRESS);
        return ResponseEntity.ok(assembler.toResource(leadRepository.save(lead)));

    }

    @PostMapping(value = "/{leadId}/approval")
    public ResponseEntity<LeadResource> approveLead(@PathVariable long leadId, @RequestBody LeadApproval leadApproval) {

        Lead lead = leadRepository.findById(leadId).orElseThrow(() -> new LeadNotFoundException(leadId));

        if (lead.getStatus() == LeadStatus.IN_PROGRESS) {
            lead.setStatus(LeadStatus.APPROVED);
            return ResponseEntity.ok(assembler.toResource(leadRepository.save(lead)));
        }

        throw new LeadAlreadyProcessedException(lead.getLeadId(), lead.getStatus());
    }

    @RequestMapping(value = "/{leadId}/approval", method = {RequestMethod.HEAD})
    public ResponseEntity<ResourceSupport> approveLeadHead(@PathVariable long leadId) {
        return ResponseEntity.ok(null);
    }

    @PostMapping("/{leadId}/denial")
    public ResponseEntity<LeadResource> denyLead(@PathVariable long leadId, @RequestBody LeadDenial leadDenial) {

        Lead lead = leadRepository.findById(leadId).orElseThrow(() -> new LeadNotFoundException(leadId));

        if (lead.getStatus() == LeadStatus.IN_PROGRESS) {
            lead.setStatus(LeadStatus.DENIED);
            return ResponseEntity.ok(assembler.toResource(leadRepository.save(lead)));
        }

        throw new LeadAlreadyProcessedException(lead.getLeadId(), lead.getStatus());

    }

    @RequestMapping(value = "/{leadId}/denial", method = {RequestMethod.HEAD})
    public ResponseEntity<ResourceSupport> denyLeadHead(@PathVariable long leadId) {
        return ResponseEntity.ok(null);
    }


}