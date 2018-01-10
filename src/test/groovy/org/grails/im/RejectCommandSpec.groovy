package org.grails.im

import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

class RejectCommandSpec extends Specification {
    @Subject
    @Shared
    RejectCommand cmd = new RejectCommand()


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
}
