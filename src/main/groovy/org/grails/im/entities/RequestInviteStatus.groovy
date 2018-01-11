package org.grails.im.entities

import groovy.transform.CompileStatic

@CompileStatic
enum RequestInviteStatus {
    PENDING, REJECTED, APPROVED
}