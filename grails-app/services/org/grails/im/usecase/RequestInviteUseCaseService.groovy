package org.grails.im.usecase

import groovy.transform.CompileStatic
import org.grails.im.entities.RequestInvite
import org.grails.im.plugins.repository.CommunityUserRepository

@CompileStatic
class RequestInviteUseCaseService {
    RequestInvitePublisherService requestInvitePublisherService

    CommunityUserRepository communityUserRepository

    void request(RequestInvite requestInvite) {
        if ( communityUserRepository.find(requestInvite.email) == null ) {
            requestInvitePublisherService.publishEvent(requestInvite)
        }

    }
}