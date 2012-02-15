package bitterbidder

import org.junit.*
import grails.test.mixin.TestFor

@TestFor(Listing)
class ListingIntegrationTests {

    Customer validCustomer
    Listing listingUnderTest
    Bid aTestBid;
    @Before
    void setUp() {

        //Setup logic here
        validCustomer = new Customer(emailAddress: "validguy@valid.com", password: "secret");

        aTestBid = new Bid(amount: 10.50, bidder: validCustomer, dateCreated: new Date())
        listingUnderTest = new Listing(                   
                description: "A test listing",
                seller: validCustomer,                
                endDateTime: new Date()+1,
                name: "MyListing",
                startingPrice: 10)
    }

    @After
    void tearDown() {
        // Tear down logic here
    }

    @Test
    void test_Save_WhenANewBidIsAdded_BidIsSavedWithListing() {
        //arrange
        listingUnderTest.bids = new HashSet<Bid>()
        listingUnderTest.addToBids(aTestBid)
        //act
        listingUnderTest.save(flush: true)

        def bid = Bid.get(1)
        
        //assert
        assert bid !=null
    }


}
