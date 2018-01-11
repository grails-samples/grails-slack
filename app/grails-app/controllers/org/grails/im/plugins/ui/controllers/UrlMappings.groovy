package org.grails.im.plugins.ui.controllers

class UrlMappings {

    static mappings = {
        "/invite/request"(controller: 'invite', action: 'request', method: 'POST')
        "/api/slack"(controller: 'apiSlack', action: 'callback', method: 'POST')
        "/"(view: '/index')
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
