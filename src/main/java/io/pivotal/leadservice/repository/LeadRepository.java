package io.pivotal.leadservice.repository;

import io.pivotal.leadservice.model.Lead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface LeadRepository extends JpaRepository<Lead, Long> { }
