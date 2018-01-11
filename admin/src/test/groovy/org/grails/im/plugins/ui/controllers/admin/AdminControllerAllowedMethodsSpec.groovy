package org.grails.im.plugins.ui.controllers.admin

import grails.testing.web.controllers.ControllerUnitTest
import org.grails.im.plugins.repository.CommunityUserRepository
import org.grails.im.usecase.ChangeInvitationStatusUseCase
import spock.lang.Specification
import spock.lang.Unroll

import static javax.servlet.http.HttpServletResponse.SC_METHOD_NOT_ALLOWED

class AdminControllerAllowedMethodsSpec extends Specification implements ControllerUnitTest<AdminController> {

    @Unroll
    def "test AdminController.index does not accept #method requests"(String method) {
        when:
        request.method = method
        controller.index()

        then:
        response.status == SC_METHOD_NOT_ALLOWED

        where:
        method << ['PATCH', 'DELETE', 'POST', 'PUT']
    }

    def "test AdminController.index accepts POST requests"() {
        given:
        controller.communityUserRepository = Mock(CommunityUserRepository)

        when:
        request.method = 'GET'
        controller.index()

        then:
        response.status == 200
    }

    @Unroll
    def "test AdminController.approve does not accept #method requests"(String method) {
        when:
        request.method = method
        controller.approve()

        then:
        response.status == SC_METHOD_NOT_ALLOWED

        where:
        method << ['PATCH', 'DELETE', 'GET', 'PUT']
    }

    def "test AdminController.approve accepts  requests"() {
        given:
        controller.changeInvitationStatusUseCase = Mock(ChangeInvitationStatusUseCase)

        when:
        request.method = 'POST'
        controller.approve()

        then:
        response.status == 422
    }

    @Unroll
    def "test AdminController.reject does not accept #method requests"(String method) {
        when:
        request.method = method
        controller.reject()

        then:
        response.status == SC_METHOD_NOT_ALLOWED

        where:
        method << ['PATCH', 'DELETE', 'GET', 'PUT']
    }

    def "test AdminController.reject accepts  requests"() {
        given:
        controller.changeInvitationStatusUseCase = Mock(ChangeInvitationStatusUseCase)

        when:
        request.method = 'POST'
        controller.reject()

        then:
        response.status == 422
    }

}
