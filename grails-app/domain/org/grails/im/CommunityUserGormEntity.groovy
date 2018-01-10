package org.grails.im

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class CommunityUserGormEntity implements Serializable {
    String id
    String about
    RequestInviteStatus status = RequestInviteStatus.PENDING

    static constraints = {
        id nullable: false, blank: false, email: true
        about nullable: false, blank: false
    }

    static mapping = {
        table 'communityuser'
        id generator: 'assigned', column: 'email'
        about type: 'text'
    }

}