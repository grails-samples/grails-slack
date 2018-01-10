package org.grails.im

import grails.testing.web.controllers.ControllerUnitTest
import spock.lang.Specification
import spock.lang.Unroll
import static javax.servlet.http.HttpServletResponse.SC_METHOD_NOT_ALLOWED
import static javax.servlet.http.HttpServletResponse.SC_OK

class CommunityUserControllerAllowedMethodsSpec extends Specification implements ControllerUnitTest<CommunityUserController> {

    @Unroll
    def "test TestController.requestInvite does not accept #method requests"(String method) {
        when:
        request.method = method
        controller.requestInvite()

        then:
        response.status == SC_METHOD_NOT_ALLOWED

        where:
        method << ['PATCH', 'DELETE', 'GET', 'PUT']
    }

    def "test TestController.requestInvite accepts POST requests"() {
        when:
        request.method = 'POST'
        controller.requestInvite()

        then:
        response.status == 422
    }

    @Unroll
    def "test TestController.index does not accept #method requests"(String method) {
        when:
        request.method = method
        controller.index()

        then:
        response.status == SC_METHOD_NOT_ALLOWED

        where:
        method << ['PATCH', 'DELETE', 'POST', 'PUT']
    }

    def "test TestController.index accepts POST requests"() {
        given:
        controller.communityUserGormService = Mock(CommunityUserGormService)

        when:
        request.method = 'GET'
        controller.index()

        then:
        response.status == 200
    }

    @Unroll
    def "test TestController.approve does not accept #method requests"(String method) {
        when:
        request.method = method
        controller.approve()

        then:
        response.status == SC_METHOD_NOT_ALLOWED

        where:
        method << ['PATCH', 'DELETE', 'GET', 'PUT']
    }

    def "test TestController.approve accepts  requests"() {
        given:
        controller.changeInvitationStatusUseCaseService = Mock(ChangeInvitationStatusUseCaseService)

        when:
        request.method = 'POST'
        controller.approve()

        then:
        response.status == 422
    }

    @Unroll
    def "test TestController.reject does not accept #method requests"(String method) {
        when:
        request.method = method
        controller.reject()

        then:
        response.status == SC_METHOD_NOT_ALLOWED

        where:
        method << ['PATCH', 'DELETE', 'GET', 'PUT']
    }

    def "test TestController.reject accepts  requests"() {
        given:
        controller.changeInvitationStatusUseCaseService = Mock(ChangeInvitationStatusUseCaseService)

        when:
        request.method = 'POST'
        controller.reject()

        then:
        response.status == 422
    }

}
