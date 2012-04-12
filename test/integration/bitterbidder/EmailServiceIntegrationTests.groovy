package bitterbidder

import static org.junit.Assert.*
import org.junit.*
import com.icegreen.greenmail.util.GreenMailUtil
import grails.converters.deep.XML


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
    def emailService
    def greenMail

    //Extra credit!
    @Test
    void test_OnMessage_WhenInvoked_EmailIsSent() {

        def listing = TestUtility.getValidListingWithBids();

        //warning: this is a hack to pass the final price to the email helper below
        listing.startingPrice = listing?.bids?.max {it->it.dateCreated}.amount

        def msg = (listing as XML).toString()
        def expected = EmailMessageHelper.MakeListingWinnerMessage(msg)

        Map mail =[message:expected, from:'bitterbidderdev@gmail.com', to:'noone@msse.com',subject: 'You are a winner!']

        mailService.sendMail {
            to mail.to
            from mail.from
            subject mail.subject
            body mail.message
        }
        def received = greenMail.getReceivedMessages()[0]

        assertEquals(expected, GreenMailUtil.getBody(received))
        assertEquals('bitterbidderdev@gmail.com', GreenMailUtil.getAddressList(received.from))
        assertEquals('You are a winner!', received.subject)
    }
}
