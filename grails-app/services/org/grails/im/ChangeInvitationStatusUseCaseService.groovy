package org.grails.im

import groovy.transform.CompileStatic

@CompileStatic
class ChangeInvitationStatusUseCaseService {

    CommunityUserGormService communityUserGormService

    ChangeInvitationStatusPublisherService changeInvitationStatusPublisherService

    void changeStatus(String email, RequestInviteStatus status) {
        communityUserGormService.update(email, status)

        if ( status == RequestInviteStatus.APPROVED ) {
            UserApproved userApproved = new UserApprovedImpl(email: email)
            changeInvitationStatusPublisherService.publishApprovedUser(userApproved)
        } else if ( status == RequestInviteStatus.REJECTED ) {
            UserRejected userRejected = new UserRejectedImpl(email: email)
            changeInvitationStatusPublisherService.publishRejectedUser(userRejected)
        }
    }

}