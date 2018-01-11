package org.grails.im.plugins.slack

import com.stehno.ersatz.ContentType
import com.stehno.ersatz.Encoders
import com.stehno.ersatz.ErsatzServer
import grails.testing.services.ServiceUnitTest
import org.grails.im.entities.UserApprovedImpl
import spock.lang.Specification

class SlackInviteUserSubscriberServiceSpec extends Specification implements ServiceUnitTest<SlackInviteUserSubscriberService> {

    void 'invite a user'() {
        given: 'a mocked remote server'
            ErsatzServer ersatz = new ErsatzServer()
            ersatz.expectations {
                get('/users.admin.invite') {
                    query('email', email)
                    called(1)
                    responder {
                        code(200)
                        encoder(ContentType.APPLICATION_JSON, Map, Encoders.json)
                        content([ok: true], ContentType.APPLICATION_JSON)
                    }
                }
            }
            service.apiUrl = ersatz.httpUrl

        when: 'inviting the user'
            service.onUserApproved(new UserApprovedImpl(email: email))

        then:
            ersatz.verify()

        cleanup:
            ersatz.stop()

        where:
            email = 'john.doe@example.com'
    }

    void 'there was an error trying to invite a user'() {
        given: 'a mocked remote server'
            ErsatzServer ersatz = new ErsatzServer()
            ersatz.expectations {
                get('/users.admin.invite') {
                    query('email', email)
                    called(1)
                    responder {
                        code(200)
                        encoder(ContentType.APPLICATION_JSON, Map, Encoders.json)
                        content([ok: false, error: 'error message'], ContentType.APPLICATION_JSON)
                    }
                }
            }
            service.apiUrl = ersatz.httpUrl

        when: 'trying to invite the user'
            service.onUserApproved(new UserApprovedImpl(email: email))

        then:
            ersatz.verify()

        cleanup:
            ersatz.stop()

        where:
            email = 'john.doe@example.com'
    }
}
