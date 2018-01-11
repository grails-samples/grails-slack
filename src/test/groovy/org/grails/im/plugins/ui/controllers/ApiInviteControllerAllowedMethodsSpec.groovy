package org.grails.im.plugins.ui.controllers

import grails.testing.web.controllers.ControllerUnitTest
import org.grails.im.plugins.ui.controllers.ApiInviteController
import org.grails.im.usecase.ChangeInvitationStatusUseCaseService
import spock.lang.Specification
import spock.lang.Unroll

import static javax.servlet.http.HttpServletResponse.SC_METHOD_NOT_ALLOWED

class ApiInviteControllerAllowedMethodsSpec extends Specification implements ControllerUnitTest<ApiInviteController> {

    @Unroll
    def "test ApiInviteController.approve does not accept #method requests"(String method) {
        when:
        request.method = method
        controller.approve()

        then:
        response.status == SC_METHOD_NOT_ALLOWED

        where:
        method << ['PATCH', 'DELETE', 'GET', 'POST']
    }

    def "test ApiInviteController.approve accepts PUT requests"() {
        given:
        controller.changeInvitationStatusUseCaseService = Mock(ChangeInvitationStatusUseCaseService)

        when:
        request.method = 'PUT'
        controller.approve()

        then:
        response.status == 422
    }

    @Unroll
    def "test ApiInviteController.reject does not accept #method requests"(String method) {
        when:
        request.method = method
        controller.reject()

        then:
        response.status == SC_METHOD_NOT_ALLOWED

        where:
        method << ['PATCH', 'DELETE', 'POST', 'GET']
    }

    def "test ApiInviteController.reject accepts PUT requests"() {
        given:
        controller.changeInvitationStatusUseCaseService = Mock(ChangeInvitationStatusUseCaseService)

        when:
        request.method = 'PUT'
        controller.reject()

        then:
        response.status == 422
    }

}
