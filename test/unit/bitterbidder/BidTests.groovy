package bitterbidder

import static org.junit.Assert.*

import grails.test.mixin.*
import grails.test.mixin.support.*
import org.junit.*
import org.h2.util.DateTimeUtils
import grails.test.GrailsUnitTestCase

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
@TestFor(Bid)
class BidTests {//extends GrailsUnitTestCase{

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

    @Test   // B-1
    void test_Amount_WhenNull_BidIsInvalid() {
        //arrange
        def bid = new Bid()
        mockForConstraintsTests(Bid,[bid])
        
        //act
        bid.validate()
        
        //assert
        assert 'nullable' == bid.errors['amount']
    }

    @Test   // B-1
    void test_DateTime_WhenNull_BidIsInvalid() {
        //arrange
        //NOTE: order seems to matter
        def seller = new Customer(emailAddress: "seller@email.com", password: "password")
        def bidder = new Customer(emailAddress: "bidder@email.com", password: "password")
        mockDomain(Customer, [seller, bidder])

        def listing = new Listing(endDateTime: new Date(), name: "Listing", startingPrice: 1.23, seller: seller)
        mockDomain(Listing, [listing])

        def bid = new Bid(listing: listing, bidder: bidder, amount: 1.23)
        mockDomain(Bid, [bid])

        seller.save()
        bidder.save()
        listing.save()

        //act
        bid.save()

        //assert
        assert bid.dateCreated != null
    }

    @Test   // B-2
    void test_Listing_WhenNull_BidIsInvalid() {
        //arrange
        def bid = new Bid()
        mockForConstraintsTests(Bid,[bid])

        //act
        bid.validate()

        //assert
        assert 'nullable' == bid.errors['listing']
    }

    @Test   // B-2
    void test_Listing_WhenInvalid_BidIsInvalid() {
        //arrange
        //NOTE: order seems to matter
        def seller = new Customer(emailAddress: "seller@email.com", password: "password")
        def bidder = new Customer(emailAddress: "bidder@email.com", password: "password")
        mockDomain Customer, [seller, bidder]
        
        def listingName = """this listing name is just going to be way too long;
                                I mean, it's a ridiculously long description and
                                completely inappropriate for normal use"""

        def invalidListing = new Listing(endDateTime: new Date(), name: listingName, startingPrice: 1.23, seller: seller)
        mockDomain Listing, [invalidListing]

        def bid = new Bid(listing: invalidListing, bidder: bidder, amount: 1.23)
        mockDomain Bid, [bid]

        seller.save()
        bidder.save()
        invalidListing.save()

        //act
        bid.validate()
        bid.save()

        //assert
        assert null != bid.bidder
        assert null == bid.listing.description
    }

    @Test   // B-3
    void test_Bidder_WhenNull_BidIsInvalid() {
        def bid = new Bid()
        mockForConstraintsTests(Bid,[bid])

        //act
        bid.validate()

        //assert
        assert 'nullable' == bid.errors['bidder']
    }

    @Test   // B-3
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
