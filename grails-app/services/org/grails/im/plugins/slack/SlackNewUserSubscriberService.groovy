package org.grails.im.plugins.slack

import grails.config.Config
import grails.core.support.GrailsConfigurationAware
import grails.events.annotation.Subscriber
import grails.plugins.rest.client.RestBuilder
import grails.plugins.rest.client.RestResponse
import groovy.transform.CompileDynamic
import groovy.transform.CompileStatic
import org.grails.im.GrailsImEvents
import groovy.util.logging.Slf4j
import org.grails.im.entities.RequestInvite

@Slf4j
@CompileStatic
class SlackNewUserSubscriberService implements GrailsConfigurationAware {

    String apiUrl
    String token
    String channel
    String callbackUrl

    @Override
    void setConfiguration(Config co) {
        apiUrl = co.getProperty('slack.apiUrl', String)
        token = co.getProperty('slack.token', String)
        channel = co.getProperty('slack.channel', String)
        callbackUrl = co.getProperty('slack.callbackUrl', String)
    }

//    @Subscriber(GrailsImEvents.NEW_USER)
    @Subscriber('newUser')
    void onNewUser(RequestInvite requestInvite) {
        send(requestInvite)
    }

    @CompileDynamic
    void send(RequestInvite requestInvite) {
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

        String text = "*user:* ${requestInvite.email}\n*about:* ${requestInvite.about}"

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
    }
}
