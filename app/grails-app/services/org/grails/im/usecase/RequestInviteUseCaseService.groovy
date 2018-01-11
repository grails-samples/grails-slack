package org.grails.im.usecase

import groovy.transform.CompileStatic
import org.grails.im.entities.RequestInvite
import org.grails.im.plugins.repository.CommunityUserRepository

@CompileStatic
class RequestInviteUseCaseService {
    RequestInvitePublisherService requestInvitePublisherService

    CommunityUserRepository communityUserRepository

    void request(RequestInvite requestInvite) {
        if ( !existsRepository() || !communityUserRepository.find(requestInvite.email) ) {
            requestInvitePublisherService.publishEvent(requestInvite)
        }
    }

    boolean existsRepository() {
        communityUserRepository != null
    }
}
