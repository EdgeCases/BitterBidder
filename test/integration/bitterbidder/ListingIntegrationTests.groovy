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
        validCustomer = new Customer(emailAddress: "validguy@valid.com", password: "secret");
        validCustomer.save(flush: true)

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
        //Setup logic here
        validCustomer = null
        aTestBid = null
        listingUnderTest = null
    }

    @Test
    void test_Save_WhenListingHasNewBids_BidsAreSaved() {

        //arrange        
        listingUnderTest.bids = new HashSet<Bid>()
        listingUnderTest.addToBids(aTestBid)

        //act
        listingUnderTest.save(flush: true)
        def bid = Bid.findByBidder(validCustomer)

        //assert
        assert bid.bidder.id == validCustomer.id
    }

    @Test
    void test_Save_WhenNewListingWithBidAtInitialListingAmount_BidIsSaved() {
        //arrange
        listingUnderTest.bids = new HashSet<Bid>()
        aTestBid.amount = 10.00
        listingUnderTest.addToBids(aTestBid)

        //act
        listingUnderTest.save(flush: true)
        def bid = Bid.findByBidder(validCustomer)

        //assert
        assert bid==null
    }

    @Test
    void test_Save_WhenNewListingWithBidBelowThreshold_BidIsNotSaved() {

        //arrange
        listingUnderTest.bids = new HashSet<Bid>()
        aTestBid.amount = 9.00
        listingUnderTest.addToBids(aTestBid)

        //act
        listingUnderTest.save(flush: true)
        def bid = Bid.findByBidder(validCustomer)

        //assert
        assert bid==null
    }

//    @Test
//    void test_Save_WhenBidEqualsLastAcceptedBid_SaveFails() {
//        //arrange
//
//        //act
//        //assert
//        fail "Not implemented"
//    }
//
//    @Test
//    void test_Save_WhenBidAmountAtMinimumThreshold_SaveFails() {
//        //arrange
//
//        //act
//        //assert
//        fail "Not implemented"
//    }

    @Test
    void test_Save_WhenAddingBidToExistingListing_BidIsSaved() {

        //arrange
        listingUnderTest.bids = new HashSet<Bid>()
        listingUnderTest.addToBids(aTestBid)
        listingUnderTest.save(flush: true)
        def saved = Listing.findByName("MyListing")
        Assume.assumeTrue(saved.bids.size()==1)
        def aNewBid = new Bid(amount: 12.50, bidder: validCustomer, dateCreated: new Date())
        Assume.assumeTrue(saved.bids.size()==1)

        def currentId = aTestBid.id
        //act
        saved.addToBids(aNewBid)
        saved.save(flush: true)
        saved = Listing.findByName("MyListing")

        Assume.assumeTrue(saved.bids.size()==2)

        def bid = Bid.findByAmount(12.50)

        //assert
        assert bid.id > currentId
    }

    @Test
    void test_Save_WhenAddingBidBelowThresholdToExistingListing_SaveFails() {

        //arrange
        listingUnderTest.bids = new HashSet<Bid>()
        listingUnderTest.addToBids(aTestBid)
        listingUnderTest.save(flush: true)
        def saved = Listing.findByName("MyListing")
        Assume.assumeTrue(saved.bids.size()==1)
        def aNewBid = new Bid(amount: 10.75, bidder: validCustomer, dateCreated: new Date())
        Assume.assumeTrue(saved.bids.size()==1)

        //act
        saved.addToBids(aNewBid)
        saved.save(flush: true)
        saved = Listing.findByName("MyListing")

        Assume.assumeTrue(saved.hasErrors());

        def bid = Bid.findByAmount(10.75)

        //assert
        Assert.assertNull(bid)
    }
}
