package bitterbidder



import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(ListingService)
@Mock([Customer, Listing])
class ListingServiceTests {

    Listing listingUnderTest;
    @Before
    void setUp() {
        listingUnderTest = TestUtility.getValidListing();
        Assume.assumeTrue(listingUnderTest.id==null)
    }

    @Test
    void test_Create_WhenListingIsValid_ListingIsSaved(){
        //arrange
        def service = new ListingService();
        //act
        def saved = service.Create(listingUnderTest);
       // assert
        assert listingUnderTest.id
        assert !saved.hasErrors()
    }

    @Test
    void test_Create_WhenListingIsInvalid_ListingIsNotSaved() {
        //arrange
        def service = new ListingService();
        listingUnderTest.startingPrice = null;
        //act
        def saved = service.Create(listingUnderTest);
        assertNull listingUnderTest.id;
        //arrange
        assertTrue(saved.errors.hasFieldErrors("startingPrice"))
    }
}
