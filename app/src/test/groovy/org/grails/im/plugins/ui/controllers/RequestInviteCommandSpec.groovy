package org.grails.im.plugins.ui.controllers

import org.grails.im.entities.RequestInvite
import org.grails.im.plugins.validator.CaptchaValidator
import org.grails.im.plugins.validator.CaptchaValidatorService
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
        description = expected ? 'is valid' : 'is not valid'
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
        'I am not OK'          |  false   | 'minSize.notmet'
        'a'*50                 |  true    | null
        description = expected ? 'is valid' : 'is not valid'
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

    @Unroll
    void "captcha '#value' #description"() {
        given:
        cmd.captchaValidator = Stub(CaptchaValidator) {
            isValid(value) >> isValidCaptcha
        }

        when:
        cmd.captcha = value

        then:
        expected == cmd.validate(['captcha'])
        cmd.errors['captcha']?.code == expectedErrorCode

        where:
        value    | isValidCaptcha | expected | expectedErrorCode
        'Grails' | true           | true     | null
        null     | false          | false    | 'nullable'
        ''       | false          | false    | 'blank'
        'php'    | false          | false    | 'wrongValue'
        description = expected ? 'is valid' : 'is not valid'
    }
}
