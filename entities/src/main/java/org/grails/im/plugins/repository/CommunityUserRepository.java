package org.grails.im.plugins.repository;

import org.grails.im.entities.RequestInviteStatus;
import org.grails.im.entities.CommunityUser;

import java.io.Serializable;
import java.util.List;

public interface CommunityUserRepository {
    List<CommunityUser> findAll(Integer max, Integer offset);
    CommunityUser save(Serializable email, String about);
    CommunityUser find(Serializable email);
    void update(Serializable email, RequestInviteStatus status);
    void delete(Serializable id);
    int count();
}