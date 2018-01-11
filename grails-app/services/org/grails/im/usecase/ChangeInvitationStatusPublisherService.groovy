package org.grails.im.usecase

import grails.events.EventPublisher
import groovy.transform.CompileStatic
import org.grails.im.entities.UserApproved
import org.grails.im.entities.UserRejected

@CompileStatic
class ChangeInvitationStatusPublisherService implements EventPublisher {

    void publishApprovedUser(UserApproved userApproved) {
        notify('approvedUser', userApproved)
    }

    void publishRejectedUser(UserRejected userRejected) {
        notify('rejectedUser', userRejected)
    }
}