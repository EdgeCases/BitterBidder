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

    @Ignore("Can't figure out how to mock GORM")
    void testList() {

        //populateValidParams(params)
        //controller.save()
        def listings = new ArrayList<Listing>()
        listings.add(TestUtility.validListing)

        Listing.metaClass.static.findListingsInTheFuture ={return listings}
        def model = controller.list()
        assert model.listingInstanceList.size() == 0
        assert model.listingInstanceTotal == 0
    }

    void testCreate() {

        def model = controller.create()

        assert model.listingInstance != null
    }

    void testSave() {
        controller.save()

        assert model.listingInstance != null
        assert view == '/listing/create'

        response.reset()

        populateValidParams(params)
        controller.save()

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

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/listing/list'


        populateValidParams(params)
        def listing = new Listing(params)

        def saved = listing.save();

        assert listing.save() != null

        params.id = listing.id

        def model = controller.edit()

        assert model.listingInstance == listing
    }

    void testUpdate() {
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

    void testDelete() {
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
