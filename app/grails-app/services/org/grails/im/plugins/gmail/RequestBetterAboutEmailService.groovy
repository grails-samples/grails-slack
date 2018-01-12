package org.grails.im.plugins.gmail

import grails.config.Config
import grails.core.support.GrailsConfigurationAware
import grails.events.annotation.Subscriber
import groovy.transform.CompileDynamic
import groovy.transform.CompileStatic
import org.grails.im.GrailsImEvents
import org.grails.im.entities.UserRejected
import org.springframework.context.MessageSource

@CompileStatic
class RequestBetterAboutEmailService implements GrailsConfigurationAware {

    String fromAddress
    String replyToAddress
    MessageSource messageSource

    @Subscriber(GrailsImEvents.REJECTED_USER)
    void onUserApproved(UserRejected userRejected) {
        if ( userRejected && isRequestBetterAboutEmail() ) {
            String body = composeBody()
            String subject = messageSource.getMessage('about.email.subject', [] as Object[], 'Provide more info to join our Slack Community', Locale.default)
            sendEmail(userRejected.email, subject, body)
        }
    }

    boolean isRequestBetterAboutEmail() {
        fromAddress
    }

    String composeBody() {
        // TODO
        null
    }

    @CompileDynamic
    @SuppressWarnings('CatchException')
    void sendEmail(String recipientEmail, String emailSubject, String body) {
        log.info "sendWinnerEmail: $recipientEmail"
        try {
            sendMail {
                from fromAddress
                to recipientEmail
                replyTo replyToAddress
                subject emailSubject
                text body
            }
        } catch (Exception e) {
            log.error(e.message, e)
        }
    }

    @Override
    void setConfiguration(Config co) {
        fromAddress = co.getProperty('grails.mail.fromAddress')
        replyToAddress = co.getProperty('grails.mail.replyToAddress')
    }
}