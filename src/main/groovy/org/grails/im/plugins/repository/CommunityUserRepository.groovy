package org.grails.im.plugins.repository

import groovy.transform.CompileStatic
import org.grails.im.entities.RequestInviteStatus
import org.grails.im.entities.CommunityUser

@CompileStatic
interface CommunityUserRepository {
    List<CommunityUser> findAll(Integer max, Integer offset)
    CommunityUser save(Serializable email, String about)
    CommunityUser find(Serializable email)
    void update(Serializable email, RequestInviteStatus status)
    void delete(Serializable id)
    int count()
}