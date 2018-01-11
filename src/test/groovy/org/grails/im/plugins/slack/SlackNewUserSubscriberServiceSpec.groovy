package org.grails.im.plugins.slack

import com.stehno.ersatz.ContentType
import com.stehno.ersatz.Encoders
import com.stehno.ersatz.ErsatzServer
import grails.testing.services.ServiceUnitTest
import org.grails.im.entities.RequestInviteImpl
import spock.lang.Specification

class SlackNewUserSubscriberServiceSpec extends Specification implements ServiceUnitTest<SlackNewUserSubscriberService> {

    void 'send request to slack'() {
        given: 'a mocked remote server'
            ErsatzServer ersatz = new ErsatzServer()
            ersatz.expectations {
                get('/chat.postMessage') {
                    called(1)
                    responder {
                        code(200)
                        encoder(ContentType.APPLICATION_JSON, Map, Encoders.json)
                        content([ok: true], ContentType.APPLICATION_JSON)
                    }
                }
            }
            service.apiUrl = ersatz.httpUrl

        when: 'sending the request to approve or reject the user'
            service.onNewUser(new RequestInviteImpl(email: email, about: about))

        then:
            ersatz.verify()

        cleanup:
            ersatz.stop()

        where:
            email = 'john.doe@example.com'
            about = 'bla, bla'
    }

    void 'there was an error trying to send the request to approve the user'() {
        given: 'a mocked remote server'
            ErsatzServer ersatz = new ErsatzServer()
            ersatz.expectations {
                get('/chat.postMessage') {
                    called(1)
                    responder {
                        code(200)
                        encoder(ContentType.APPLICATION_JSON, Map, Encoders.json)
                        content([ok: false, error: 'error message'], ContentType.APPLICATION_JSON)
                    }
                }
            }
            service.apiUrl = ersatz.httpUrl

        when: 'trying to send request to approve or reject the user the user'
            service.onNewUser(new RequestInviteImpl(email: email, about: about))

        then:
            ersatz.verify()

        cleanup:
            ersatz.stop()

        where:
            email = 'john.doe@example.com'
            about = 'bla, bla'
    }
}
