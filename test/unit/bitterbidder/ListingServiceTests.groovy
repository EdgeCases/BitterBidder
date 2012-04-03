package bitterbidder



import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(ListingService)
@Mock([Customer, Listing])
class ListingServiceTests {

    def listingUnderTest;
    @Before
    void setUp() {
        listingUnderTest = TestUtility.getValidListing();
    }

    @Test
    void test_Save_WhenListingIsValid_ListingIsSaved(){
        Assume.assumeTrue(listingUnderTest.id==null)
        def service = new ListingService();
        def saved = service.CreateListing(listingUnderTest);
        assert listingUnderTest.id
        assert !saved.hasErrors()
    }

    @Test
    void test_Save_WhenListingIsInvalid_ListingIsNotSaved() {
        //arrange
        
        //act
        //assert
        fail "Not implemented"
    }

    @Test
    void test_Save_WhenListingIsInvalid_ResultContainsErrors() {
        //arrange

        //act
        //assert
        fail "Not implemented"
    }
}
