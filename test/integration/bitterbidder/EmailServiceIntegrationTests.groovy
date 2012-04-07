package bitterbidder

import static org.junit.Assert.*
import org.junit.*
import com.icegreen.greenmail.util.GreenMailUtil

class EmailServiceIntegrationTests {

    @Before
    void setUp() {
        // Setup logic here
    }

    @After
    void tearDown() {
        greenMail.deleteAllMessages()
    }

    def mailService
    def greenMail

    @Test
    void testSendMail() {
        Map mail = [message:'A test email from bitter bidder', from:'bitterbidderdev@gmail.com', to:'EdgeCases@groups.live.com', subject:'subject']

        mailService.sendMail {
            to mail.to
            from mail.from
            subject mail.subject
            body mail.message
        }

        assertEquals(1, greenMail.getReceivedMessages().length)

        def message = greenMail.getReceivedMessages()[0]

        assertEquals(mail.message, GreenMailUtil.getBody(message))
        assertEquals(mail.from, GreenMailUtil.getAddressList(message.from))
        assertEquals(mail.subject, message.subject)
    }
}
