package org.grails.im.plugins.ui.controllers

class UrlMappings {

    static mappings = {
        "/invite/request"(controller: 'invite', action: 'request', method: 'POST')
        "/invite/index"(controller: 'invite', action: 'index', method: 'GET')
        "/invite/approve"(controller: 'invite', action: 'approve', method: 'POST')
        "/invite/reject"(controller: 'invite', action: 'reject', method: 'POST')
        "/api/invite/approve"(controller: 'apiInvite', action: 'approve', method: 'PUT')
        "/api/invite/reject"(controller: 'apiInvite', action: 'reject', method: 'PUT')

        "/"(view: '/index')
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
