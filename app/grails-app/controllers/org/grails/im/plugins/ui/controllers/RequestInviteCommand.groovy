package org.grails.im.plugins.ui.controllers

import grails.compiler.GrailsCompileStatic
import grails.validation.Validateable
import org.grails.im.entities.RequestInvite
import org.grails.im.entities.RequestInviteImpl

@GrailsCompileStatic
class RequestInviteCommand implements Validateable, RequestInvite {
    String email
    String about
    String captcha

    static constraints = {
        email nullable: false, blank: false, email: true
        about nullable: false, blank: false, minSize: 50
        captcha nullable: false, blank: false, validator: { String val, RequestInviteCommand obj ->
            if (val && val.trim().toLowerCase() != 'grails') {
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