package org.grails.im.plugins.ui.controllers

import grails.testing.web.UrlMappingsUnitTest
import org.grails.im.plugins.ui.controllers.ApiInviteController
import org.grails.im.plugins.ui.controllers.InviteController
import org.grails.im.plugins.ui.controllers.UrlMappings
import spock.lang.Specification

class UrlMappingsSpec extends Specification implements UrlMappingsUnitTest<UrlMappings> {

    void setup() {
        mockController(InviteController)
        mockController(ApiInviteController)
    }

    def "test forward mappings"() {
        verifyForwardUrlMapping("/invite/request", controller: 'invite', action: 'request', method: 'POST')
        verifyForwardUrlMapping("/api/invite/approve", controller: 'apiInvite', action: 'approve', method: 'PUT')
        verifyForwardUrlMapping("/api/invite/reject", controller: 'apiInvite', action: 'reject', method: 'PUT')
        verifyForwardUrlMapping("/", view: 'index')
    }
}
