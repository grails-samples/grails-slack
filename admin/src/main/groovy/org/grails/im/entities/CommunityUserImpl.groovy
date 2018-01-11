package org.grails.im.entities

import groovy.transform.CompileStatic

@CompileStatic
class CommunityUserImpl implements CommunityUser {
    String email
    String about
    RequestInviteStatus status
}
