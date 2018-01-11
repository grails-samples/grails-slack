package org.grails.im.usecase

import grails.testing.mixin.integration.Integration
import org.grails.im.entities.RequestInviteImpl
import org.grails.im.plugins.repository.gorm.CommunityUserGormService
import org.grails.im.usecase.RequestInviteUseCaseService
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
        List<String> ids = communityUserGormService.findAll(null, null)*.email
        for (String id : ids ) {
            communityUserGormService.delete(id)
        }
    }

}