package org.grails.im.plugins.api.controllers.slack

import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

class SlackCallbackCommandSpec extends Specification {

    @Subject
    @Shared
    SlackCallbackCommand cmd = new SlackCallbackCommand()

    void 'valid parameters'() {
        when:
        cmd.payload = '{"token": "xxxx", "actions": [{"name":"Approve", "value":"john.doe@example.com"}], "user":{"name":"Iván"}}'

        then:
        cmd.validate()

        and:
        cmd.fetchToken() == 'xxxx'
        cmd.fetchAction() == 'Approve'
        cmd.fetchEmail() == 'john.doe@example.com'
        cmd.fetchUser() == 'Iván'
    }

    @Unroll
    void "payload '#value' #description"() {
        when:
        cmd.payload = value

        then:
        cmd.validate(['payload']) == expected
        cmd.errors['payload']?.code == expectedErrorCode

        where:
        value    | expected | expectedErrorCode
        null     | false    | 'nullable'
        ''       | false    | 'blank'
        description = expected ? 'is valid' : 'is not valid'
    }
}
