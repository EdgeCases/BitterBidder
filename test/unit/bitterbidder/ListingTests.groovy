package bitterbidder

import grails.test.mixin.TestFor
import org.junit.Assert
import org.junit.Before
import org.junit.Test

import org.junit.After
import org.junit.Assume
import grails.test.mixin.Mock

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestFor(Listing)
@Mock(Customer)
class ListingTests {

    Customer validCustomer
    Customer invalidCustomer;
    Listing listingUnderTest
    private Boolean False = Boolean.FALSE;
    private Boolean True = Boolean.TRUE;

    @Before
    void setUp() {
       
        //Setup logic here               
        validCustomer = new Customer(emailAddress: "validguy@valid.com", password: "secret", username: "validguy");
        invalidCustomer = new Customer(emailAddress: null, password: "secret", username: "invalidguy");

        def springSecurityService = new Object()
        springSecurityService.metaClass.encodePassword = {String password -> "ENCODED_PASSWORD"}

        validCustomer.springSecurityService = springSecurityService
        invalidCustomer.springSecurityService = springSecurityService

        def bidSet = [new Bid(amount: 10, bidder: validCustomer, dateCreated: new Date()),
                      new Bid(amount: 10.50, bidder: validCustomer, dateCreated: new Date())] as Set
        
        listingUnderTest = new Listing(
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
        listingUnderTest=null;
    }

    @Test
    void test_ValidListing_WithAllFieldsPopulated_HasNoValidationErrors() {

        def date = new Date()
        date.setMinutes(date.minutes+2)

        //arrange
        listingUnderTest.endDateTime = new Date()+1

        //act
        listingUnderTest.validate()

        //assert
        assert listingUnderTest.errors.errorCount==0
    }

    @Test
    void test_ValidListing_WithAllFieldsPopulated_BidderHasValidUsername() {

        //arrange
        listingUnderTest.endDateTime = new Date()+1

        //act
        listingUnderTest.validate()

        //assert
        assert "validguy" == listingUnderTest.seller.username
    }

    @Test
    void test_ValidListing_WithOptionalFieldsNull_HasNoValidationErrors() {
        //arrange
        listingUnderTest.description = null;
        listingUnderTest.winner = null;
        listingUnderTest.endDateTime = new Date()+1

        //act
        listingUnderTest.validate()

        //assert
        Assert.assertTrue(listingUnderTest.errors.errorCount==0)
    }

    @Test
    void test_ValidListing_WithEmptyDescription_ListingIsInvalid() {
        //arrange
        listingUnderTest.description = "";
        listingUnderTest.endDateTime = new Date()+1

        //act
        listingUnderTest.validate()

        //assert
        verifyValidationError("description")
    }

    @Test
    void test_ValidListing_WhenDescriptionIsAllSpaces_ListingIsInvalid() {
        //arrange
        listingUnderTest.description = "           ";
        listingUnderTest.endDateTime = new Date()+1

        //act
        listingUnderTest.validate()
        //assert
        verifyValidationError("description")
    }


    @Test
    void test_Name_WhenLongerThanMax_ListingIsInvalid() {

        //arrange
        listingUnderTest.name = "a".padLeft(64, "b");
        
        //act
        listingUnderTest.validate()

        //assert
        verifyValidationError("name")
    }

    @Test
    void test_Name_WhenNull_ListingIsInvalid() {
        //arrange
        listingUnderTest.name = null;

        //act
        Assume.assumeTrue(listingUnderTest.validate()==false);

        //assert
        verifyValidationError("name")
    }

    @Test
    void test_Name_WhenBlank_ListingIsInvalid() {

        //arrange
        listingUnderTest.name = "";
        println listingUnderTest.name.length()
        //act
        listingUnderTest.validate()

        //assert
        verifyValidationError("name")
    }

    @Test
    void test_Name_WhenEmpty_ListingIsInvalid() {
        //arrange
        listingUnderTest.name = "              ";
        println listingUnderTest.name.length()
        //act
        listingUnderTest.validate()

        //assert
        verifyValidationError("name")
    }
    
    @Test
    void test_EndDateTime_WhenNull_ListingIsInvalid() {

        //arrange
        listingUnderTest.endDateTime = null;

        //act
        listingUnderTest.validate()

        //assert
        verifyValidationError("endDateTime")
    }

    @Test
    void test_EndDateTime_WhenDateIsInThePast_ListingIsInvalid() {
        //arrange

        listingUnderTest.endDateTime = new Date()
        def cal = Calendar.instance;
        cal.setTime(listingUnderTest.endDateTime)
        cal.add(Calendar.MINUTE, -1);
        def inThePast = cal.time;
        listingUnderTest.endDateTime = inThePast;
        
        //act
        listingUnderTest.validate()
       
        //assert
        verifyValidationError("endDateTime")
    }
    
    @Test
    void test_Seller_WhenNull_ListingIsInvalid() {
        //arrange        
        listingUnderTest.seller =null;

        //act
        listingUnderTest.save(true);

        //assert
        verifyValidationError("seller")
    }

    @Test
    void test_Description_WhenLongerThanMax_ListingIsInvalid() {
        //arrange
        listingUnderTest.description = "a".padLeft(256)

        Assume.assumeTrue(listingUnderTest.description.length()==256)
        //act
        listingUnderTest.validate()
        //assert
        verifyValidationError("description")
    }


    @Test
    void test_Description_WhenLengthIsMax_ListingIsInvalid() {
        //arrange
        listingUnderTest.description = "a".padLeft(255)
        Assume.assumeTrue(listingUnderTest.description.length()==255)
        //act
        listingUnderTest.validate()
        //assert
        Assert.assertTrue(!listingUnderTest.errors.hasFieldErrors("description"))
    }

    @Test
    void test_StartingPrice_WhenNull_ListingIsInvalid() {
        //arrange
        listingUnderTest.startingPrice = null

        //act
        listingUnderTest.validate()

        //assert
        verifyValidationError("startingPrice")
    }

    @Test
    void test_Seller_WhenSellerIsInvalid_ListingShouldBeInvalid() {

        //arrange
        listingUnderTest.seller = invalidCustomer;
       //act
        listingUnderTest.validate()
        Assume.assumeTrue(listingUnderTest.seller.hasErrors())

        //assert
        //TODO: This should fail w/o the magic constraint.  Bug? http://jira.grails.org/browse/GRAILS-7713"
        verifyValidationError("seller")
    }

    @Test
    void test_Winner_WhenWinnerIsInvalid_ListingShouldBeInvalid() {
        //arrange
        listingUnderTest.winner = invalidCustomer
        //act
        listingUnderTest.validate()
        //assert
        verifyValidationError("winner")
    }

    @Test
    void test_IsEnded_WhenEndDateTimeIsPast_IsEnded() {
        //arrange
         listingUnderTest.endDateTime = new Date()-2
        //act && assert
        Assert.assertTrue(listingUnderTest.isEnded())
    }

    @Test
    void test_IsEnded_WhenEndDateTimeNotPast_NotIsEnded() {
        //arrange
        listingUnderTest.endDateTime = new Date()+2
        //act && assert
        Assert.assertFalse(listingUnderTest.isEnded())
    }

    @Test
    void test_IsEnded_WhenNew_NotIsEnded() {
        //arrange
        def listing = new Listing();
        //act && assert
        Assert.assertFalse(listingUnderTest.isEnded())
    }

    private void verifyValidationError(String fieldName) {
        Assert.assertTrue(listingUnderTest.errors.hasFieldErrors(fieldName))
    }
}

