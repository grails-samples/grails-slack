package org.grails.im.plugins.ui.controllers.slack

import groovy.json.JsonSlurper
import org.grails.im.entities.RequestInviteStatus
import org.grails.im.usecase.ChangeInvitationStatusUseCaseService

class ApiSlackController {

    ChangeInvitationStatusUseCaseService changeInvitationStatusUseCaseService

    def callback() {
        def json = new JsonSlurper().parse(params.payload.bytes)
        String action = json.actions.first().name
        String email = json.actions.first().value
        String user = json.user.name

//        String emoji = action == 'Approve' ? ':white_check_mark:' : ':x:'
        String emoji = ':x:'
        if (action == 'Approve') {
            changeInvitationStatusUseCaseService.changeStatus(email, RequestInviteStatus.APPROVED)
            emoji = ':white_check_mark:'
        }

        String msg = "${emoji} ${action} executed by _${user}_ for user ${email}"
        render msg
    }
}
