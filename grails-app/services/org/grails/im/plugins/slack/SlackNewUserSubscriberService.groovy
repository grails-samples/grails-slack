package org.grails.im.plugins.slack

import grails.events.annotation.Subscriber
import groovy.transform.CompileStatic
import org.grails.im.GrailsImEvents
import org.grails.im.entities.RequestInvite

@CompileStatic
class SlackNewUserSubscriberService {

    SlackApprovalRequestComposerService slackApprovalRequestComposerService

    @Subscriber(GrailsImEvents.NEW_USER)
    void onNewUser(RequestInvite requestInvite) {
        SlackApproveMessage slackApproveMessage = slackApprovalRequestComposerService.compose(requestInvite)
        send(slackApproveMessage)
    }

    void send(SlackApproveMessage slackApproveMessage) {

    }
}
