package org.grails.im.plugins.validator

class CaptchaValidatorService {

    boolean isValid(String text) {
        text?.trim()?.toLowerCase() == 'grails'
    }
}
