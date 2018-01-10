package org.grails.im

import grails.events.EventPublisher
import groovy.transform.CompileStatic

@CompileStatic
class RequestInviteUseCaseService {
    RequestInvitePublisherService requestInvitePublisherService

    void request(RequestInvite requestInvite) {
        requestInvitePublisherService.publishEvent(requestInvite)
    }
}