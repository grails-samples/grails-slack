package org.grails.im.plugins.ui.controllers

import groovy.transform.CompileDynamic
import groovy.transform.CompileStatic
import org.grails.im.entities.RequestInvite
import org.grails.im.usecase.RequestInviteUseCaseService
import org.springframework.context.MessageSource

@CompileStatic
class InviteController {
    static responseFormats = ['html']

    static allowedMethods = [
        request: 'POST',
    ]

    MessageSource messageSource
    RequestInviteUseCaseService requestInviteUseCaseService

    def request(RequestInviteCommand cmd) {
        if (cmd.hasErrors()) {
            flash.error = this.beanMessage(cmd, messageSource)
            render view: '/index', model: [email: cmd.email, about: cmd.about, captcha: cmd.captcha]
            return
        }
        requestInviteUseCaseService.request(cmd as RequestInvite)
        [:]
    }

    @CompileDynamic
    private List<String> beanMessage(def bean, MessageSource messageSource, Locale locale = Locale.getDefault()) {
        List<String> errorMsgs = []
        for (fieldErrors in bean.errors) {
            for (error in fieldErrors.allErrors) {
                errorMsgs << messageSource.getMessage(error, locale)
            }
        }
        return errorMsgs
    }
}
