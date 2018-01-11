package org.grails.im.usecase

import grails.events.EventPublisher
import groovy.transform.CompileStatic
import org.grails.im.GrailsImEvents
import org.grails.im.entities.UserApproved
import org.grails.im.entities.UserRejected

@CompileStatic
class ChangeInvitationStatusPublisherService implements EventPublisher {

    void publishApprovedUser(UserApproved userApproved) {
        notify(GrailsImEvents.APPROVED_USER, userApproved)
    }

    void publishRejectedUser(UserRejected userRejected) {
        notify(GrailsImEvents.REJECTED_USER, userRejected)
    }
}
