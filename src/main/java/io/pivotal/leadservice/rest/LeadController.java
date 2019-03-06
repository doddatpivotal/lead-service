package io.pivotal.leadservice.rest;

import io.pivotal.leadservice.model.Lead;
import io.pivotal.leadservice.model.LeadApproval;
import io.pivotal.leadservice.model.LeadDenial;
import io.pivotal.leadservice.model.LeadStatus;
import io.pivotal.leadservice.repository.LeadRepository;
import io.pivotal.leadservice.resource.LeadResourceAssembler;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController()
@RequestMapping("/lead")
public class LeadController {


    private LeadRepository leadRepository;
    private LeadResourceAssembler assembler;

    public LeadController(LeadRepository leadRepository, LeadResourceAssembler assembler) {
        this.leadRepository = leadRepository;
        this.assembler = assembler;
    }

    @GetMapping(produces = "application/hal+json;charset=UTF-8")
    public ResponseEntity<Resources<Resource<Lead>>> getLeads() {
        List<Lead> allLeads = this.leadRepository.findAll();
        return ResponseEntity.ok(assembler.toResources(allLeads));
    }

    @GetMapping(value = "/{leadId}")
    public ResponseEntity<Resource<Lead>> getLeadByLeadId(@PathVariable long leadId) {
        Lead lead = leadRepository.findById(leadId).orElseThrow(() -> new LeadNotFoundException(leadId));
        return ResponseEntity.ok(assembler.toResource(lead));
    }

    @PostMapping()
    public ResponseEntity<Resource<Lead>> createLead(@Valid @RequestBody Lead lead) {
        lead.setStatus(LeadStatus.IN_PROGRESS);
        return new ResponseEntity(assembler.toResource(leadRepository.save(lead)), HttpStatus.CREATED);

    }

    @PostMapping(value = "/{leadId}/approval")
    public ResponseEntity<Resource<Lead>> approveLead(@PathVariable long leadId, @RequestBody LeadApproval leadApproval) {

        Lead lead = leadRepository.findById(leadId).orElseThrow(() -> new LeadNotFoundException(leadId));

        if (lead.getStatus() == LeadStatus.IN_PROGRESS) {
            lead.setStatus(LeadStatus.APPROVED);
            return ResponseEntity.ok(assembler.toResource(leadRepository.save(lead)));
        }

        throw new LeadAlreadyProcessedException(lead.getLeadId(), lead.getStatus());
    }

    @PostMapping("/{leadId}/denial")
    public ResponseEntity<Resource<Lead>> denyLead(@PathVariable long leadId, @RequestBody LeadDenial leadDenial) {

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


    @RequestMapping(value = "/{leadId}/approval", method = {RequestMethod.HEAD})
    public ResponseEntity<ResourceSupport> approveLeadHead(@PathVariable long leadId) {
        return ResponseEntity.ok(null);
    }

}