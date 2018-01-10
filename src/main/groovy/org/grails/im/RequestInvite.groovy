package org.grails.im

import groovy.transform.CompileStatic

@CompileStatic
interface RequestInvite {
    String getEmail()
    String getAbout()

}