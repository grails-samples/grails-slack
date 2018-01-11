package org.grails.im.plugins.slack

import groovy.transform.CompileStatic

@CompileStatic
class SlackApproveMessage {
    String message
    String approveUrl
    String rejectUrl
}
