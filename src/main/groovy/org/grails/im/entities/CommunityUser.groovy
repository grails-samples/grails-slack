package org.grails.im.entities

import groovy.transform.CompileStatic

@CompileStatic
interface CommunityUser {
    String getEmail()
    String getAbout()
    RequestInviteStatus getStatus()
}