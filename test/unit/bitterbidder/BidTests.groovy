// MikeG: requirement B-4 does not have a test yet
package bitterbidder

import grails.test.mixin.*
import grails.test.mixin.support.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
@TestFor(Bid)
@Mock([Customer, Listing])
class BidTests {//extends GrailsUnitTestCase{

    final static bidAmount = 2.20
    final static bidderEmail = "bidder@email.com"
    final static customerPassword = "password"
    final static sellerEmail = "seller@email.com"
    final static startingPrice = 1.23

    Bid bidUnderTest
    Customer bidder
    Listing listing
    Customer seller

    @Before
    void setUp() {
        // Setup logic here
        bidder = new Customer(
                emailAddress: bidderEmail,
                password: customerPassword
        )

        seller = new Customer(
                emailAddress: sellerEmail,
                password: customerPassword
        )

        listing = new Listing(
                endDateTime: new Date(),
                name: "Listing",
                startingPrice: startingPrice,
                seller: seller
        )

        bidUnderTest = new Bid(
                listing: listing,
                bidder: bidder,
                amount: bidAmount
        )
    }

    @After
    void tearDown() {
        // Tear down logic here
        bidUnderTest = null
        listing = null
        seller = null
        bidder = null
    }

//    @Test //UI-1: named query
//    void test_Bids_WhenMoreThanTenBids_GetLastTenNamedQueryReturnsMostRecentTenBids() {
//
//
//    }

    @Test   // B-1: Bids have the following required fields: amount and date/time of bid (unit test)
    void test_Amount_WhenNull_BidIsInvalid() {
        //arrange
        bidUnderTest.amount = null
        //mockForConstraintsTests(Bid,[bidUnderTest])
        
        //act
        bidUnderTest.validate()
        
        //assert
        assert bidUnderTest.errors.hasFieldErrors("amount")

        //restore
        bidUnderTest.amount = bidAmount
    }

    @Test   // B-1: Bids have the following required fields: amount and date/time of bid (unit test)
    void test_AmountAndDateTime_WhenValid_BidIsValid() {
        //arrange

        //act
        bidUnderTest.validate();

        //assert
        assert bidUnderTest.errors.fieldErrorCount == 0
    }

    @Test   // B-2: Bids are required to be for a Listing (unit test)
    void test_Listing_WhenNull_BidIsInvalid() {
        //arrange
        def bidUnderTest = new Bid()
        mockForConstraintsTests(Bid,[bidUnderTest])

        //act
        bidUnderTest.validate()

        //assert
        assert 'nullable' == bidUnderTest.errors['listing']
    }
}
