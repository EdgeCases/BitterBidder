package bitterbidder

import grails.test.mixin.*
import org.junit.*
import grails.test.mixin.support.GrailsUnitTestMixin
import grails.validation.ValidationException
import org.springframework.test.AssertThrows

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */

@TestFor(BidService)
@Mock([Bid, Listing, ListingService])
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
    def listingService

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
                amount: bidAmount)

         listingService = new ListingService()
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
        bidService.listingService = listingService
        assert null != bidService
        listing.save(validate: false)


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
        service.listingService = listingService
        bidUnderTest.amount = bidUnderTest.listing.startingPrice + 5

        listing.save(validate: false)

        //act
        def saved = service.Create(bidUnderTest);

        // assert
        assert bidUnderTest.id
        assert !saved.hasErrors()
    }

    //C-3: When a listing completes it can no longer accept any more bids.
    // Verify this by testing through the service method created for SRV-3 (unit test).
    @Test
    void test_Create_WhenListingIsEnded_ValidationExceptionIsThrown() {
        BidService bidService = new BidService()
        bidService.listingService = listingService
        assert null != bidService

        listing.endDateTime = new Date()-1;
        listing.save(validate: false)
        shouldFail(ValidationException){ bidService.createBidForListing(listing, bidder, 200)};
    }

    @Test
    void test_Create_WhenBidIsValid_BidIsCreate() {

        BidService bidService = new BidService()
        bidService.listingService = listingService
        assert null != bidService
        listing.save(validate: false)

        assert null != bidService

        bidUnderTest.amount = bidUnderTest.listing.startingPrice + 5

        //listing.endDateTime = new Date()-1;
        //listing.save(validate: false)
        //shouldFail(ValidationException){ bidService.createBidForListing(listing, bidder, 200)};
        def saved = bidService.createBidForListing(bidUnderTest.listing, bidder, (int)bidUnderTest.listing.startingPrice + 5)
        assert !saved.hasErrors()
    }
}
