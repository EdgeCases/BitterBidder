package bitterbidder

import static org.junit.Assert.*
import org.junit.*

class BidIntegrationTests {

    @Before
    void setUp() {
        // Setup logic here
    }

    @After
    void tearDown() {
        // Tear down logic here
    }

    @Test   // B-5
    void test_Save_WhenAmountIsGreaterThanLastBidThreshold_SavesSuccessfully() {
        //arrange
        //act
        //assert
        fail "Not implemented"
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
        def seller = new Customer(emailAddress: "seller@email.com", password: "password")
        seller.save()
        def listing = new Listing(endDateTime: new Date(), name: "Listing", startingPrice: 1.23, seller: seller)
        def bidder = new Customer(emailAddress: "bidder@email.com", password: "password")
        bidder.save()
        def bid1 = new Bid(listing: listing, bidder: bidder, amount: 1.20)
        listing.save()

        def bid2 = new Bid(listing: listing, bidder: bidder, amount: 1.21)
        bid2.save()

        def customer = new Customer()
    }
}
