package org.grails.im

import grails.events.EventPublisher
import groovy.transform.CompileStatic

@CompileStatic
class ChangeInvitationStatusPublisherService implements EventPublisher {

    void publishApprovedUser(UserApproved userApproved) {
        notify('approvedUser', userApproved)
    }

    void publishRejectedUser(UserRejected userRejected) {
        notify('rejectedUser', userRejected)
    }
}