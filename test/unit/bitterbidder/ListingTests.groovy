package bitterbidder

import grails.test.mixin.TestFor
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import static org.junit.Assert.fail
import org.junit.After
import org.junit.Assume


/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestFor(Listing)
class ListingTests {

    Customer validCustomer
    Customer invalidCustomer;
    Listing defaultListing
    private Boolean False = Boolean.FALSE;
    private Boolean True = Boolean.TRUE;

    @Before
    void setUp() {
        //Setup logic here
        validCustomer = new Customer(emailAddress: "validguy@valid.com", password: "secret");
        invalidCustomer = new Customer(emailAddress: null, password: "secret");
        defaultListing = new Listing(
                                description: "A test listing",
                                seller: validCustomer,
                                winner: validCustomer,
                                endDateTime: new Date(),
                                name: "Default", 
                                startingPrice: 10)
    }

    @After
    void tearDown() {
        validCustomer = null;
        invalidCustomer=null;
        defaultListing=null;
    }

    @Test
    void test_ValidListing_HasNoValidationErrors() {

        //arrange
        defaultListing.endDateTime = new Date()+1
        //act && assume
        Assume.assumeTrue(defaultListing.validate());

        //assert
        assert defaultListing.errors.errorCount==0
    }
    
    @Test
    void test_Name_WhenLongerThanMax_ListingIsInvalid() {

        //arrange
        defaultListing.name = "a".padLeft(64, "b");
        
        //act
        Assume.assumeTrue(defaultListing.validate()==false);

        //assert
        Assert.assertTrue(defaultListing.errors.hasFieldErrors("name"));
    }

    @Test
    void test_Name_WhenNull_ListingIsInvalid() {
        //arrange
        defaultListing.name = null;

        //act
        Assume.assumeTrue(defaultListing.validate()==false);

        //assert
        Assert.assertTrue(defaultListing.errors.hasFieldErrors("name"));
    }

    @Test
    void test_Name_WhenBlank_ListingIsInvalid() {

        //arrange
        defaultListing.name = "";
        println defaultListing.name.length()
        //act
        Assume.assumeTrue(defaultListing.validate()==false);

        //assert
        Assert.assertTrue(defaultListing.errors.hasFieldErrors("name"));
    }

    @Test
    void test_Name_WhenEmpty_ListingIsInvalid() {
        //arrange
        defaultListing.name = "              ";
        println defaultListing.name.length()
        //act
        Assume.assumeTrue(defaultListing.validate()==false);

        //assert
        Assert.assertTrue(defaultListing.errors.hasFieldErrors("name"));
    }
    
    @Test
    void test_EndDateTime_WhenNull_ListingIsInvalid() {

        //arrange
        defaultListing.endDateTime = null;
        //act
        Assume.assumeTrue(defaultListing.validate()==false);

        //assert
        Assert.assertTrue(defaultListing.errors.hasFieldErrors("endDateTime"));
    }

    @Test
    void test_EndDateTime_WhenDateIsInThePast_ListingIsInvalid() {
        //arrange
        def cal = Calendar.instance;
        cal.setTime(defaultListing.endDateTime)
        cal.add(Calendar.MILLISECOND, -1);
        def inThePast = cal.time;
        defaultListing.endDateTime = inThePast;
        
        //act
        Assume.assumeTrue(defaultListing.endDateTime!=null)
        Assume.assumeTrue(defaultListing.validate()==false);
       
        //assert
        Assert.assertTrue(defaultListing.errors.hasFieldErrors("endDateTime"));
    }
    
    @Test
    void test_Seller_WhenNull_ListingIsInvalid() {
        //arrange        
        defaultListing.seller =null;

        //act
        defaultListing.save(true);

        //assert
        Assert.assertTrue(defaultListing.errors.getFieldError("seller").rejectedValue.equals(null));
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
        
}

