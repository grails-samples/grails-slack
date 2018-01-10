package org.grails.im

import grails.testing.mixin.integration.Integration
import spock.lang.Specification
import spock.util.concurrent.PollingConditions

@Integration
class RequestInviteUseCaseServiceIntegrationSpec extends Specification {

    RequestInviteUseCaseService requestInviteUseCaseService
    CommunityUserGormService communityUserGormService

    def conditions = new PollingConditions(timeout: 30)

    void "requesting an invite adds a user to the database"() {
        when:
        requestInviteUseCaseService.request(new RequestInviteImpl(email: 'email@address.com', about: 'I am OK'))

        then:
        conditions.eventually {
            communityUserGormService.count() == old(communityUserGormService.count()) + 1
        }

        cleanup:
        List<String> ids = communityUserGormService.findAll()*.id
        for (String id : ids ) {
            communityUserGormService.delete(id)
        }
    }

}