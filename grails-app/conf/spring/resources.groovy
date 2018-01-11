import org.grails.im.plugins.repository.gorm.CommunityUserGormService

// Place your Spring DSL code here
beans = {
    communityUserRepository(CommunityUserGormService) {
        messageSource = ref('messageSource')
    }
}
