package org.grails.im

import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

class ChangeInvitationStatusUseCaseServiceSpec extends Specification implements ServiceUnitTest<ChangeInvitationStatusUseCaseService> {

    def "verify publishApprovedUser is called when status is APPROVED"() {
        given:
        service.communityUserGormService = Mock(CommunityUserGormService)
        service.changeInvitationStatusPublisherService = Mock(ChangeInvitationStatusPublisherService)

        when:
        service.changeStatus('email@address.com', RequestInviteStatus.APPROVED)

        then:
        1 * service.communityUserGormService.update(_, _)
        1 * service.changeInvitationStatusPublisherService.publishApprovedUser(_)
        0 * service.changeInvitationStatusPublisherService.publishRejectedUser(_)
    }

    def "verify publishRejectedUser is called when status is REJECTED"() {
        given:
        service.communityUserGormService = Mock(CommunityUserGormService)
        service.changeInvitationStatusPublisherService = Mock(ChangeInvitationStatusPublisherService)

        when:
        service.changeStatus('email@address.com', RequestInviteStatus.REJECTED)

        then:
        1 * service.communityUserGormService.update(_, _)
        0 * service.changeInvitationStatusPublisherService.publishApprovedUser(_)
        1 * service.changeInvitationStatusPublisherService.publishRejectedUser(_)
    }


}
