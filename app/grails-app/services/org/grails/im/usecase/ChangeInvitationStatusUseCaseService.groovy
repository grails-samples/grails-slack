package org.grails.im.usecase

import groovy.transform.CompileStatic
import org.grails.im.entities.RequestInviteStatus
import org.grails.im.entities.UserApproved
import org.grails.im.entities.UserApprovedImpl
import org.grails.im.entities.UserRejected
import org.grails.im.entities.UserRejectedImpl
import org.grails.im.plugins.repository.CommunityUserRepository

@CompileStatic
class ChangeInvitationStatusUseCaseService implements ChangeInvitationStatusUseCase {

    CommunityUserRepository communityUserRepository
    ChangeInvitationStatusPublisherService changeInvitationStatusPublisherService

    void changeStatus(String email, RequestInviteStatus status) {
        if (!existsRepository() || communityUserRepository.find(email)) {
            communityUserRepository?.update(email, status)
            if (status == RequestInviteStatus.APPROVED) {
                UserApproved userApproved = new UserApprovedImpl(email: email)
                changeInvitationStatusPublisherService.publishApprovedUser(userApproved)
            } else if (status == RequestInviteStatus.REJECTED) {
                UserRejected userRejected = new UserRejectedImpl(email: email)
                changeInvitationStatusPublisherService.publishRejectedUser(userRejected)
            }
        }
    }

    boolean existsRepository() {
        communityUserRepository != null
    }
}
