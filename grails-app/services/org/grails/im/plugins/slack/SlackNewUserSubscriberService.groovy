package org.grails.im.plugins.slack

import grails.config.Config
import grails.core.support.GrailsConfigurationAware
import grails.events.annotation.Subscriber
import grails.plugins.rest.client.RestBuilder
import grails.plugins.rest.client.RestResponse
import groovy.transform.CompileDynamic
import groovy.transform.CompileStatic
import org.grails.im.entities.RequestInvite

@CompileStatic
class SlackNewUserSubscriberService {

    SlackApprovalRequestComposerService slackApprovalRequestComposerService

    @Subscriber('newUser')
    void onNewUser(RequestInvite requestInvite) {
        SlackApproveMessage slackApproveMessage = slackApprovalRequestComposerService.compose(requestInvite)
        send(slackApproveMessage)
    }

    void send(SlackApproveMessage slackApproveMessage) {

    }


}