package bitterbidder

import grails.test.mixin.TestFor
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import static org.junit.Assert.fail
import org.omg.CORBA.Environment

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestFor(Listing)
class ListingTests {

    Customer validCustomer
    Customer invalidCustomer;
    Listing defaultListing


    @Before
    void setUp() {
        //Setup logic here
        validCustomer = new Customer(emailAddress: "validguy@valid.com", password: "secret");
        invalidCustomer = new Customer(emailAddress: null, password: "secret");
        defaultListing = new Listing(
                                description: "A test listing",
                                seller: null,
                                winner: validCustomer,
                                endDateTime: new Date()+1,
                                name: "Default", 
                                startingPrice: 10)
    }

    void tearDown() {
        // Tear down logic here
    }

    @Test
    void test_Seller_WhenNull_ListingIsInvalid() {
        //arrange        
        def listing = defaultListing;

        //act
        listing.save(true);

        //assert
        Assert.assertTrue(listing.errors.getFieldError("seller").rejectedValue.equals(null));
    }

    @Test
    void test_Seller_WhenSellerIsInvalid_WhatDoWeDoHere() {

        //arrange
        defaultListing.seller = invalidCustomer;
        //act
        defaultListing.save(true);

        //assert
        //TODO: This should fail
        // Assert.assertTrue(listing.errors?.getFieldError("seller")?.rejectedValue.equals(null));            }
    }

    @Test
    void test_Winner_WhenNull_ListingIsValid() {
        //arrange
        defaultListing
        //act
        //assert
        fail "Not implemented"
    }

    @Test
    void test_Winner_WhenWinnerIsInvalid_WhatDoWeDoHere() {
        //arrange
        //act
        //assert
        fail "Not implemented"
    }

    @Test
    void test_Description_WhenLongerThanMax_ListingIsInvalid() {
        //arrange
        //act
        //assert
        fail "Not implemented"
    }

    @Test
    void test_Description_WhenShorterThanMin_ListingIsInvalid() {
        //arrange
        //act
        //assert
        fail "Not implemented"
    }

    @Test
    void test_Date_WhenInvalid_ListingIsInvalid() {
        //arrange
        //act
        //assert
        fail "Not implemented"
    }

    @Test
    void test_Name_WhenInvalid_ListingIsInvalid() {
        //arrange
        def listing = new Listing(name: "name")

        //act & assert
        //null
        SaveAndAssert({listing.name=null}, "name", "name cannot be invaild");
        //all spaces
        SaveAndAssert({listing.name="    "}, "name", "name cannot be invaild");
        //tabs
        SaveAndAssert({listing.name="       "}, "name", "name cannot be invaild");

        fail "Not implemented"
    }

    void SaveAndAssert(Closure closure, String fieldName, String message) {
            }

}

