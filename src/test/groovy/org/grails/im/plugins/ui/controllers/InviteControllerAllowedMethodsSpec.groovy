package org.grails.im.plugins.ui.controllers

import grails.testing.web.controllers.ControllerUnitTest
import org.grails.im.plugins.repository.CommunityUserRepository
import org.grails.im.plugins.ui.controllers.InviteController
import org.grails.im.usecase.ChangeInvitationStatusUseCaseService
import spock.lang.Specification
import spock.lang.Unroll
import static javax.servlet.http.HttpServletResponse.SC_METHOD_NOT_ALLOWED

class InviteControllerAllowedMethodsSpec extends Specification implements ControllerUnitTest<InviteController> {

    @Unroll
    def "test InviteController.request does not accept #method requests"(String method) {
        when:
        request.method = method
        controller.request()

        then:
        response.status == SC_METHOD_NOT_ALLOWED

        where:
        method << ['PATCH', 'DELETE', 'GET', 'PUT']
    }

    def "test InviteController.request accepts POST requests"() {
        when:
        request.method = 'POST'
        controller.request()

        then:
        response.status == 200
    }

    @Unroll
    def "test InviteController.index does not accept #method requests"(String method) {
        when:
        request.method = method
        controller.index()

        then:
        response.status == SC_METHOD_NOT_ALLOWED

        where:
        method << ['PATCH', 'DELETE', 'POST', 'PUT']
    }

    def "test InviteController.index accepts POST requests"() {
        given:
        controller.communityUserRepository = Mock(CommunityUserRepository)

        when:
        request.method = 'GET'
        controller.index()

        then:
        response.status == 200
    }

    @Unroll
    def "test InviteController.approve does not accept #method requests"(String method) {
        when:
        request.method = method
        controller.approve()

        then:
        response.status == SC_METHOD_NOT_ALLOWED

        where:
        method << ['PATCH', 'DELETE', 'GET', 'PUT']
    }

    def "test InviteController.approve accepts  requests"() {
        given:
        controller.changeInvitationStatusUseCaseService = Mock(ChangeInvitationStatusUseCaseService)

        when:
        request.method = 'POST'
        controller.approve()

        then:
        response.status == 422
    }

    @Unroll
    def "test InviteController.reject does not accept #method requests"(String method) {
        when:
        request.method = method
        controller.reject()

        then:
        response.status == SC_METHOD_NOT_ALLOWED

        where:
        method << ['PATCH', 'DELETE', 'GET', 'PUT']
    }

    def "test InviteController.reject accepts  requests"() {
        given:
        controller.changeInvitationStatusUseCaseService = Mock(ChangeInvitationStatusUseCaseService)

        when:
        request.method = 'POST'
        controller.reject()

        then:
        response.status == 422
    }

}
