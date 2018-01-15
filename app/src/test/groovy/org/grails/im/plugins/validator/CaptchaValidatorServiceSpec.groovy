package org.grails.im.plugins.validator

import grails.testing.services.ServiceUnitTest
import spock.lang.Specification
import spock.lang.Unroll

class CaptchaValidatorServiceSpec extends Specification implements ServiceUnitTest<CaptchaValidatorService> {

    @Unroll
    void "captcha '#value' #description"() {
        expect:
        service.isValid(value) == expectedValue

        where:
        value        | expectedValue
        'Grails'     | true
        '  grails  ' | true
        null         | false
        ''           | false
        'php'        | false
        description = expectedValue ? 'is valid' : 'is not valid'
    }
}
