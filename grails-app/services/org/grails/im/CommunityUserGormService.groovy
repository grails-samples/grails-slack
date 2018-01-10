package org.grails.im

import grails.gorm.DetachedCriteria
import grails.gorm.services.Service
import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional
import groovy.transform.CompileStatic

@CompileStatic
interface CommunityUserDataService {

    @Transactional
    CommunityUserGormEntity save(String id, String about)

    @Transactional
    void delete(Serializable id)

    @ReadOnly
    int count()
}

@CompileStatic
@Service(CommunityUserGormEntity)
abstract class CommunityUserGormService implements CommunityUserDataService {


    @ReadOnly
    List<CommunityUserGormEntity> findAll(Integer max = null, Integer offset = null) {
        DetachedCriteria<CommunityUserGormEntity> query = CommunityUserGormEntity.where {
        }
        if ( max != null ) {
            query = query.max(max)
        }
        if ( offset != null ) {
            query = query.offset(offset)
        }
        query.list()
    }

    @Transactional
    void update(Serializable emailParam, RequestInviteStatus status) {
        DetachedCriteria<CommunityUserGormEntity> query = CommunityUserGormEntity.where {
            id == emailParam
        }
        query.updateAll(status: status)
    }

}
