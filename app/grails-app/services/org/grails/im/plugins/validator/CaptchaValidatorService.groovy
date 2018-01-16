package org.grails.im.plugins.validator

import grails.config.Config
import grails.core.support.GrailsConfigurationAware
import groovy.transform.CompileStatic

@CompileStatic
class CaptchaValidatorService implements CaptchaValidator, GrailsConfigurationAware {

    String captcha

    @Override
    void setConfiguration(Config co) {
        captcha = co.getProperty('requestinvite.captcha',String)
    }

    @Override
    boolean isValid(String text) {
        text?.trim()?.toLowerCase() == captcha
    }
}
