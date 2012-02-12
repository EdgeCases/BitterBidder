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

    def validBidder
    def validListing

    void setUp() {
        validBidder = new Customer()
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
        def bid = new Bid(listing: listing)
        mockForConstraintsTests(Bid,[bid])

        //act
        bid.validate()

        //assert
        //assert 'nullable' == bid.errors['amount']
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

        bid.save()

        //act
        //assert
        assert bid.hasErrors()

        //bid.errors.allErrors.each {
        //    print it
        //}
    }
}
