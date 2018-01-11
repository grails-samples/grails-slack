package org.grails.im.usecase;

import org.grails.im.entities.RequestInviteStatus;

public interface ChangeInvitationStatusUseCase {
    void changeStatus(String email, RequestInviteStatus status);
}
