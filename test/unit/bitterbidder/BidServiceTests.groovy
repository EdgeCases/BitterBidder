package bitterbidder

import grails.test.mixin.*
import org.junit.*
import grails.test.mixin.support.GrailsUnitTestMixin

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */

@TestFor(BidService)
@Mock([Bid, Listing])
class BidServiceTests {

    final static bidAmount = 2.50
    final static bidderEmail = "bidder@email.com"
    final static customerPassword = "password"
    final static sellerEmail = "seller@email.com"
    final static startingPrice = 1.23

    Bid bidUnderTest
    Customer bidder
    Listing listing
    Customer seller
    //BidService bidService

    @Before
    void setUp() {
        // Setup logic here

        bidder = TestUtility.getValidCustomer()

        seller = new Customer(
                emailAddress: sellerEmail,
                password: customerPassword
        )

        listing = TestUtility.getValidListing()

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

    //SRV-3: Create a Grails service method that supports creating a new bid for a listing (unit test)
    @Test
    void test_Create_BidForListingWithValidNewAmount_BidCreated() {

        BidService bidService = new BidService()

        assert null != bidService

        def bid = bidService.createBidForListing(listing, bidder, 200);

        assert null != bid
        assert listing.id == bid.listing.id
    }

    @Test   // B-3: Bids are required to have a bidder (Customer) (unit test)
    @Ignore("")
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

    @Test
    void test_Create_WhenBidIsValid_BidIsSaved(){
        //arrange
        def service = new BidService();

         bidUnderTest.amount = bidUnderTest.listing.startingPrice + 5

        //act
        def saved = service.Create(bidUnderTest);

        // assert
        assert bidUnderTest.id
        assert !saved.hasErrors()
    }
}
