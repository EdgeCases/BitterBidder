package bitterbidder

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import javax.validation.constraints.AssertTrue
import javax.validation.constraints.AssertFalse
import org.junit.Assume
import org.junit.Before
import grails.converters.deep.JSON
import org.junit.Assert
import org.junit.AfterClass
import org.junit.After
import grails.plugin.mail.MailService

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(ListingNotificationService)
@Mock(Listing)
class ListingNotificationServiceIntegrationTests {

    void test_sendListingEndedNotifications_ForEndedListings_SendJMSMessage() {

        def ended1 = TestUtility.getValidListingWithBids()
        ended1.wasNotificationSent=false;
        ended1.endDateTime = new Date()-1
        ended1.name = "Brewskies"

        def ended2 = TestUtility.getValidListingWithBids()
        ended2.wasNotificationSent=false;
        ended2.endDateTime = new Date()-1
        ended2.name = "Chohibas"

        def notEnded = new Listing(wasNotificationSent: false, endDateTime: new Date()+1, name: "notended")

        ended1.save(validate: false)
        ended2.save(validate: false)
        notEnded.save(validate: false)

        Listing.getAll().each {Assume.assumeTrue !it.wasNotificationSent}
        def svc = new ListingNotificationService()
        //act
        svc.sendListingEndedNotifications();

        Listing.findAll {name!="notended"}
                .each {Assert.assertTrue it.wasNotificationSent}
        Assert.assertFalse Listing.get(notEnded.id).wasNotificationSent
    }


    void test_sendListingEndedNotifications_WhenMessageHasBeenSent_JMSMessageNotSent() {

        //arrange
        def ended1 = new Listing(wasNotificationSent: false, endDateTime: new Date()-1, name: "Beers")
        def ended2 = new Listing(wasNotificationSent: false, endDateTime: new Date()-1, name:"Cigars")
        ended2.save(validate: false)
        ended1.save(validate: false)

        Listing.findAll()
                .each {Assume.assumeTrue !it.wasNotificationSent}

        def svc = new ListingNotificationService()
        svc.sendListingEndedNotifications();
        Listing.getAll().each {Assume.assumeTrue it.wasNotificationSent}

    }

    void test_sendListingEndedNotifications_WhenMessageHasBeenSent_ListingNotificationSentSetToTrue() {
        //arrange
        def saved = new Listing(wasNotificationSent: false, endDateTime: new Date()-1, name: "Cohibas")

        saved.save(validate: false)
        Assume.assumeTrue(!saved.wasNotificationSent)
        Assume.assumeTrue(saved.isEnded())
        def svc = new ListingNotificationService()

        //act
        svc.sendListingEndedNotifications();
        //assert
        saved = Listing.get(saved.id)

        Assert.assertTrue saved.wasNotificationSent
    }

}


