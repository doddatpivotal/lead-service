package contracts.operatorDashboard

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    name "should return leads"

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
                    leadId      : $(anyInteger()),
                    name        : $(anyAlphaUnicode()),
                    addressLine1: $(regex(/^[a-zA-Z0-9 ]+$/)),
                    city        : $(anyAlphaUnicode()),
                    state       : $(anyAlphaUnicode()),
                    zip         : $(regex(/^\d{5}$/)),
                    status      : $(consumer("IN_PROGRESS"), producer(regex(/^(IN_PROGRESS|ACCEPTED|DENIED)$/)))
                ]
            ]
        ])
        headers {
            contentType "application/hal+json;charset=UTF-8"
        }
    }
}