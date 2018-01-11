package org.grails.im.plugins.ui.controllers

import groovy.transform.CompileStatic
import org.grails.im.entities.RequestInvite
import org.grails.im.usecase.RequestInviteUseCaseService

@CompileStatic
class InviteController {
    static responseFormats = ['html']

    static allowedMethods = [
            request: 'POST',
    ]

    RequestInviteUseCaseService requestInviteUseCaseService


    def request(RequestInviteCommand cmd) {
        if ( cmd.hasErrors() ) {
            render view: '/index'
            return
        }
        requestInviteUseCaseService.request(cmd as RequestInvite)
        [:]
    }
}