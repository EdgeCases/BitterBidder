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

    void setUp() {
        // Setup logic here
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
        //act
        //assert
        fail "Not implemented"
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
        //act
        //assert
        fail "Not implemented"
    }
    
    @Test
    void test_Bidder_WhenNull_BidIsInvalid() {
        //arrange
        //act
        //assert
        fail "Not implemented"
    }

    @Test
    void test_Bidder_WhenInvalid_BidIsInvalid() {
        //arrange
        //act
        //assert
        fail "Not implemented"
    }
}
