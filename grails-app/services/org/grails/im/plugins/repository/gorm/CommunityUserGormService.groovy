package org.grails.im.plugins.repository.gorm

import grails.gorm.DetachedCriteria
import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.grails.im.entities.RequestInviteStatus
import org.grails.im.entities.CommunityUser
import org.grails.im.plugins.repository.CommunityUserRepository
import org.springframework.context.MessageSource

@Slf4j
@CompileStatic
class CommunityUserGormService implements CommunityUserRepository, GormValidationErrors {

    MessageSource messageSource

    @Transactional
    @Override
    void update(Serializable emailParam, RequestInviteStatus status) {
        DetachedCriteria<CommunityUserGormEntity> query = CommunityUserGormEntity.where {
            id == emailParam
        }
        query.updateAll(status: status)
    }

    @ReadOnly
    @Override
    List<CommunityUser> findAll(Integer max, Integer offset) {
        DetachedCriteria<CommunityUserGormEntity> query = CommunityUserGormEntity.where {}
        if ( max != null ) {
            query = query.max(max)
        }
        if ( offset != null ) {
            query = query.offset(offset)
        }
        query.sort('dateCreated', 'desc').list().collect { CommunityUserGormEntity gormEntity ->
            CommunityUserGormEntityUtils.of(gormEntity)
        }
    }

    @Transactional
    @Override
    CommunityUser save(Serializable email, String aboutParam) {
        CommunityUserGormEntity gormEntity = new CommunityUserGormEntity()
        gormEntity.with {
            id = email
            about = aboutParam
        }
        if ( !gormEntity.save() ) {
            log.warn '{}', validationMessages(gormEntity, messageSource)
        }
        CommunityUserGormEntityUtils.of(gormEntity)
    }

    @ReadOnly
    @Override
    CommunityUser find(Serializable email) {
        CommunityUserGormEntity gormEntity = CommunityUserGormEntity.where {
            id == email
        }.get()
        CommunityUserGormEntityUtils.of(gormEntity)
    }

    @Transactional
    @Override
    void delete(Serializable email) {
        CommunityUserGormEntity.where {
            id == email
        }.deleteAll()
    }

    @ReadOnly
    @Override
    int count() {
        CommunityUserGormEntity.where {}.count() as int
    }
}
