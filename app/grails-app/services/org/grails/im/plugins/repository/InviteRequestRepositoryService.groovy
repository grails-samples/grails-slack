package org.grails.im.plugins.repository

import grails.events.annotation.Subscriber
import groovy.transform.CompileStatic
import org.grails.im.GrailsImEvents
import org.grails.im.entities.RequestInvite

@CompileStatic
class InviteRequestRepositoryService {

    CommunityUserRepository communityUserRepository

    @Subscriber(GrailsImEvents.NEW_USER)
    void onNewUser(RequestInvite requestInvite) {
        communityUserRepository?.save(requestInvite.email, requestInvite.about)
    }
}
