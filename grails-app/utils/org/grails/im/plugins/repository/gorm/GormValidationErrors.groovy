package org.grails.im.plugins.repository.gorm

import org.springframework.context.MessageSource

trait GormValidationErrors {

    String validationMessages(def bean, MessageSource messageSource, Locale locale = Locale.getDefault()) {
        StringBuilder message = new StringBuilder(
                "problem ${bean.id ? 'updating' : 'creating'} ${bean.getClass().simpleName}: $bean")
        for (fieldErrors in bean.errors) {
            for (error in fieldErrors.allErrors) {
                message.append("\n\t").append(messageSource.getMessage(error, locale))
            }
        }
        message.toString()
    }
}
