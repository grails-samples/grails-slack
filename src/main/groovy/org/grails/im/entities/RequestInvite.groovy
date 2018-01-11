package org.grails.im.entities

import groovy.transform.CompileStatic

@CompileStatic
interface RequestInvite {
    String getEmail()
    String getAbout()

}