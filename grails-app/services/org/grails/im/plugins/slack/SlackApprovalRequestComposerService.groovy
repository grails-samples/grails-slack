package org.grails.im.plugins.slack

import grails.web.mapping.LinkGenerator
import groovy.transform.CompileStatic
import org.grails.im.entities.RequestInvite

@CompileStatic
class SlackApprovalRequestComposerService {

    LinkGenerator grailsLinkGenerator

    SlackApproveMessage compose(RequestInvite requestInvite) {
        String approveLink = grailsLinkGenerator.link(controller: 'apiInvite', action: 'approve', params: [email: requestInvite.email])
        String rejectLink = grailsLinkGenerator.link(controller: 'apiInvite', action: 'reject', params: [email: requestInvite.email])

        new SlackApproveMessage(approveUrl: approveLink, rejectUrl: rejectLink, message: requestInvite.about)
    }
}