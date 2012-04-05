package bitterbidder

import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Test

class BidIntegrationTests {

    final static bidderEmail = "bidder11@email.com"
    final static customerPassword = "password"
    final static bidAmount_DoesNotMeetIncrementThreshold = 1.23
    final static bidAmount_MeetsIncrementThreshold = 1.50
    final static sellerEmail = "seller1@email.com"
    final static startingPrice = 1.00

    Bid firstBid
    Bid bidUnderTest
    Customer bidder
    Customer seller
    Customer sellerUnderTest
    Listing listing

    @Before
    void setUp() {
        bidder = new Customer(
                username: 'bidder1',
                emailAddress: bidderEmail,
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

        seller = new Customer(
                username: "customer1",
                emailAddress: sellerEmail,
                password: customerPassword
        )

        sellerUnderTest = new Customer(
                username: "customerUnderTest",
                emailAddress: bidderEmail,
                password: customerPassword
        )

        def customerSpringSecurityService = new Object()
        customerSpringSecurityService.metaClass.encodePassword = {String password -> "ENCODED_PASSWORD"}

        seller.springSecurityService = customerSpringSecurityService

        def customerUnderTestSpringSecurityService = new Object()
        customerUnderTestSpringSecurityService.metaClass.encodePassword = {String password -> "ENCODED_PASSWORD"}

        sellerUnderTest.springSecurityService = customerUnderTestSpringSecurityService

//        bidder.save(flush: true)
//        seller.save(flush: true)
//        listing.save(flush: true)
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
    }
    
    @Test
    void test_Bids_WhenMoreThanTenBids_GetLastTenNamedQueryReturnsMostRecentTenBids() {

        //set up a seller
        seller.save(flush: true)
        assert !seller.hasErrors()
        assert seller.validate()

        //set up a bidder
        bidder.save(flush: true)
        assert !bidder.hasErrors()
        assert bidder.validate()

        //set up the listing
        listing.seller = seller
        listing.save(flush: true)

        //arrange (make > 10 bids and apply them to the listing)
        listing.addToBids(new Bid(listing: listing, bidder: bidder, amount: 1.50))
        listing.save(flush: true)
        listing.addToBids(new Bid(listing: listing, bidder: bidder, amount: 2.50))
        listing.save(flush: true)
        listing.addToBids(new Bid(listing: listing, bidder: bidder, amount: 3.50))
        listing.save(flush: true)
        listing.addToBids(new Bid(listing: listing, bidder: bidder, amount: 4.50))
        listing.save(flush: true)
        listing.addToBids(new Bid(listing: listing, bidder: bidder, amount: 5.50))
        listing.save(flush: true)
        listing.addToBids(new Bid(listing: listing, bidder: bidder, amount: 6.50))
        listing.save(flush: true)
        listing.addToBids(new Bid(listing: listing, bidder: bidder, amount: 7.50))
        listing.save(flush: true)
        listing.addToBids(new Bid(listing: listing, bidder: bidder, amount: 8.50))
        listing.save(flush: true)
        listing.addToBids(new Bid(listing: listing, bidder: bidder, amount: 9.50))
        listing.save(flush: true)
        listing.addToBids(new Bid(listing: listing, bidder: bidder, amount: 10.50))
        listing.save(flush: true)
        listing.addToBids(new Bid(listing: listing, bidder: bidder, amount: 11.50))
        listing.save(flush: true)
        listing.addToBids(new Bid(listing: listing, bidder: bidder, amount: 12.50))
        listing.save(flush: true)

//        def amt = 1
//        for(i in 0..9) {
//
//            listing.addToBids(new Bid(listing: listing, bidder: bidder, amount: amt))
//            listing.save(flush: true)
//            amt += 1.50
//        }
        
        //act
        assert !listing.hasErrors()
        assert 12 == listing.bids.size()

        def latest = Bid.getLastTen(listing.id).list()

        //assert
        assert 10 == latest.size()
        assert 12.50 == latest[0].amount
        assert 3.50 == latest[9].amount
    }
}
