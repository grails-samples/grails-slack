package org.grails.im.plugins.ui.controllers

import grails.testing.web.UrlMappingsUnitTest
import spock.lang.Specification

class UrlMappingsSpec extends Specification implements UrlMappingsUnitTest<UrlMappings> {

    void setup() {
        mockController(InviteController)
    }

    def "test forward mappings"() {
        verifyForwardUrlMapping("/invite/request", controller: 'invite', action: 'request', method: 'POST')
        verifyForwardUrlMapping("/api/slack", controller: 'apiSlack', action: 'callback', method: 'POST')
        verifyForwardUrlMapping("/", view: 'index')
    }
}
