package bitterbidder

import grails.test.mixin.*
import grails.test.mixin.support.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
@TestFor(Bid)
@Mock([Customer, Listing])
class BidTests {//extends GrailsUnitTestCase{

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

    @Test   // B-1: Bids have the following required fields: amount and date/time of bid (unit test)
    void test_Amount_WhenNull_BidIsInvalid() {
        //arrange
        bidUnderTest.amount = null
        mockForConstraintsTests(Bid,[bidUnderTest])
        
        //act
        bidUnderTest.validate()
        
        //assert
        assert 'nullable' == bidUnderTest.errors['amount']

        //restore
        bidUnderTest.amount = bidAmount
    }

    @Test   // B-1: Bids have the following required fields: amount and date/time of bid (unit test)
    void test_DateTime_WhenNull_BidIsInvalid() {
        //arrange
        mockDomain(Customer, [seller, bidder])
        mockDomain(Listing, [listing])
        mockDomain(Bid, [bidUnderTest])

        seller.save()
        bidder.save()
        listing.save()

        //act
        bidUnderTest.save()

        //assert
        assert bidUnderTest.dateCreated != null
    }

    @Test   // B-2: Bids are required to be for a Listing (unit test)
    void test_Listing_WhenNull_BidIsInvalid() {
        //arrange
        def bidUnderTest = new Bid()
        mockForConstraintsTests(Bid,[bidUnderTest])

        //act
        bidUnderTest.validate()

        //assert
        assert 'nullable' == bidUnderTest.errors['listing']
    }

    @Test   // B-2: Bids are required to be for a Listing (unit test)
    void test_Listing_WhenInvalid_BidIsInvalid() {
        // MikeG: do we need this test?  duplication of previous test?
        //arrange
        def seller = new Customer(emailAddress: "seller@email.com", password: "password")
        def bidder = new Customer(emailAddress: "bidder@email.com", password: "password")
        mockDomain Customer, [seller, bidder]
        
        def listingName = """this listing name is just going to be way too long;
                                I mean, it's a ridiculously long description and
                                completely inappropriate for normal use"""

        def invalidListing = new Listing(endDateTime: new Date(), name: listingName, startingPrice: 1.23, seller: seller)
        mockDomain Listing, [invalidListing]

        def bid = new Bid(listing: invalidListing, bidder: bidder, amount: 1.23)
        mockDomain Bid, [bid]

        seller.save()
        bidder.save()
        invalidListing.save()

        //act
        bid.validate()
        bid.save()

        //assert
        assert null != bid.bidder
        assert null == bid.listing.description
    }

    @Test   // B-3: Bids are required to have a bidder (Customer) (unit test)
    void test_Bidder_WhenNull_BidIsInvalid() {
        bidUnderTest.bidder = null
        mockForConstraintsTests(Bid,[bidUnderTest])

        //act
        bidUnderTest.validate()

        //assert
        assert 'nullable' == bidUnderTest.errors['bidder']

        //restore
        bidUnderTest.bidder = bidder
    }
}
