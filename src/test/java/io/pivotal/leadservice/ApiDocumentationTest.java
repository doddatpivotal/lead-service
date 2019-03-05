package io.pivotal.leadservice;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LeadServiceApplication.class)
public class ApiDocumentationTest {

    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("target/generated-snippets");

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
            .apply(documentationConfiguration(this.restDocumentation))
            .alwaysDo(document("{method-name}",
                preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
            .build();
    }

    @Test
    public void createLeadExample() throws Exception {

        this.mockMvc.perform(post("/lead")
            .contentType(MediaTypes.HAL_JSON)
            .content("{\n" +
                "  \"name\": \"chuck norris\",\n" +
                "  \"addressLine1\": \"123 main street\",\n" +
                "  \"addressLine2\": \"123 main street\",\n" +
                "  \"city\": \"anywhere\",\n" +
                "  \"state\": \"NY\",\n" +
                "  \"zip\": \"19234\",\n" +
                "  \"email\": \"abc@abc.com\"\n" +
                "}"))
            .andExpect(status().isCreated())
            .andDo(document("create-lead-example",
                requestFields(
                    fieldWithPath("name").description("The name of the input"),
                    fieldWithPath("addressLine1").description("The addressLine1 of the input"),
                    fieldWithPath("addressLine2").description("The addressLine2 of the input"),
                    fieldWithPath("city").description("The city of the input"),
                    fieldWithPath("state").description("The state of the input"),
                    fieldWithPath("zip").description("The zip of the input"),
                    fieldWithPath("email").description("The email of the input")
                ),
                links(
                    linkWithRel("allLeads").description("All the leads"),
                    linkWithRel("self").description("Current lead"),
                    linkWithRel("denial").description("Deny the lead"),
                    linkWithRel("approval").description("Approve the lead")
                ),
                leadResponseFieldsSnippet()
            ));
    }

    private ResponseFieldsSnippet leadResponseFieldsSnippet() {
        return responseFields(
            subsectionWithPath("_links")
                .description("Links to other resources"),
            fieldWithPath("name").description("The name of the input"),
            fieldWithPath("addressLine1").description("The addressLine1 of the input"),
            fieldWithPath("addressLine2").description("The addressLine2 of the input"),
            fieldWithPath("city").description("The city of the input"),
            fieldWithPath("state").description("The state of the input"),
            fieldWithPath("zip").description("The zip of the input"),
            fieldWithPath("email").description("The email of the input"),
            fieldWithPath("status").description("The status of the input")
        );
    }

    @Test
    public void getLeadExample() throws Exception {

        this.mockMvc.perform(get("/lead/1").contentType(MediaTypes.HAL_JSON))
            .andExpect(status().isOk())
            .andExpect(status().isOk())
            .andDo(document("get-lead-example",
                leadResponseFieldsSnippet()
            ));
    }
}
