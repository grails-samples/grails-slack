package org.grails.im

import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

class RequestInviteCommandSpec extends Specification {

    @Subject
    @Shared
    RequestInviteCommand cmd = new RequestInviteCommand()


    @Unroll
    void "email '#value' #description"(String value, boolean expected, String expectedErrorCode, String description) {
        when:
        cmd.email = value

        then:
        expected == cmd.validate(['email'])
        cmd.errors['email']?.code == expectedErrorCode

        where:
        value                  | expected | expectedErrorCode
        null                   |  false   | 'nullable'
        ''                     |  false   | 'blank'
        'contact@gmail.com'    |  true    | null
        'contact'              |  false   | 'email.invalid'
        description = expected ? 'is not valid' : 'is valid'
    }


    @Unroll
    void "about '#value' #description"(String value, boolean expected, String expectedErrorCode, String description) {
        when:
        cmd.about = value

        then:
        expected == cmd.validate(['about'])
        cmd.errors['about']?.code == expectedErrorCode

        where:
        value                  | expected | expectedErrorCode
        null                   |  false   | 'nullable'
        ''                     |  false   | 'blank'
        'I am OK'              |  true    | null
        description = expected ? 'is not valid' : 'is valid'
    }

    void "as RequestInvite"() {
        given:
        RequestInviteCommand cmd = new RequestInviteCommand(email: 'email@address.com', about: 'I am OK')

        when:
        RequestInvite requestInvite = cmd as RequestInvite

        then:
        requestInvite
        requestInvite.email == 'email@address.com'
        requestInvite.about == 'I am OK'
    }
}
