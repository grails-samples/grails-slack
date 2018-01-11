package org.grails.im.plugins.ui.controllers.admin

import groovy.transform.CompileStatic
import org.grails.im.entities.CommunityUser
import org.grails.im.entities.RequestInviteStatus
import org.grails.im.plugins.repository.CommunityUserRepository
import org.grails.im.plugins.ui.Pagination
import org.grails.im.plugins.ui.PaginationImpl
import org.grails.im.usecase.ChangeInvitationStatusUseCase

@CompileStatic
class AdminController {
    static responseFormats = ['html']

    static allowedMethods = [
            index  : 'GET',
            approve: 'POST',
            reject : 'POST',
    ]

    CommunityUserRepository communityUserRepository
    ChangeInvitationStatusUseCase changeInvitationStatusUseCase

    def index() {
        int max = Math.min(params.int('max') ?: 10, 100)
        int offset = params.int('offset') ?: 0
        List<CommunityUser> communityUserList = communityUserRepository.findAll(max, offset)
        int total = communityUserRepository.count()
        Pagination pagination = new PaginationImpl(total: total, offset: offset, max: max)
        [communityUserList: communityUserList, pagination: pagination]
    }

    def approve(ApproveCommand cmd) {
        if ( cmd.hasErrors() ) {
            render status: 422
            return
        }
        changeInvitationStatusUseCase.changeStatus(cmd.email, RequestInviteStatus.APPROVED)
        redirect action: 'index'
    }

    def reject(RejectCommand cmd) {
        if ( cmd.hasErrors() ) {
            render status: 422
            return
        }
        changeInvitationStatusUseCase.changeStatus(cmd.email, RequestInviteStatus.REJECTED)
        redirect action: 'index'
    }
}