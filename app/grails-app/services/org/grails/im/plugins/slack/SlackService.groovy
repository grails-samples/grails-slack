package org.grails.im.plugins.slack

import grails.config.Config
import grails.core.support.GrailsConfigurationAware
import grails.events.annotation.Subscriber
import grails.plugins.rest.client.RestBuilder
import grails.plugins.rest.client.RestResponse
import grails.web.mapping.LinkGenerator
import groovy.transform.CompileDynamic
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.grails.im.GrailsImEvents
import org.grails.im.entities.RequestInvite
import org.grails.im.entities.UserApproved

@Slf4j
@CompileStatic
class SlackService implements GrailsConfigurationAware {

    String apiUrl
    String token
    String legacyToken
    String channel

    LinkGenerator grailsLinkGenerator

    @Override
    void setConfiguration(Config co) {
        apiUrl = co.getProperty('slack.apiUrl', String)
        token = co.getProperty('slack.token', String)
        legacyToken = co.getProperty('slack.legacyToken', String)
        channel = co.getProperty('slack.channel', String)
    }

    boolean isSlackConfiguredCorrectly() {
        token && channel
    }

    @Subscriber(GrailsImEvents.NEW_USER)
    void onNewUser(RequestInvite requestInvite) {
        send(requestInvite)
    }

    @CompileDynamic
    void send(RequestInvite requestInvite) {
        if (isSlackConfiguredCorrectly()) {
            String callbackUrl = grailsLinkGenerator.link(absolute: true, controller: 'apiSlack', action: 'callback')
            log.debug 'callback url: {}', callbackUrl

            String url = "${apiUrl}/chat.postMessage?token={token}&channel={channel}&text={text}&attachments={attachments}"
            String attachments = """
[{
    "fallback": "You can not approve users",
    "callback_id": "approver",
    "color": "#3AA3E3",
    "attachment_type": "default",
    "response_url": "${callbackUrl}",
    "actions": [
        {
            "name": "Approve",
            "text": "Approve",
            "style": "primary",
            "type": "button",
            "value": "${requestInvite.email}"
        },
        {
            "name": "Reject",
            "text": "Reject",
            "style": "danger",
            "type": "button",
            "value": "${requestInvite.email}"
        }
    ]
}]
"""
            String text = message(requestInvite)

            Map<String, String> params = [
                token      : token,
                channel    : channel,
                text       : text,
                attachments: attachments,
            ]

            RestResponse response = new RestBuilder().get(url) {
                urlVariables params
            }

            if (!response.json.ok) {
                log.error "There was an error posting to the slack channel Approve/Reject buttons: ${response.json.error}"
            }
        } else {
            log.debug 'Slack is not configured correctly. Missing token or channel'
        }
    }

    String message(RequestInvite requestInvite) {
        "*user:* ${requestInvite.email}\n*about:* ${requestInvite.about}"
    }

    @CompileDynamic
    void invite(String email) {
        if (isSlackConfiguredCorrectly()) {
            String url = "${apiUrl}/users.admin.invite?token={token}&channel={channel}&email={email}"
            Map<String, Object> params = [
                token  : legacyToken,
                channel: channel,
                email  : email
            ]

            RestResponse response = new RestBuilder().get(url) {
                urlVariables params
            }

            if (!response.json.ok) {
                log.error "There was an error inviting the user: ${response.json.error}"
            }
        } else {
            log.debug 'Slack is not configured correctly. Missing token or channel'
        }

    }

    @Subscriber(GrailsImEvents.APPROVED_USER)
    void onUserApproved(UserApproved userApproved) {
        invite(userApproved.email)
    }
}

