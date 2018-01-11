package org.grails.im.usecase

import grails.testing.services.ServiceUnitTest
import org.grails.im.entities.RequestInvite
import org.grails.im.entities.RequestInviteImpl
import org.grails.im.plugins.repository.CommunityUserRepository
import org.grails.im.usecase.RequestInvitePublisherService
import org.grails.im.usecase.RequestInviteUseCaseService
import spock.lang.Specification

class RequestInviteUseCaseServiceSpec extends Specification implements ServiceUnitTest<RequestInviteUseCaseService> {

    def "verify publishEvent is called"() {
        given:
        RequestInvite requestInvite = new RequestInviteImpl(email: 'email@address.com', about: 'I am OK')
        service.requestInvitePublisherService = Mock(RequestInvitePublisherService)
        service.communityUserRepository = Mock(CommunityUserRepository)

        when:
        service.request(requestInvite)

        then:
        1 * service.requestInvitePublisherService.publishEvent(requestInvite)

    }
}
