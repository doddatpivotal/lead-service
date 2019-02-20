# Lead Service

## Swagger UI
http://localhost:8080/swagger-ui.html

## Swagger Doc
http://localhost:8080/v2/api-docs

## HAL Browser
http://localhost:8080/browser/index.html

## Issues
- _embedded in /lead is an object and not an array

## Test Cases
- /lead returns two leads with self link.  each lead has links for approval, denial, self, and allLeads
- approving a lead then hides the approve/deny options
- deleting a lead then hides the approve/deny options
- you can approve/deny leads through hal brower
- swagger ui shows resources and resource for /lead and /lead/1
- BUG: swagger ui /lead has bad embedded (https://github.com/springfox/springfox/issues/2709)
- /leads endpoint is not exposed