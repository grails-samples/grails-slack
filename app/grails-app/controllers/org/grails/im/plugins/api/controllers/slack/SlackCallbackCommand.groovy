package org.grails.im.plugins.api.controllers.slack

import grails.validation.Validateable
import groovy.json.JsonSlurper

class SlackCallbackCommand implements Validateable {

    String payload
    private Map<String, Object> parsedJson

    static constraints = {
        payload nullable: false, blank: false
    }

    String fetchToken() {
        parseJson()?.token
    }

    String fetchAction() {
        parseJson()?.actions?.first()?.name
    }

    String fetchEmail() {
        parseJson()?.actions?.first()?.value
    }

    String fetchUser() {
        parseJson()?.user?.name
    }

    String fetchOriginalMessage() {
        parseJson()?.original_message?.text
    }

    private Map<String, Object> parseJson() {
        if (!parsedJson) {
            parsedJson = new JsonSlurper().parse(payload.bytes) as Map
        }

        return parsedJson
    }
}
