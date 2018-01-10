package org.grails.im

import grails.compiler.GrailsCompileStatic
import grails.validation.Validateable

@GrailsCompileStatic
class RequestInviteCommand implements Validateable, RequestInvite {
    String email
    String about

    static constraints = {
        email nullable: false, blank: false, email: true
        about nullable: false, blank: false
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