package org.grails.im.plugins.api.controllers.slack

import grails.config.Config
import grails.core.support.GrailsConfigurationAware
import org.grails.im.entities.RequestInviteStatus
import org.grails.im.usecase.ChangeInvitationStatusUseCaseService
import org.springframework.http.HttpStatus

class ApiSlackController implements GrailsConfigurationAware {

    static responseFormats = ['json']

    String slackToken
    ChangeInvitationStatusUseCaseService changeInvitationStatusUseCaseService

    def callback(SlackCallbackCommand cmd) {
        if (!cmd.validate()) {
            response.status = HttpStatus.UNPROCESSABLE_ENTITY.value()
            respond([error: 'Wrong request parameters'])
            return
        }

        if (cmd.fetchToken() != slackToken) {
            response.status = HttpStatus.UNAUTHORIZED.value()
            respond([error: 'You are not Slack'])
            return
        }

        String msg
        if (cmd.fetchAction() == 'Approve') {
            changeInvitationStatusUseCaseService.changeStatus(cmd.fetchEmail(), RequestInviteStatus.APPROVED)
            msg = ":white_check_mark: ${cmd.fetchAction()} executed by _${cmd.fetchUser()}_:\n${cmd.fetchOriginalMessage()}"
        } else {
            msg = ":x: ${cmd.fetchAction()} executed by _${cmd.fetchUser()}_ for user ${cmd.fetchEmail()}"
        }

        render msg
    }

    @Override
    void setConfiguration(Config co) {
        slackToken = co.getProperty('slack.verificationToken', String)
    }
}
