package org.grails.im.slack

import grails.config.Config
import grails.core.support.GrailsConfigurationAware
import grails.events.annotation.Subscriber
import groovy.transform.CompileStatic

@CompileStatic
class SlackNewUserSubscriberService implements GrailsConfigurationAware {

    String token
    String channel

    @Subscriber('newUser')
    void onNewUser(String email, String about) {
        // TODO notify Skype
    }

    @Override
    void setConfiguration(Config co) {
        token = co.getProperty('slack.token', String)
        channel = co.getProperty('slack.channel', String)
    }
}