package org.grails.im.plugins.ui.controllers

import grails.compiler.GrailsCompileStatic
import grails.validation.Validateable
import org.grails.im.entities.RequestInvite
import org.grails.im.entities.RequestInviteImpl

@GrailsCompileStatic
class RequestInviteCommand implements Validateable, RequestInvite {
    String email
    String about

    static constraints = {
        email nullable: false, blank: false, email: true
        about nullable: false, blank: false, minSize: 50
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