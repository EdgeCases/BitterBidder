package bitterbidder

import grails.test.mixin.*
import org.junit.Before
import org.junit.After

@TestFor(BidController)
@Mock([Bid, Listing, Customer])
class BidControllerTests {

    final static emptyString = ""
    final static invalidEmail_MissingAtSign = "customeremail.com"
    final static invalidEmail_MissingDotDomain = "customer@email"
    final static invalidPassword_TooLong = "longpassword"
    final static invalidPassword_TooShort = "short"
    final static validEmail = "customer@email.com"
    final static validPassword = "password"
    final static validPassword_MediumLength = "passwrd"
    final static validPassword_MinimumLength = "paswrd"

    Customer seller

    @Before
    void setUp() {
        // Setup logic here
        seller = new Customer(
                username: "user",
                emailAddress: validEmail,
                password: validPassword
        )

        def springSecurityService = new Object()
        springSecurityService.metaClass.encodePassword = {String password -> "ENCODED_PASSWORD"}

        seller.springSecurityService = springSecurityService
    }

    @After
    void tearDown() {
        // Tear down logic here
        seller = null
    }

    //L-7: The detail page for the listing allows a new bid to be placed
    void test_SaveNewBidWithValidParams_NewBidCreated() {

        assert null != seller.save()

        def listing = new Listing(
                name: "Listing",
                startingPrice: 1.25,
                endDateTime: new Date(2012, 3, 7, 8, 0, 0),
                seller: seller
        )

        listing.save(flush: true)
        assert null != listing.save()

        def bid = new Bid(
                listing: listing,
                bidder: seller,
                amount: 12
        )

        params["id"] = listing.id
        params["listing"] = listing
        controller.save()

        //this is the proof of redirection
        assert response.redirectedUrl.contains('/listing/show')
    }

    //L-8: Validation errors will be displayed on the listing detail page if an added bid does not pass validation
    void test_SaveNewBidWithInvalidAmount_BidCreateFailsErrorsReturnedToListing() {

        assert null != seller.save()

        def listing = new Listing(
                name: "Listing",
                startingPrice: 1.25,
                endDateTime: new Date(2012, 3, 7, 8, 0, 0),
                seller: seller
        )
        listing.save(flush: true)
        assert null != listing.save()

        def bid = new Bid(
                listing: listing,
                bidder: seller,
                amount: 12
        )

        params["id"] = listing.id
        params["listing"] = listing
        //add an invalid amount (too low for our constraints)
        params["amount"] = 12.25
        controller.save()

        //this is the proof of redirection
        assert response.redirectedUrl.contains('/listing/show')
        assert null != flash.message

        //this proves that the message about the error arrives
        assert "default.invalid.validator.message" == flash.message
    }
}
