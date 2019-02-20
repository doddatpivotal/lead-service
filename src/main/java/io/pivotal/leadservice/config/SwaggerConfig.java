package io.pivotal.leadservice.config;

import com.fasterxml.classmate.TypeResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.VndErrors;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("io.pivotal.leadservice.rest"))
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false)
                .alternateTypeRules(
                        AlternateTypeRules.newRule(typeResolver.resolve(List.class, Link.class),
                                typeResolver.resolve(Object.class)))
                .globalResponseMessage(RequestMethod.GET,
                        newArrayList(new ResponseMessageBuilder()
                                        .code(500)
                                        .message("Internal Server Error!")
                                        .responseModel(new ModelRef("VndError"))
                                        .build(),
                                new ResponseMessageBuilder()
                                        .code(403)
                                        .message("Forbidden!")
                                        .build()))
                .additionalModels(typeResolver.resolve(VndErrors.VndError.class))
                .apiInfo(apiInfo());
    }

    @Autowired
    private TypeResolver typeResolver;

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Leads Rest API",
                "A sample API for marketing leads.",
                "1.0",
                "Terms of service",
                new Contact("Dodd Pfeffer", "https://leads-service.cfapps.io", "dpfeffer@pivotal.io"),
                "Apache 2", "https://www.apache.org/licenses/LICENSE-2.0", Collections.emptyList());

    }
}