package bitterbidder

import static org.junit.Assert.*
import org.junit.*

class ListingServiceIntegrationTests {

    @Test
    void test_getMyListings_WhenUserHasListings_ListingsAreReturned() {
        //arrange
        def nextweek = new Date()+7;
        def aCustomer = TestUtility.makeCustomer("cliente123", "secrete", "danny1234@pwd.com")
        aCustomer.save(validate: false, flush:true);

        def myListing1 = new Listing(seller: aCustomer, endDateTime:nextweek, startingPrice: 10, name: "myListing1")
        myListing1.save(flush: true)
        def myListing2 = new Listing(seller: aCustomer, endDateTime:nextweek, startingPrice: 100, name: "myListing2")
        myListing2.save(flush: true)

        def listingService = new ListingService();
        def myListings = listingService.getMyListings(aCustomer.username);
        //assert
        def count = myListings.count({it->it.name=="myListing1" || it.name=="myListing2"})
        Assert.assertTrue(count==2);
    }

    @Test
    void test_getMyListings_WhenUserHasNoListings_NoListingsAreReturned() {
        //arrange
        def aCustomer = TestUtility.makeCustomer("cliente123", "secrete", "danny12345@pwd.com")
        aCustomer.save(validate: false, flush:true);

        //act
        def listingService = new ListingService();
        def myListings = listingService.getMyListings(aCustomer.username);

        //assert
        Assert.assertTrue(myListings.size()==0)
    }

    @Test
   // @Ignore("Fail in grails test-app but not in the IDE")
    void test_hasListingEnded_WhenListingIsEnded_ReturnsTrue() {
        def yesterday = new Date()-1;
        def aCustomer = TestUtility.makeCustomer("cliente123", "secrete", "danny1234567@pwd.com")
        aCustomer.save(validate: false, flush:true);

        def aListing = new Listing(seller: aCustomer, endDateTime:yesterday, startingPrice: 10, name: "myListing1")
        aListing.save(flush: true, validate: false)

        def listingService = new ListingService();
        Assert.assertTrue(listingService.hasListingEnded(aListing.id))
    }

    @Test
  //  @Ignore("Fail in grails test-app but not in the IDE")
    void test_hasListingEnded_WhenListingHasNotEnded_RetunsFalse() {
        def nextweek = new Date()+7;
        def aCustomer = TestUtility.makeCustomer("cliente123", "secrete", "danny123456@pwd.com")
        aCustomer.save(validate: false, flush:true);

        def aListing = new Listing(seller: aCustomer, endDateTime:nextweek, startingPrice: 10, name: "myListing1")
        aListing.save(flush: true)

        def listingService = new ListingService();
        Assert.assertFalse(listingService.hasListingEnded(aListing.id))

    }
}
