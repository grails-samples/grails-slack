package org.grails.im

import grails.events.annotation.Subscriber
import groovy.transform.CompileStatic

@CompileStatic
class GormNewUserSubscriberService {

    CommunityUserGormService communityUserGormService

    @Subscriber('newUser')
    void onNewUser(RequestInvite requestInvite) {
        communityUserGormService.save(requestInvite.email, requestInvite.about)
    }
}