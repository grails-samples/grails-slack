package org.grails.im.plugins.repository.gorm

import grails.compiler.GrailsCompileStatic
import org.grails.im.entities.RequestInviteStatus

@GrailsCompileStatic
class CommunityUserGormEntity implements Serializable {
    String id
    String about
    Date dateCreated
    Date lastUpdated
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