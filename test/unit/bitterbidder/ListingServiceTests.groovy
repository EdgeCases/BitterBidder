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
        //act
        def saved = service.Create(listingUnderTest);
        assertNull listingUnderTest.id;
        //arrange
        assertTrue(saved.errors.hasFieldErrors("startingPrice"))
    }
}
