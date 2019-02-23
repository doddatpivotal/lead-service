package contracts.acmeMarketingSite

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    name "should return results"

    request {
        method POST()
        urlPath("/lead")
        headers {
            contentType applicationJsonUtf8()
        }
        body(
            name: $(consumer(alphaNumeric()), producer('chuck norris')),
            addressLine1: $(consumer(regex(/^[a-zA-Z0-9 ]+$/)), producer('123 main street')),
            city: $(consumer(regex(alphaNumeric())), producer('anywhere')),
            state: $(consumer(regex(/[A-Z]{2}/)), producer('NY')),
            zip: $(consumer(regex(/(\d{5})/)), producer('19234')),
            email: $(consumer(regex(email())), producer('abc@abc.com'))
        )
    }

    response {
        status OK()
        body(
            leadId: anyInteger(),
            status: "IN_PROGRESS",
        )
        headers {
            contentType "application/hal+json;charset=UTF-8"
        }
    }
}