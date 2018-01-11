package org.grails.im.plugins.ui.controllers.admin

import grails.testing.web.UrlMappingsUnitTest
import org.grails.im.plugins.ui.controllers.UrlMappings
import spock.lang.Specification

class AdminUrlMappingsSpec extends Specification implements UrlMappingsUnitTest<UrlMappings> {

    void setup() {
        mockController(AdminInviteController)
    }

    def "test forward mappings"() {
        verifyForwardUrlMapping("/admin/index", controller: 'invite', action: 'index', method: 'GET')
        verifyForwardUrlMapping("/admin/approve", controller: 'invite', action: 'approve', method: 'POST')
        verifyForwardUrlMapping("/admin/reject", controller: 'invite', action: 'reject', method: 'POST')
    }
}
