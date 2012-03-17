package bitterbidder



import org.junit.*
import grails.test.mixin.*

@TestFor(BidController)
@Mock(Bid)
class BidControllerTests {


    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/bid/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.bidInstanceList.size() == 0
        assert model.bidInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.bidInstance != null
    }

    void testCreateWithListingId() {

        populateValidParams(params)
        def listing = new Listing(params)

        def id = listing.id
        def model = controller.create(id)

        assert model.bidInstance != null
        assert model.bidInstance.listing.id == id
    }
    
    void testSave() {
        controller.save()

        assert model.bidInstance != null
        assert view == '/bid/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/bid/show/1'
        assert controller.flash.message != null
        assert Bid.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/bid/list'


        populateValidParams(params)
        def bid = new Bid(params)

        assert bid.save() != null

        params.id = bid.id

        def model = controller.show()

        assert model.bidInstance == bid
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/bid/list'


        populateValidParams(params)
        def bid = new Bid(params)

        assert bid.save() != null

        params.id = bid.id

        def model = controller.edit()

        assert model.bidInstance == bid
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/bid/list'

        response.reset()


        populateValidParams(params)
        def bid = new Bid(params)

        assert bid.save() != null

        // test invalid parameters in update
        params.id = bid.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/bid/edit"
        assert model.bidInstance != null

        bid.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/bid/show/$bid.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        bid.clearErrors()

        populateValidParams(params)
        params.id = bid.id
        params.version = -1
        controller.update()

        assert view == "/bid/edit"
        assert model.bidInstance != null
        assert model.bidInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/bid/list'

        response.reset()

        populateValidParams(params)
        def bid = new Bid(params)

        assert bid.save() != null
        assert Bid.count() == 1

        params.id = bid.id

        controller.delete()

        assert Bid.count() == 0
        assert Bid.get(bid.id) == null
        assert response.redirectedUrl == '/bid/list'
    }
}
