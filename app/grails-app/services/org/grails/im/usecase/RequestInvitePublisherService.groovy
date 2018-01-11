package org.grails.im.usecase

import grails.events.EventPublisher
import groovy.transform.CompileStatic
import org.grails.im.GrailsImEvents
import org.grails.im.entities.RequestInvite

@CompileStatic
class RequestInvitePublisherService implements EventPublisher {

    void publishEvent(RequestInvite requestInvite) {
        notify(GrailsImEvents.NEW_USER, requestInvite)
    }
}
