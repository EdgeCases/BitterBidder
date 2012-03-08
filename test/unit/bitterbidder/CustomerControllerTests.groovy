package bitterbidder



import org.junit.*
import grails.test.mixin.*

@TestFor(CustomerController)
@Mock([Customer, Listing, Bid])
class CustomerControllerTests {


    def populateValidParams(params) {
      assert params != null
      params["emailAddress"] = 'customer@email.com'
      params["password"] = 'password'
    }

    // C-4: An existing customer can only be deleted through the web interface if they have 0 listings and 0 bids.
    void test_Delete_WhenCustomerHasNoListingsOrBids_CustomerIsSuccessfullyDeleted() {
        // arrange
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/customer/list'

        response.reset()

        populateValidParams(params)
        def customer = new Customer(params)

        assert customer.save() != null
        assert Customer.count() == 1

        params.id = customer.id

        // act
        controller.delete()

        // assert
        assert Customer.count() == 0
        assert Customer.get(customer.id) == null
        assert response.redirectedUrl == '/customer/list'
    }

    // C-4: An existing customer can only be deleted through the web interface if they have 0 listings.
    void test_Delete_WhenCustomerHasListing_CustomerIsNotDeleted() {
        // arrange
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/customer/list'

        response.reset()

        populateValidParams(params)
        def customer = new Customer(params)

        assert customer.save() != null
        assert Customer.count() == 1

        def listing = new Listing(
                name: "Listing",
                startingPrice: 1.25,
                endDateTime: new Date(2012, 3, 7, 8, 0, 0),
                seller: customer
        )
        listing.save(flush: true)

        params.id = customer.id

        // act
        controller.delete()

        // assert
        assert Customer.count() == 1
        assert Customer.get(customer.id) != null
        assert flash.error == 'default.not.deleted.listing.exists.message'
        assert response.redirectedUrl == '/customer/list'
    }

    // C-4: An existing customer can only be deleted through the web interface if they have 0 bids.
    void test_Delete_WhenCustomerHasBid_CustomerIsNotDeleted() {
        // arrante
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/customer/list'

        response.reset()

        populateValidParams(params)
        def customer = new Customer(params)

        assert customer.save() != null
        assert Customer.count() == 1

        def bid = new Bid(
                amount: 2.25,
                listing: new Listing(
                        name: "Listing",
                        startingPrice: 1.25,
                        endDateTime: new Date(2012, 3, 7, 8, 0, 0),
                        seller: new Customer(
                                emailAddress: "customerwithlisting@email.com",
                                password: "password").save(flush: true)
                ).save(flush: true),
                bidder: customer
        ).save(flush: true)

        params.id = customer.id

        // act
        controller.delete()

        // assert
        assert Customer.count() == 2
        assert Customer.get(customer.id) != null
        assert flash.error == 'default.not.deleted.bid.exists.message'
        assert response.redirectedUrl == '/customer/list'
    }
}
