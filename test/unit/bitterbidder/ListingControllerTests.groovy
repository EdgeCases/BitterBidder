package bitterbidder



import org.junit.*
import grails.test.mixin.*
import sun.security.util.Debug
import groovy.mock.interceptor.MockFor

@TestFor(ListingController)
@Mock([Listing, ListingService, Bid, Customer])
class ListingControllerTests {

    def populateValidParams(params) {
        assert params != null

        def validListing = TestUtility.getValidListing();
        params["dateCreated"] = validListing.dateCreated
        params["description"] = validListing.description
        params["endDateTime"] = validListing.endDateTime
        params["name"] = validListing.name
        params["startingPrice"] = validListing.startingPrice
        params["winner"] = validListing.winner
        params["bids"] = validListing.bids
        params["seller"] = validListing.seller;
    }
    void testIndex() {
        controller.index()
        assert "/listing/list" == response.redirectedUrl
    }

    void test_List_WhenListingEndsInThePast_ListingIsNotReturned() {

        def yesterday = new Date()-1
        populateValidParams(params)
        params["endDateTime"] = yesterday;
        controller.save()
        def model = controller.list()
        assert model.listingInstanceList.size() == 0
        assert model.listingInstanceTotal == 0
    }

    void test_List_WhenListingEndsInTheFuture_ListingIsReturned() {

        def tomorrow = new Date()+1
        populateValidParams(params)
        params["endDateTime"] = tomorrow
        controller.save()
        def model = controller.list()
        assert model.listingInstanceList.size() == 1
        assert model.listingInstanceTotal == 1
    }

    void test_List_WhenNoListingsExist_NoListingsReturned() {
        def model = controller.list()
        assert model.listingInstanceList.size() == 0
        assert model.listingInstanceTotal == 0
    }

    void test_Create_Always_ReturnsNewListing() {

        def model = controller.create()
        assert model.listingInstance!=null;
        assertTrue model.listingInstance.name== null;
    }

    void testSave() {
        controller.save()

        assert model.listingInstance != null
        assert view == '/listing/create'

        response.reset()

        populateValidParams(params)
        def model = controller.save()

        assert response.redirectedUrl == '/listing/show/1'
        assert controller.flash.message != null
        assert Listing.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/listing/list'

        populateValidParams(params)
        def listing = new Listing(params)

        assert listing.save() != null

        params.id = listing.id

        def model = controller.show()

        assert model.listingInstance == listing
    }

    void test_Edit_WhenListingIsFound_ListingCanBeEdited() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/listing/list'

        populateValidParams(params)
        def listing = new Listing(params)

        assert listing.save() != null

        params.id = listing.id

        def model = controller.edit()

        response.reset()

        populateValidParams(params)
        params.name='edited listing'

        controller.update()

        assert Listing.get(listing.id).name == 'edited listing'
        assert response.redirectedUrl == "/listing/show/1"
    }

    void test_Update_WhenRequiredFieldMissing_And_VersionIsOld_ReturnsError() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/listing/list'

        response.reset()


        populateValidParams(params)
        def listing = new Listing(params)

        assert listing.save() != null

        // test invalid parameters in update
        params.id = listing.id
        params.name=null

        controller.update()

        assert view == "/listing/edit"
        assert model.listingInstance != null

        assert model.listingInstance.errors.getFieldError("name")
        listing.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/listing/show/$listing.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        listing.clearErrors()

        populateValidParams(params)
        params.id = listing.id
        params.version = -1
        controller.update()

        assert view == "/listing/edit"
        assert model.listingInstance != null
        assert model.listingInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void test_Delete_WhenListingIsFound_ListingDeletedSuccessfully() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/listing/list'

        response.reset()

        populateValidParams(params)
        def listing = new Listing(params)

        assert listing.save() != null
        assert Listing.count() == 1

        params.id = listing.id

        controller.delete()

        assert Listing.count() == 0
        assert Listing.get(listing.id) == null
        assert response.redirectedUrl == '/listing/list'
    }
}
