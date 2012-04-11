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

        //emailService = new EmailService();
        //emailService.mailService = mailService
        def listing = TestUtility.getValidListingWithBids();

        def msg = (listing as XML).toString()

        //TODO: fix this after we figure out the serialization problem
        emailService.onMessage("A Test Message");
        assertEquals(1, greenMail.getReceivedMessages().length)

        def message = greenMail.getReceivedMessages()[0]
        def expected =EmailMessageHelper.MakeListingWinnerMessage("A Test Message")

//        def expected =EmailMessageHelper.MakeListingWinnerMessage(listing)
        assertEquals(expected, GreenMailUtil.getBody(message))
        assertEquals('bitterbidderdev@gmail.com', GreenMailUtil.getAddressList(message.from))
        assertEquals('You are a winner!', message.subject)
    }
}
