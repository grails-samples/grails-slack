package org.grails.im.entities;

public interface CommunityUser {
    String getEmail();
    String getAbout();
    RequestInviteStatus getStatus();
}