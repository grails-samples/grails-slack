package org.grails.im.plugins.ui.controllers.admin

class AdminUrlMappings {

    static mappings = {
        "/admin/index"(controller: 'invite', action: 'index', method: 'GET')
        "/admin/approve"(controller: 'invite', action: 'approve', method: 'POST')
        "/admin/reject"(controller: 'invite', action: 'reject', method: 'POST')
    }
}
