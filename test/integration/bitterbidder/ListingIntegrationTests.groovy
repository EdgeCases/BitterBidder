package bitterbidder

import org.junit.*

class ListingIntegrationTests{

    Customer validCustomer
    Customer anotherValidCustomer
    Listing listingUnderTest
    Bid aTestBid;

    @Before
    void setUp() {

        //Setup logic here
        validCustomer = new Customer(emailAddress: "validguy@valid.com", password: "secret");
        validCustomer.save(flush: true)

        anotherValidCustomer = new Customer(emailAddress: "anothervalidguy@valid.com", password: "secret");
        anotherValidCustomer.save(flush: true)


        aTestBid = new Bid(amount: 10.50, bidder: validCustomer, createdDate: new Date())
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
        def aNewBid = new Bid(amount: 12.50, bidder: validCustomer, createdDate: new Date())
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
        def aNewBid = new Bid(amount: 10.75, bidder: validCustomer, createdDate: new Date())
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
//
//    @Test(expected = IllegalStateException.class)
//    @Ignore()
//    void test_Save_WhenAddingTwoBidsAnd2ndOneIsBelowThreshold_SaveFails() {
//
//        //arrange
//        listingUnderTest.bids = new HashSet<Bid>()
//        listingUnderTest.addToBids(aTestBid)
//        listingUnderTest.save(flush: true)
//
//        def client1 = Listing.findByName("MyListing")
//        def client2 = Listing.findByName("MyListing")
//
//        Assume.assumeTrue(client1.bids.size()==1)
//        Assume.assumeTrue(client1.id==client2.id)
//
//        def aNewBid = new Bid(amount: 12.75, bidder: validCustomer, createdDate: new Date())
//        def anotherBid = new Bid(amount: 13.00, bidder: anotherValidCustomer, createdDate: new Date())
//
//        //act
//        client1.addToBids(aNewBid)
//        client2.addToBids(anotherBid)
//        client1.save(flush: true)
//        client2.save(flush: true)
//        //assert - whoops no assert- check the magic attribute
//    }
}
