package bitterbidder

import static org.junit.Assert.*
import org.junit.*
import grails.test.mixin.TestFor
import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin

class BidIntegrationTests {

    final static bidderEmail = "bidder@email.com"
    final static customerPassword = "password"
    final static bidAmount_DoesNotMeetIncrementThreshold = 1.23
    final static bidAmount_MeetsIncrementThreshold = 1.50
    final static sellerEmail = "seller@email.com"
    final static startingPrice = 1.00

    Bid firstBid
    Bid bidUnderTest
    Customer bidder
    Customer seller
    Listing listing

    @Before
    void setUp() {
        bidder = new Customer(
                emailAddress: bidderEmail,
                password: customerPassword
        )

        seller = new Customer(
                emailAddress: sellerEmail,
                password: customerPassword
        )

        listing = new Listing(
                endDateTime: new Date(),
                name: "Listing",
                startingPrice: startingPrice,
                seller: seller
        )

        bidUnderTest = new Bid(
                listing:  listing,
                bidder: bidder,
                amount: 0.00
        )

        bidder.save(flush: true)
        seller.save(flush: true)
        listing.save(flush: true)
    }

    @After
    void tearDown() {
        // Tear down logic here
        listing = null
        seller = null
        bidder = null
    }

    @Test   // B-5: The Bid amount must be at least .50 higher than the previous Bid for the same listing (integration test)
    @Ignore
    void test_Save_WhenAmountIsGreaterThanLastBidThreshold_SavesSuccessfully() {
        //arrange
        bidUnderTest.amount = bidAmount_MeetsIncrementThreshold
        listing.addToBids(bidUnderTest)
        //act
        bidUnderTest.save(flush: true)

        //assert
        assert listing.bids.size() == 1
    }

    @Test   // B-5: The Bid amount must be at least .50 higher than the previous Bid for the same listing (integration test)
    void test_Save_WhenAmountIsLessThanLastBidThreshold_SaveFails() {
        //arrange
        bidUnderTest.amount = bidAmount_DoesNotMeetIncrementThreshold

        //act
        bidUnderTest.save(flush: true)

        //assert
        assert listing.bids == null
    }

    @Test   // B-6: When a Listing is saved, any new Bids added for the listing must be saved (integration test)
    void test_Save_WhenBidAssociatedWithListing_BidIsSaved() {
        //arrange
        def bid1 = new Bid(listing: listing, bidder: bidder, amount: 1.50)
        def bid2 = new Bid(listing: listing, bidder: bidder, amount: 2.00)
        listing.addToBids(bid1)
        listing.addToBids(bid2)

        //act
        listing.save(flush: true)

        //assert
        assert listing.bids.size() == 2
    }}
