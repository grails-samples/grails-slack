package org.grails.im.usecase

import grails.testing.services.ServiceUnitTest
import org.grails.im.entities.CommunityUserImpl
import org.grails.im.entities.RequestInviteStatus
import org.grails.im.plugins.repository.CommunityUserRepository
import org.grails.im.usecase.ChangeInvitationStatusPublisherService
import org.grails.im.usecase.ChangeInvitationStatusUseCaseService
import spock.lang.Specification

class ChangeInvitationStatusUseCaseServiceSpec extends Specification implements ServiceUnitTest<ChangeInvitationStatusUseCaseService> {

    def "verify publishApprovedUser is called when status is APPROVED"() {
        given:
        service.communityUserRepository = Stub(CommunityUserRepository) {
            find(_) >> new CommunityUserImpl(email: 'email@address.com', about: 'I am OK')
        }
        service.changeInvitationStatusPublisherService = Mock(ChangeInvitationStatusPublisherService)

        when:
        service.changeStatus('email@address.com', RequestInviteStatus.APPROVED)

        then:
        1 * service.changeInvitationStatusPublisherService.publishApprovedUser(_)
        0 * service.changeInvitationStatusPublisherService.publishRejectedUser(_)
    }

    def "verify publishRejectedUser is called when status is REJECTED"() {
        given:
        service.communityUserRepository = Stub(CommunityUserRepository) {
            find(_) >> new CommunityUserImpl(email: 'email@address.com', about: 'I am OK')
        }
        service.changeInvitationStatusPublisherService = Mock(ChangeInvitationStatusPublisherService)

        when:
        service.changeStatus('email@address.com', RequestInviteStatus.REJECTED)

        then:
        0 * service.changeInvitationStatusPublisherService.publishApprovedUser(_)
        1 * service.changeInvitationStatusPublisherService.publishRejectedUser(_)
    }


}
