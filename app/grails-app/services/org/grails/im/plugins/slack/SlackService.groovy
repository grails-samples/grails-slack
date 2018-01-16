package org.grails.im.plugins.slack

import grails.config.Config
import grails.core.support.GrailsConfigurationAware
import grails.events.annotation.Subscriber
import grails.plugins.rest.client.RestBuilder
import grails.plugins.rest.client.RestResponse
import groovy.json.StreamingJsonBuilder
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

    @Override
    void setConfiguration(Config co) {
        apiUrl = co.getProperty('slack.apiUrl', String)
        token = co.getProperty('slack.token', String)
        legacyToken = co.getProperty('slack.legacyToken', String)
        channel = co.getProperty('slack.channel', String)
    }

    boolean isSlackConfiguredCorrectly() {
        token && token != '${SLACK_OAUTH_TOKEN}' &&
        channel && channel != '${SLACK_CHANNEL}' &&
        legacyToken && legacyToken != '${SLACK_LEGACY_TOKEN}' &&
        apiUrl
    }

    @Subscriber(GrailsImEvents.NEW_USER)
    void onNewUser(RequestInvite requestInvite) {
        send(requestInvite)
    }

    @CompileDynamic
    void send(RequestInvite requestInvite) {
        if (isSlackConfiguredCorrectly()) {
            String attachments = this.generateAttachments(requestInvite.email)
            String text = message(requestInvite)

            Map<String, String> params = [
                token      : token,
                channel    : channel,
                text       : text,
                attachments: attachments,
            ]

            String urlParams = this.generateUrlParamsStringFromMap(params)
            String url = "${apiUrl}/chat.postMessage?${urlParams}"

            RestResponse response = new RestBuilder().get(url) {
                urlVariables params
            }

            if (!response.json.ok) {
                log.error "There was an error posting to the slack channel Approve/Reject buttons: ${response.json.error}"
            }
        } else {
            logSlackWrongConfiguration()
        }
    }

    @CompileDynamic
    String generateAttachments(String email) {
        new StringWriter().with { w ->
            StreamingJsonBuilder builder = new StreamingJsonBuilder(w)
            builder.call(
                [
                    {
                        fallback 'You can not approve users'
                        callback_id 'approver'
                        color '#3AA3E3'
                        attachment_type 'default'
                        actions(
                            [
                                "name" : "Approve",
                                "text" : "Approve",
                                "style": "primary",
                                "type" : "button",
                                "value": "${email}"
                            ],
                            [
                                "name" : "Reject",
                                "text" : "Reject",
                                "style": "danger",
                                "type" : "button",
                                "value": "${email}"
                            ]
                        )
                    }
                ]
            )
            w.toString()
        }.toString()
    }

    String message(RequestInvite requestInvite) {
        "*user:* ${requestInvite.email}\n*about:* ${requestInvite.about}"
    }

    @CompileDynamic
    void invite(String email) {
        if (isSlackConfiguredCorrectly()) {
            Map<String, Object> params = [
                token  : legacyToken,
                channel: channel,
                email  : email
            ]

            String urlParams = this.generateUrlParamsStringFromMap(params)
            String url = "${apiUrl}/users.admin.invite?${urlParams}"

            RestResponse response = new RestBuilder().get(url) {
                urlVariables params
            }

            if (!response.json.ok) {
                log.error "There was an error inviting the user: ${response.json.error}"
            }
        } else {
            logSlackWrongConfiguration()
        }
    }

    void logSlackWrongConfiguration() {
        log.debug 'Slack is not configured correctly. Missing oauth token, legacy token or channel'
    }

    private String generateUrlParamsStringFromMap(Map<String, String> map) {
        return map.keySet().collect { "${it}={${it}}" }.join('&')
    }

    @Subscriber(GrailsImEvents.APPROVED_USER)
    void onUserApproved(UserApproved userApproved) {
        invite(userApproved.email)
    }
}

