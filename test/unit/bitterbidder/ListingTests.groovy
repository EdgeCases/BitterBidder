package bitterbidder

import grails.test.mixin.TestFor
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import static org.junit.Assert.fail
import org.junit.After
import org.junit.Assume
import grails.test.mixin.Mock
import org.junit.Ignore

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestFor(Listing)
@Mock(Customer)
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
        
        def bidSet = [new Bid(amount: 10, bidder: validCustomer, dateCreated: new Date()),
                      new Bid(amount: 10.50, bidder: validCustomer, dateCreated: new Date())] as Set
        
        defaultListing = new Listing(
                                bids: bidSet,
                                description: "A test listing",
                                seller: validCustomer,
                                winner: validCustomer,
                                endDateTime: new Date()+1,
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
    void test_ValidListing_WithAllFieldsPopulated_HasNoValidationErrors() {

        //arrange
        defaultListing.endDateTime = new Date()+1

        //act
        defaultListing.validate()

        //assert
        assert defaultListing.errors.errorCount==0
    }

    @Test
    void test_ValidListing_WithOptionalFieldsNull_HasNoValidationErrors() {
        //arrange
        defaultListing.description = null;
        defaultListing.winner = null;
        defaultListing.endDateTime = new Date()+1

        //act
        defaultListing.validate()

        //assert
        Assert.assertTrue(defaultListing.errors.errorCount==0)
    }

    @Test
    void test_ValidListing_WithEmptyDescription_ListingIsInvalid() {
        //arrange
        defaultListing.description = "";
        defaultListing.endDateTime = new Date()+1

        //act
        defaultListing.validate()

        //assert
        verifyValidationError("description")
    }

    @Test
    void test_ValidListing_WhenDescriptionIsAllSpaces_ListingIsInvalid() {
        //arrange
        defaultListing.description = "           ";
        defaultListing.endDateTime = new Date()+1

        //act
        defaultListing.validate()
        //assert
        verifyValidationError("description")
    }


    @Test
    void test_Name_WhenLongerThanMax_ListingIsInvalid() {

        //arrange
        defaultListing.name = "a".padLeft(64, "b");
        
        //act
        defaultListing.validate()

        //assert
        verifyValidationError("name")
    }

    @Test
    void test_Name_WhenNull_ListingIsInvalid() {
        //arrange
        defaultListing.name = null;

        //act
        Assume.assumeTrue(defaultListing.validate()==false);

        //assert
        verifyValidationError("name")
    }

    @Test
    void test_Name_WhenBlank_ListingIsInvalid() {

        //arrange
        defaultListing.name = "";
        println defaultListing.name.length()
        //act
        defaultListing.validate()

        //assert
        verifyValidationError("name")
    }

    @Test
    void test_Name_WhenEmpty_ListingIsInvalid() {
        //arrange
        defaultListing.name = "              ";
        println defaultListing.name.length()
        //act
        defaultListing.validate()

        //assert
        verifyValidationError("name")
    }
    
    @Test
    void test_EndDateTime_WhenNull_ListingIsInvalid() {

        //arrange
        defaultListing.endDateTime = null;

        //act
        defaultListing.validate()

        //assert
        verifyValidationError("endDateTime")
    }

    @Test
    void test_EndDateTime_WhenDateIsInThePast_ListingIsInvalid() {
        //arrange
        defaultListing.endDateTime = new Date()
        def cal = Calendar.instance;
        cal.setTime(defaultListing.endDateTime)
        cal.add(Calendar.MINUTE, -1);
        def inThePast = cal.time;
        defaultListing.endDateTime = inThePast;
        
        //act
        defaultListing.validate()
       
        //assert
        verifyValidationError("endDateTime")
    }
    
    @Test
    void test_Seller_WhenNull_ListingIsInvalid() {
        //arrange        
        defaultListing.seller =null;

        //act
        defaultListing.save(true);

        //assert
        verifyValidationError("seller")
    }

    @Test
    void test_Description_WhenLongerThanMax_ListingIsInvalid() {
        //arrange
        defaultListing.description = "a".padLeft(256)

        Assume.assumeTrue(defaultListing.description.length()==256)
        //act
        defaultListing.validate()
        //assert
        verifyValidationError("description")
    }


    @Test
    void test_Description_WhenLengthIsMax_ListingIsInvalid() {
        //arrange
        defaultListing.description = "a".padLeft(255)                
        Assume.assumeTrue(defaultListing.description.length()==255)
        //act
        defaultListing.validate()
        //assert
        Assert.assertTrue(!defaultListing.errors.hasFieldErrors("description"))
    }

    @Test
    void test_StartingPrice_WhenNull_ListingIsInvalid() {
        //arrange
        defaultListing.startingPrice = null

        //act
        defaultListing.validate()

        //assert
        verifyValidationError("startingPrice")
    }

    @Test
    void test_Seller_WhenSellerIsInvalid_ListingShouldBeInvalid() {

        //arrange
        defaultListing.seller = invalidCustomer;
       //act
        defaultListing.validate()
        Assume.assumeTrue(defaultListing.seller.hasErrors())

        //assert
        //TODO: This should fail w/o the magic constraint.  Bug? http://jira.grails.org/browse/GRAILS-7713"
        verifyValidationError("seller")
    }

    @Test
    void test_Winner_WhenWinnerIsInvalid_ListingShouldBeInvalid() {
        //arrange
        defaultListing.winner = invalidCustomer
        //act
        defaultListing.validate()
        //assert
        verifyValidationError("winner")
    }

    @Test
    void test_Bids_WhenLessThanTwoBids_ListingIsInvalid() {
        //arrange
        defaultListing.bids.clear()
        defaultListing.bids = [new Bid(amount: 10, bidder: validCustomer, dateCreated: new Date())] as Set
        //act
        defaultListing.validate()
        //assert
        verifyValidationError("bids")
    }

//    @Test
//    void test_Bids_WhenBidIsInvalid_ListingIsInvalid() {
//        //arrange
//
//        //act
//        //assert
//        fail "Not implemented"
//    }
    
    
    private void verifyValidationError(String fieldName) {
        Assert.assertTrue(defaultListing.errors.hasFieldErrors(fieldName))
    }
}

