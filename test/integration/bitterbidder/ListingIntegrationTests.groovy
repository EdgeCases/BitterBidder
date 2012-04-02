package bitterbidder

import org.junit.*
import grails.test.mixin.TestFor

class ListingIntegrationTests{

    Customer validCustomer
    Listing listingUnderTest
    Bid aTestBid;

    @Before
    void setUp() {

        //Setup logic here
        validCustomer = new Customer(emailAddress: "validguy@valid.com", password: "secret", username: "validCustomer");

        def customerSpringSecurityService = new Object()
        customerSpringSecurityService.metaClass.encodePassword = {String password -> "ENCODED_PASSWORD"}

        validCustomer.springSecurityService = customerSpringSecurityService
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
    void test_Save_WhenListingHasNewBids_BidsAreSaved() {
        
        //arrange
        validCustomer.save(flush: true)
        listingUnderTest.bids = new HashSet<Bid>()
        listingUnderTest.addToBids(aTestBid)
        
        //act
        listingUnderTest.save(flush: true)
        def bid = Bid.findByBidder(validCustomer)
        
        //assert
        assert bid.bidder.id == validCustomer.id
    }

    @Test
    void test_Save_WhenBidAddingToExistingListing_BidIsSaved() {
        
        //arrange
        validCustomer.save(flush: true)
        listingUnderTest.bids = new HashSet<Bid>()
        listingUnderTest.addToBids(aTestBid)
        listingUnderTest.save(flush: true)
        def saved = Listing.findByName("MyListing")
        Assume.assumeTrue(saved.bids.size()==1)
        def aNewBid = new Bid(amount: 12.50, bidder: validCustomer, dateCreated: new Date())
        Assume.assumeTrue(saved.bids.size()==1)

        //act
        saved.addToBids(aNewBid)                                
        saved.save(flush: true)
        saved = Listing.findByName("MyListing")
        def bid = Bid.findByAmount(12.50)
        
        //assert
        Assert.assertTrue(saved.bids.size()==2)
    }

}
