package org.grails.im

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification
import spock.lang.Unroll

class CommunityUserGormEntitySpec extends Specification implements DomainUnitTest<CommunityUserGormEntity> {

    @Unroll
    void "email '#value' #description"(String value, boolean expected, String expectedErrorCode, String description) {
        when:
        domain.email = value

        then:
        expected == domain.validate(['email'])
        domain.errors['email']?.code == expectedErrorCode

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
        domain.about = value

        then:
        expected == domain.validate(['about'])
        domain.errors['about']?.code == expectedErrorCode

        where:
        value                  | expected | expectedErrorCode
        null                   |  false   | 'nullable'
        ''                     |  false   | 'blank'
        'I am OK'              |  true    | null
        description = expected ? 'is not valid' : 'is valid'
    }
}
