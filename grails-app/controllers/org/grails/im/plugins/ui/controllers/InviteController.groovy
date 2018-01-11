package org.grails.im.plugins.ui.controllers

import groovy.transform.CompileStatic
import org.grails.im.entities.CommunityUser
import org.grails.im.entities.RequestInvite
import org.grails.im.entities.RequestInviteStatus
import org.grails.im.plugins.repository.CommunityUserRepository
import org.grails.im.plugins.ui.Pagination
import org.grails.im.plugins.ui.PaginationImpl
import org.grails.im.usecase.ChangeInvitationStatusUseCaseService
import org.grails.im.usecase.RequestInviteUseCaseService

@CompileStatic
class InviteController {
    static responseFormats = ['html']

    static allowedMethods = [
            index  : 'GET',
            request: 'POST',
            approve: 'POST',
            reject : 'POST',
    ]

    RequestInviteUseCaseService requestInviteUseCaseService
    ChangeInvitationStatusUseCaseService changeInvitationStatusUseCaseService
    CommunityUserRepository communityUserRepository

    def index() {
        int max = Math.min(params.int('max') ?: 10, 100)
        int offset = params.int('offset') ?: 0
        List<CommunityUser> communityUserList = communityUserRepository.findAll(max, offset)
        int total = communityUserRepository.count()
        Pagination pagination = new PaginationImpl(total: total, offset: offset, max: max)
        [communityUserList: communityUserList, pagination: pagination]
    }

    def request(RequestInviteCommand cmd) {
        if ( cmd.hasErrors() ) {
            render view: '/index'
            return
        }
        requestInviteUseCaseService.request(cmd as RequestInvite)
        [:]
    }

    def approve(ApproveCommand cmd) {
        if ( cmd.hasErrors() ) {
            render status: 422
            return
        }
        changeInvitationStatusUseCaseService.changeStatus(cmd.email, RequestInviteStatus.APPROVED)
        redirect action: 'index'
    }

    def reject(RejectCommand cmd) {
        if ( cmd.hasErrors() ) {
            render status: 422
            return
        }
        changeInvitationStatusUseCaseService.changeStatus(cmd.email, RequestInviteStatus.REJECTED)
        redirect action: 'index'
    }
}