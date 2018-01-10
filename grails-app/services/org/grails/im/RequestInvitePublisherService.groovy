package org.grails.im

import grails.events.EventPublisher
import groovy.transform.CompileStatic

@CompileStatic
class RequestInvitePublisherService implements EventPublisher {

    void publishEvent(RequestInvite requestInvite) {
        notify('newUser', requestInvite)
    }
}