package contracts.acmeMarketingSite

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    name "should accept leads"

    request {
        method POST()
        urlPath("/lead")
        headers {
            contentType applicationJsonUtf8()
        }
        body(
            name: $(consumer(alphaNumeric()), producer('chuck norris')),
            addressLine1: $(consumer(regex(/^[a-zA-Z0-9 ]+$/)), producer('123 main street')),
            addressLine2: $(consumer(optional(regex(/^[a-zA-Z0-9 ]+$/))), producer('123 main street')),
            city: $(consumer(alphaNumeric()), producer('anywhere')),
            state: $(consumer(regex(/[A-Z]{2}/)), producer('NY')),
            zip: $(consumer(regex(/(\d{5})/)), producer('19234')),
            email: $(consumer(regex(email())), producer('abc@abc.com'))
        )
    }

    response {
        status CREATED()
        body(
            status: "IN_PROGRESS",
            name: fromRequest().body('$.name'),
            _links: [
                self: [
                    href: $(consumer('http://localhost:8081/lead/3'), producer(regex(/^(https?:\/\/.*):(\d*)\/?(.*)$/)))
                ]
            ]
        )
        headers {
            contentType "application/hal+json;charset=UTF-8"
        }
    }
}
//$.['_links']['approval']['href']