package io.pivotal.leadservice;

import io.pivotal.leadservice.model.Lead;
import io.pivotal.leadservice.model.LeadStatus;
import io.pivotal.leadservice.repository.LeadRepository;
import lombok.extern.java.Log;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.config.EnableHypermediaSupport;

import java.util.stream.Stream;

@Log
@SpringBootApplication
@EnableHypermediaSupport(type= {EnableHypermediaSupport.HypermediaType.HAL})
public class LeadServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(LeadServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner loadData(LeadRepository repository) {
        return commandLineRunner -> {
            Stream.of("Doug:123 Main Street::Philadelphia:PA:19044:doug@test.mail.com",
                    "Martin:897 South Street:Apt 2:Trenton:NJ:08601:martin@test.mail.com").forEach(token -> {
                String[] split = token.split(":");
                repository.save(new Lead(null, split[0], split[1], split[2], split[3], split[4], split[5], split[6], LeadStatus.IN_PROGRESS));
            });
            repository.findAll().forEach(lead -> log.info(lead.toString()));
        };
    }
}
