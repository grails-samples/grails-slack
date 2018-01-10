package org.grails.im

import groovy.transform.CompileStatic

@CompileStatic
class CommunityUserController {

    static allowedMethods = [
            index: 'GET',
            requestInvite: 'POST',
            approve: 'POST',
            reject: 'POST',
    ]

    RequestInviteUseCaseService requestInviteUseCaseService
    ChangeInvitationStatusUseCaseService changeInvitationStatusUseCaseService
    CommunityUserGormService communityUserGormService

    def index() {
        int max = Math.min(params.int('max') ?: 10, 100)
        int offset = params.int('offset') ?: 0
        List<CommunityUserGormEntity> communityUserList = communityUserGormService.findAll(max, offset)
        int total = communityUserGormService.count()
        Pagination pagination = new PaginationImpl(total: total, offset: offset, max: max)
        [communityUserList: communityUserList, pagination: pagination]
    }

    def requestInvite(RequestInviteCommand cmd) {
        if ( cmd.hasErrors() ) {
            render status: 422
            return
        }
        requestInviteUseCaseService.request(cmd as RequestInvite)

        redirect action: 'index'
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