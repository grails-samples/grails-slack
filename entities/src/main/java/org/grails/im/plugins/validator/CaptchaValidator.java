package org.grails.im.plugins.validator;

public interface CaptchaValidator {
    boolean isValid(String text);
}
