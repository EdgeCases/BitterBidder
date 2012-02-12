package bitterbidder

import static org.junit.Assert.*

import grails.test.mixin.*
import grails.test.mixin.support.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
class BidTests {

    final static validCustomerEmail = 'customer@email.com'
    final static validPassword = 'password'
    final static emptyString = "";

    private def validBidder
    private def validListing

    void setUp() {
        validBidder = new Customer(emailAddress: validCustomerEmail, password: validPassword)
        mockForConstraintsTests(Customer,[validBidder])
        
        validListing = new Listing(name: "some great item")
        mockForConstraintsTests(Listing,[validListing])
    }

    void tearDown() {
        // Tear down logic here
    }

    @Test
    void test_Amount_WhenNull_BidIsInvalid() {
        //arrange
        def bid = new Bid()
        mockForConstraintsTests(Bid,[bid])
        
        //act
        bid.validate()
        
        //assert
        assert 'nullable' == bid.errors['amount']
    }

    @Test
    void test_DateTime_WhenNull_BidIsInvalid() {
        //arrange
        def bid = new Bid()
        mockForConstraintsTests(Bid,[bid])

        //act
        bid.validate()
    }

    @Test
    void test_Listing_WhenNull_BidIsInvalid() {
        def bid = new Bid()
        mockForConstraintsTests(Bid,[bid])

        //act
        bid.validate()

        //assert
        assert 'nullable' == bid.errors['listing']
    }

    @Test
    void test_Listing_WhenInvalid_BidIsInvalid() {
        //arrange
        def invalidListing = new Listing(name: "this description is just going to be way too long; I mean, it's a ridiculously long description and completely inappropriate for normal use")
        mockForConstraintsTests Listing,[invalidListing]
        
        def validBidder = new Customer()
        mockForConstraintsTests Customer, [validBidder]

        def bid = new Bid(bidder: validBidder, amount: 1.5, listing: invalidListing)
        mockForConstraintsTests(Bid,[bid])

        //act
        bid.save()
        bid.validate()

        //assert
        assert bid.hasErrors()
        //the above assert fails, why?
        //we are creating a bid with an invalid listing (name too long)
        //but we don't get any errors
    }

    @Test
    void test_Bidder_WhenNull_BidIsInvalid() {
        def bid = new Bid()
        mockForConstraintsTests(Bid,[bid])

        //act
        bid.validate()

        //assert
        assert 'nullable' == bid.errors['bidder']
    }

    @Test
    void test_Bidder_WhenInvalid_BidIsInvalid() {
        //arrange
        def invalidBidder = new Customer(password: "short")
        mockForConstraintsTests(Customer,[invalidBidder])
        
        def alisting = new Listing(name: "a great product")
        mockForConstraintsTests(Listing,[alisting])

        def bid = new Bid(bidder: invalidBidder, amount: 1.5,listing: alisting)
        mockForConstraintsTests(Bid,[bid])
        
        //act
        bid.save()        
        
        //assert
        assert bid.hasErrors()

        //bid.errors.allErrors.each {
        //    print it
        //}
    }
}
