package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    name "should return results"

    request {
        method GET()
        headers {
            contentType "application/hal+json;charset=UTF-8"
        }
        urlPath("/lead")
    }

    response {
        status OK()
        body([
                _embedded: [
                        leads: [
                                leadId: $(anyInteger()),
                                name: $(anyAlphaUnicode()),
                                addressLine1: $(regex(/^[a-zA-Z0-9 ]+$/)),
                                city: $(anyAlphaUnicode()),
                                state: $(anyAlphaUnicode()),
                                zip: $(regex(/^\d{5}$/)),
                                status: $(consumer("IN_PROGRESS"), producer(regex(/^(IN_PROGRESS|ACCEPTED|DENIED)$/)))
                        ]
                ]
        ])
//        body '''
//
//{
//  "_embedded" : {
//    "leads" : [ {
//      "leadId" : 1,
//      "name" : "Doug",
//      "addressLine1" : "123 Main Street",
//      "city" : "Philadelphia",
//      "state" : "PA",
//      "zip" : "19044",
//      "email" : "doug@test.mail.com",
//      "status" : "IN_PROGRESS",
//      "_links" : {
//        "self" : {
//          "href" : "http://localhost:8080/lead/1"
//        },
//        "approval" : {
//          "href" : "http://localhost:8080/lead/1/approval"
//        },
//        "denial" : {
//          "href" : "http://localhost:8080/lead/1/denial"
//        },
//        "allLeads" : {
//          "href" : "http://localhost:8080/lead"
//        }
//      }
//    }, {
//      "leadId" : 2,
//      "name" : "Martin",
//      "addressLine1" : "897 South Street",
//      "city" : "Trenton",
//      "state" : "NJ",
//      "zip" : "08601",
//      "email" : "martin@test.mail.com",
//      "status" : "IN_PROGRESS",
//      "_links" : {
//        "self" : {
//          "href" : "http://localhost:8080/lead/2"
//        },
//        "approval" : {
//          "href" : "http://localhost:8080/lead/2/approval"
//        },
//        "denial" : {
//          "href" : "http://localhost:8080/lead/2/denial"
//        },
//        "allLeads" : {
//          "href" : "http://localhost:8080/lead"
//        }
//      }
//    } ]
//  },
//  "_links" : {
//    "self" : {
//      "href" : "http://localhost:8080/lead"
//    }
//  }
//}
//
//        '''
        headers {
            contentType "application/hal+json;charset=UTF-8"
        }
    }
}