package org.grails.im

import grails.compiler.GrailsCompileStatic
import grails.validation.Validateable

@GrailsCompileStatic
class ApproveCommand implements Validateable {
    String email

    static constraints = {
        email nullable: false, blank: false, email: true
    }
}