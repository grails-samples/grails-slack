package org.grails.im.plugins.ui.controllers

import grails.testing.web.controllers.ControllerUnitTest
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
}
