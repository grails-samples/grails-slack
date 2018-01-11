package org.grails.im.plugins.repository.gorm

import groovy.transform.CompileStatic
import org.grails.im.entities.CommunityUser
import org.grails.im.entities.CommunityUserImpl

@CompileStatic
class CommunityUserGormEntityUtils {

    static CommunityUser of(CommunityUserGormEntity gormEntity) {
        if ( gormEntity == null ) {
            return null
        }
        new CommunityUserImpl(email: gormEntity.id, about: gormEntity.about, status: gormEntity.status)

    }

}
