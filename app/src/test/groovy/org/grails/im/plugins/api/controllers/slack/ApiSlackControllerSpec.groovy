package org.grails.im.plugins.api.controllers.slack

import grails.testing.web.controllers.ControllerUnitTest
import org.grails.im.entities.RequestInviteStatus
import org.grails.im.usecase.ChangeInvitationStatusUseCaseService
import spock.lang.Specification

class ApiSlackControllerSpec extends Specification implements ControllerUnitTest<ApiSlackController> {

    void 'the params validation fails'() {
        when: 'calling the controller'
        controller.callback()

        then: 'the params validation fails'
        response.json.error != null
        response.status == 422
    }

    void 'the authentication token is not from slack'() {
        given: 'a wrong slack token'
        params.payload = '{"token": "xxxx"}'

        when: 'calling the controller'
        controller.callback()

        then: 'the request is not authorized'
        response.json.error != null
        response.status == 401
    }

    void 'the user request is approved'() {
        given: 'valid params request'
        controller.slackToken = 'xxxx'
        params.payload = '{"token": "xxxx", "actions": [{"name":"Approve", "value":"john.doe@example.com"}], "user":{"name":"Iván"}, "original_message":{"text":"Original user message asking for approval"}}'

        and: 'a mock for the collaborator service'
        controller.changeInvitationStatusUseCaseService = Mock(ChangeInvitationStatusUseCaseService)

        when: 'calling the controller'
        controller.callback()

        then: 'the request is approved'
        1 * controller.changeInvitationStatusUseCaseService.changeStatus('john.doe@example.com', RequestInviteStatus.APPROVED)

        and:
        response.text == ':white_check_mark: Approve executed by _Iván_:\nOriginal user message asking for approval'
    }

    void 'the user request is rejected'() {
        given: 'valid params request'
        controller.slackToken = 'xxxx'
        params.payload = '{"token": "xxxx", "actions": [{"name":"Reject", "value":"john.doe@example.com"}], "user":{"name":"Iván"}}'

        and: 'a mock for the collaborator service'
        controller.changeInvitationStatusUseCaseService = Mock(ChangeInvitationStatusUseCaseService)

        when: 'calling the controller'
        controller.callback()

        then: 'the request is approved'
        0 * controller.changeInvitationStatusUseCaseService.changeStatus(*_)

        and:
        response.text == ':x: Reject executed by _Iván_ for user john.doe@example.com'
    }
}
