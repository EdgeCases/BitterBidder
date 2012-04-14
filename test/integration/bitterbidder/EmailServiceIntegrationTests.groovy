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
    void test_WhenMessageInQueue_EmailIsSentToWinner() {

        def listing = TestUtility.getValidListingWithBids();
        listing.winningPrice = listing?.bids?.max {it->it.dateCreated}.amount

        def listingxml = (listing as XML).toString()
        def expected = EmailMessageHelper.MakeListingWinnerMessage(listingxml)

        emailService.onMessage(listingxml)
        def received = greenMail.getReceivedMessages()[0]
        assertEquals(expected, GreenMailUtil.getBody(received))
        assertEquals('bitterbidderdev@gmail.com', GreenMailUtil.getAddressList(received.from))
        assertEquals('You are a winner!', received.subject)
    }

    @Test
    void test_WhenMessageInQueue_EmailIsSentToSeller() {

        def listing = TestUtility.getValidListingWithBids();
        listing.winningPrice = listing?.bids?.max {it->it.dateCreated}.amount

        def listingxml = (listing as XML).toString()
        def expected = EmailMessageHelper.MakeListingSellerMessage(listingxml)

        emailService.onMessage(listingxml)
        def received = greenMail.getReceivedMessages()[1]
        assertEquals(expected, GreenMailUtil.getBody(received))
        assertEquals('bitterbidderdev@gmail.com', GreenMailUtil.getAddressList(received.from))
        assertEquals('Your Item was Sold!', received.subject)

    }
}