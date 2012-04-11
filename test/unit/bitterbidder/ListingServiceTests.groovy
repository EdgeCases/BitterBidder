package bitterbidder



import grails.test.mixin.*
import org.junit.*
import grails.validation.ValidationException

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(ListingService)
@Mock([Customer, Listing, Bid])
class ListingServiceTests {


    Listing listingUnderTest;
    @Before
    void setUp() {
        listingUnderTest = TestUtility.getValidListing();
        Assume.assumeTrue(listingUnderTest.id==null)
    }

    @Test
    void test_Create_WhenListingIsValid_ListingIsCreated(){
        //arrange
        def service = new ListingService();
        //act
        def saved = service.Create(listingUnderTest);
        // assert
        assert saved.name == "Default"

        assert !saved.hasErrors()
    }

    @Test
    void test_Create_WhenListingIsInvalid_ListingIsNotCreated() {
        //arrange
        def service = new ListingService();
        listingUnderTest.startingPrice = null;
        //act and assert
        shouldFail(ValidationException) {service.Create(listingUnderTest)}
    }

    @Test
    void test_getMinimumBidAmount_WhenListingHasBids_Returns(){
        //arrange
        def service = new ListingService();
        def listing = TestUtility.getValidListingWithBids()
        listing = listing.save()
        Assume.assumeTrue (!listing.hasErrors())
        def maxAmount = listing.bids.max{it->it.amount}.amount

        //act
        def minBidAmount = service.getMinimumBidAmount(listing.id)

        // assert
        Assert.assertTrue(minBidAmount==(maxAmount+Listing.MINIMUM_BID_INCREMENT))
    }

    @Test
    void test_getMinimumBidAmount_WhenListingHasNoBids_ReturnStartingPrice(){
        //arrange
        def service = new ListingService();
        def listing = TestUtility.getValidListing()
        listing = listing.save()
        Assume.assumeTrue (!listing.hasErrors())

        //act
        def minBidAmount = service.getMinimumBidAmount(listing.id)

        // assert
        Assert.assertTrue("amounts didn't match", minBidAmount == listing.startingPrice)
    }

}


