package org.grails.im.slack

import grails.events.annotation.Subscriber
import groovy.transform.CompileStatic
import org.grails.im.InviteUserGateway
import org.grails.im.UserApproved

@CompileStatic
class SlackInviteUserService implements InviteUserGateway {

    @Override
    void invite(String email) {
        // TODO
    }

    @Subscriber('approvedUser')
    void onUserApproved(UserApproved userApproved) {
        invite(userApproved.email)
    }

}