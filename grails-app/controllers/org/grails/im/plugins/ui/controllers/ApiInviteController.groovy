package org.grails.im.plugins.ui.controllers

import groovy.transform.CompileStatic
import org.grails.im.entities.RequestInviteStatus
import org.grails.im.usecase.ChangeInvitationStatusUseCaseService

@CompileStatic
class ApiInviteController {

    ChangeInvitationStatusUseCaseService changeInvitationStatusUseCaseService

    static responseFormats = ['json']

    static allowedMethods = [
            approve: 'PUT',
            reject : 'PUT',
    ]

    def approve(ApproveCommand cmd) {
        if ( cmd.hasErrors() ) {
            render status: 422
            return
        }
        changeInvitationStatusUseCaseService.changeStatus(cmd.email, RequestInviteStatus.APPROVED)
        render status: 200
    }

    def reject(RejectCommand cmd) {
        if ( cmd.hasErrors() ) {
            render status: 422
            return
        }
        changeInvitationStatusUseCaseService.changeStatus(cmd.email, RequestInviteStatus.REJECTED)
        render status: 200
    }
}