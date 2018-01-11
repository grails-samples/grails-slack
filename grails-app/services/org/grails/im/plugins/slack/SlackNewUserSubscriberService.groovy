package org.grails.im.plugins.slack

import grails.config.Config
import grails.core.support.GrailsConfigurationAware
import grails.events.annotation.Subscriber
import grails.plugins.rest.client.RestBuilder
import grails.plugins.rest.client.RestResponse
import groovy.transform.CompileDynamic
import groovy.transform.CompileStatic

@CompileStatic
class SlackNewUserSubscriberService implements GrailsConfigurationAware {

    String apiUrl
    String token
    String channel

    @CompileDynamic
    @Subscriber('newUser')
    void onNewUser(String email, String about) {
        String url = "${apiUrl}/users.admin.invite?token={token}&channel={channel}&email={email}"
        Map params = [token: token,
                      channel: channel,
                      email: email
        ]

        RestResponse response = new RestBuilder().get(url) {
            urlVariables params
        }

        if (!response.json.ok) {
            log.error "There was an error inviting the user: ${response.json.error}"
        }
    }

    @Override
    void setConfiguration(Config co) {
        apiUrl = co.getProperty('slack.apiUrl', String)
        token = co.getProperty('slack.token', String)
        channel = co.getProperty('slack.channel', String)
    }
}