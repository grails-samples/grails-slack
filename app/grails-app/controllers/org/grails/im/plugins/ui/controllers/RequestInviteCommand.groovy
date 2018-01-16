package org.grails.im.plugins.ui.controllers

import grails.compiler.GrailsCompileStatic
import grails.validation.Validateable
import org.grails.im.entities.RequestInvite
import org.grails.im.entities.RequestInviteImpl
import org.grails.im.plugins.validator.CaptchaValidator
import org.grails.im.plugins.validator.CaptchaValidatorService

@GrailsCompileStatic
class RequestInviteCommand implements Validateable, RequestInvite {

    CaptchaValidator captchaValidator

    String email
    String about
    String captcha

    static constraints = {
        email nullable: false, blank: false, email: true
        about nullable: false, blank: false, minSize: 50
        captcha nullable: false, blank: false, validator: { String val, RequestInviteCommand obj ->
            if (!obj.captchaValidator.isValid(val)) {
                return 'wrongValue'
            }
        }
    }

    Object asType(Class clazz) {
        if (clazz == RequestInvite) {
            return new RequestInviteImpl(email: email, about: about)
        }
        else {
            super.asType(clazz)
        }
    }
}