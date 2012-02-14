package bitterbidder

import static org.junit.Assert.*
import org.junit.*
import grails.test.mixin.TestFor

@TestFor(Bid)
class BidIntegrationTests {

    def bidder
    def bidder2
    def listing

    @Before
    void setUp() {
        bidder = new Customer()
        bidder2 = new Customer()
        listing = new Listing(name: "cool product")
    }

    @After
    void tearDown() {
        // Tear down logic here
    }

    @Test   // B-5
    void test_Save_WhenAmountIsGreaterThanLastBidThreshold_SavesSuccessfully() {
        //arrange
        def firstBid = new Bid(bidder: bidder,amount: 1.5, listing: listing )
        
        //act
        firstBid.save(flush: true)

        //arrange
        def nextBid = new Bid(bidder: bidder2,amount: 2.0, listing: listing )

        //act
        nextBid.save(flush: true)

        //assert
        fail "Not implemented"
        //Bid.fin
    }

    @Test   // B-6
    void test_Save_WhenBidNotAssociatedWithListing_SaveFails() {
        //arrange
        //act
        //assert
        fail "Not implemented"
    }

    @Test   // B-5
    void test_Save_WhenAmountIsLessThanLastBidThreshold_SaveFails() {
        //arrange
        def seller = new Customer(emailAddress: "seller@email.com", password: "password")
        def bidder = new Customer(emailAddress: "bidder@email.com", password: "password")
        def listing = new Listing(endDateTime: new Date(), name: "Listing", startingPrice: 1.23, seller: seller)

        seller.save()
        bidder.save()
        listing.save()

        def bid1 = new Bid(listing: listing, bidder: bidder, amount: 1.20)
        bid1.save()
        def bid2 = new Bid(listing: listing, bidder: bidder, amount: 1.21)
        
        //act
        bid2.save()
        
        //assert
        assert listing.bids.size() == 1
    }
}
