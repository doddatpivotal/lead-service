package io.pivotal.leadservice.contracts;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMessageVerifier
public class BaseTestClass {

//    @Autowired
//    protected WebTestClient webTestClient;
//
//    @Before
//    public void setup() {
//        StandaloneMockMvcBuilder standaloneMockMvcBuilder
//                = MockMvcBuilders.standaloneSetup(leadController);
//        RestAssuredMockMvc.standaloneSetup(standaloneMockMvcBuilder);
//        WebA
//        RestAssuredWebTestClient.webAppContextSetup();
//    }
}
