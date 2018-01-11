package org.grails.im.plugins.slack

import grails.config.Config
import grails.core.support.GrailsConfigurationAware
import grails.events.annotation.Subscriber
import grails.plugins.rest.client.RestBuilder
import grails.plugins.rest.client.RestResponse
import groovy.transform.CompileDynamic
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.grails.im.GrailsImEvents
import org.grails.im.entities.UserApproved

@Slf4j
@CompileStatic
class SlackInviteUserSubscriberService implements GrailsConfigurationAware {

    String apiUrl
    String token
    String channel

    @Override
    void setConfiguration(Config co) {
        apiUrl = co.getProperty('slack.apiUrl', String)
        token = co.getProperty('slack.token', String)
        channel = co.getProperty('slack.channel', String)
    }

    @CompileDynamic
    void invite(String email) {
        String url = "${apiUrl}/users.admin.invite?token={token}&channel={channel}&email={email}"
        Map<String, Object> params = [token: token,
                      channel: channel,
                      email: email
        ] as Map<String, Object>

        RestResponse response = new RestBuilder().get(url) {
            urlVariables params
        }

        if (!response.json.ok) {
            log.error "There was an error inviting the user: ${response.json.error}"
        }
    }

    @Subscriber(GrailsImEvents.APPROVED_USER)
    void onUserApproved(UserApproved userApproved) {
        invite(userApproved.email)
    }
}
