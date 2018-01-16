package org.grails.im.plugins.slack

import com.stehno.ersatz.ContentType
import com.stehno.ersatz.Encoders
import com.stehno.ersatz.ErsatzServer
import grails.testing.services.ServiceUnitTest
import org.grails.im.entities.RequestInviteImpl
import org.grails.im.entities.UserApprovedImpl
import spock.lang.Specification
import static net.javacrumbs.jsonunit.JsonAssert.assertJsonEquals

class SlackServiceSpec extends Specification implements ServiceUnitTest<SlackService> {

    Closure doWithConfig() {{ config ->
        config.slack.apiUrl = "https://slack.com/api"
        config.slack.token = "x"
        config.slack.legacyToken = "x"
        config.slack.channel = "x"
    }}

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

    void 'generateAttachments produces expected JSON'() {
        given:
        def expected = """
[
   {
      "fallback":"You can not approve users",
      "callback_id":"approver",
      "color":"#3AA3E3",
      "attachment_type":"default",
      "actions":[
         {
            "name":"Approve",
            "text":"Approve",
            "style":"primary",
            "type":"button",
            "value":"me@email.com"
         },
         {
            "name":"Reject",
            "text":"Reject",
            "style":"danger",
            "type":"button",
            "value":"me@email.com"
         }
      ]
   }
]
"""
        when:
        String result = service.generateAttachments('me@email.com')

        then:
        result
        assertJsonEquals(result, expected)
    }
}
