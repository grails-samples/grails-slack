package org.grails.im

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class CommunityUserGormEntity {
    String email
    String about

    static constraints = {
        email nullable: false, blank: false, email: true
        about nullable: false, blank: false
    }

    static mapping = {
        table 'communityuser'
        about type: 'text'
    }

}