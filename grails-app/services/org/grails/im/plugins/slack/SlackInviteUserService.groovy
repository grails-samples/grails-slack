package org.grails.im.plugins.slack

import grails.events.annotation.Subscriber
import groovy.transform.CompileStatic
import org.grails.im.entities.UserApproved

@CompileStatic
class SlackInviteUserService {

    void invite(String email) {
        // TODO
    }

    @Subscriber('approvedUser')
    void onUserApproved(UserApproved userApproved) {
        invite(userApproved.email)
    }

}