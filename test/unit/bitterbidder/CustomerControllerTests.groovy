package bitterbidder

import org.junit.*
import grails.test.mixin.*

@TestFor(CustomerController)
@Mock([Customer, Listing, Bid])
class CustomerControllerTests {

    def populateValidParams(params) {
      assert params != null
      params["username"] = 'customer'
      params["emailAddress"] = 'customer@email.com'
      params["password"] = 'password'
    }

    def populateInValidParams(params) {
        assert params != null
        params["emailAddress"] = 'customer@email@domain.com'
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

        def springSecurityService = new Object()
        springSecurityService.metaClass.encodePassword = {String password -> "ENCODED_PASSWORD"}

        customer.springSecurityService = springSecurityService

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

        def springSecurityService = new Object()
        springSecurityService.metaClass.encodePassword = {String password -> "ENCODED_PASSWORD"}

        customer.springSecurityService = springSecurityService

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
        // arrange
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/customer/list'

        response.reset()

        populateValidParams(params)
        def customer = new Customer(params)

        def springSecurityService = new Object()
        springSecurityService.metaClass.encodePassword = {String password -> "ENCODED_PASSWORD"}

        customer.springSecurityService = springSecurityService

        assert customer.save() != null
        assert Customer.count() == 1

        def biddingCustomer = new Customer(username: "biddingCustomer", emailAddress: "biddingCustomer@email.com", password: "password")
        def biddingCustomerSpringSecurityService = new Object()
        biddingCustomerSpringSecurityService.metaClass.encodePassword = {String password -> "ENCODED_PASSWORD"}

        biddingCustomer.springSecurityService = biddingCustomerSpringSecurityService

        biddingCustomer.save(flush: true)

        def bid = new Bid(
                amount: 2.25,
                listing: new Listing(
                        name: "Listing",
                        startingPrice: 1.25,
                        endDateTime: new Date(2012, 3, 7, 8, 0, 0),
                        seller: biddingCustomer,
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
    
    void test_GetUserFromEmail_WhenCustomerHasValidEmail_UserPortionReturned(){

        // arrange
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/customer/list'

        response.reset()

        populateValidParams(params)
        def customer = new Customer(params)

        def springSecurityService = new Object()
        springSecurityService.metaClass.encodePassword = {String password -> "ENCODED_PASSWORD"}

        customer.springSecurityService = springSecurityService

        assert customer.save() != null
        assert Customer.count() == 1

        params.id = customer.id

        //assume
        def email = 'customer@email.com'
        def userPortion = 'customer'

        // act
        def user = controller.passwordMinusDomain()

        assert userPortion == user

        // cleanup
        controller.delete()
    }

/*  //can't insert a bad email due to constraints... may have to come up with another way
    void test_GetUserFromEmail_WhenCustomerHasInValidEmail_ErrorThrown(){

        // arrange
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/customer/list'

        response.reset()

        populateInValidParams(params)
        def customer = new Customer(params)

        assert customer.save() != null
        assert Customer.count() == 1

        params.id = customer.id

        //assume
        def email = 'customer@email.com'
        def userPortion = 'customer'

        // act
        def user = controller.passwordMinusDomain()

        assert controller.hasErrors()

        // cleanup
        controller.delete()
    }
*/
}
