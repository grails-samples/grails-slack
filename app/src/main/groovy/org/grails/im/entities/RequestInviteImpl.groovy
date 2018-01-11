package org.grails.im.entities

import groovy.transform.CompileStatic

@CompileStatic
class RequestInviteImpl implements RequestInvite {
    String email
    String about
}
