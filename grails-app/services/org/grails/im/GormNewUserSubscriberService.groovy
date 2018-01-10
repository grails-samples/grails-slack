package org.grails.im

import grails.config.Config
import grails.core.support.GrailsConfigurationAware
import grails.events.annotation.Subscriber
import groovy.transform.CompileStatic

@CompileStatic
class GormNewUserSubscriberService {

    String token
    String channel

    @Subscriber('newUser')
    void onNewUser(String email, String about) {
        // TODO save in db
    }
}