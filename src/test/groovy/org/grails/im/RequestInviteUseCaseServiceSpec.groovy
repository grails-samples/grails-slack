package org.grails.im

import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

class RequestInviteUseCaseServiceSpec extends Specification implements ServiceUnitTest<RequestInviteUseCaseService> {

    def "verify publishEvent is called"() {
        given:
        RequestInvite requestInvite = new RequestInviteImpl(email: 'email@address.com', about: 'I am OK')
        service.requestInvitePublisherService = Mock(RequestInvitePublisherService)

        when:
        service.request(requestInvite)

        then:
        1 * service.requestInvitePublisherService.publishEvent(requestInvite)

    }
}
