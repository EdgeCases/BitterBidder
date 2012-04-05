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

    @Test
    void test_findListingsEndingInTheFuture_WhenFutureEndingDate_ListingIsReturned() {
        //arrange
        def nextweek = new Date()+7;
        def yesterday = new Date()-1;
        validCustomer.save(flush: true)

        def futureListing = new Listing(seller: validCustomer, endDateTime:nextweek, startingPrice: 10, name: "f1")
        futureListing.save(flush: true)
        def futureListing2 = new Listing(seller: validCustomer, endDateTime:nextweek, startingPrice: 100, name: "f2")
        futureListing2.save(flush: true)
        def pastListing =  new Listing(seller: validCustomer, endDateTime:yesterday, startingPrice: 10, name: 'older11')
        pastListing = pastListing.save(flush: true, validate: false)

        def listings = Listing.findAll();
        def count = listings.count({it->it.name=="older11"})
        Assume.assumeTrue(count==1)
        def futureListings = Listing.findListingsEndingInTheFuture.list()

        //assert
        count = futureListings.count({it->it.name=="older11"})
        Assert.assertTrue(count==0);

    }
}
