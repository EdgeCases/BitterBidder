package bitterbidder

import grails.test.mixin.*
import org.junit.*
import grails.test.mixin.support.GrailsUnitTestMixin

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
//@TestFor(BidService)
//@TestFor(Bid)
//@Mock([Customer, Listing])

class BidServiceTests {

    final static bidAmount = 2.20
    final static bidderEmail = "bidder@email.com"
    final static customerPassword = "password"
    final static sellerEmail = "seller@email.com"
    final static startingPrice = 1.23

    Bid bidUnderTest
    Customer bidder
    Listing listing
    Customer seller

    @Before
    void setUp() {
        // Setup logic here
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
                listing: listing,
                bidder: bidder,
                amount: bidAmount
        )
    }

    @After
    void tearDown() {
        // Tear down logic here
        bidUnderTest = null
        listing = null
        seller = null
        bidder = null
    }

    @Test   // B-3: Bids are required to have a bidder (Customer) (unit test)
    @Ignore
    void test_Bidder_WhenNull_BidIsInvalid() {
        // arrange
        bidUnderTest.bidder = null

        //act
        bidUnderTest.validate()

        //assert
        assert bidUnderTest.errors.hasFieldErrors("bidder")

        //restore
        bidUnderTest.bidder = bidder
    }
}
