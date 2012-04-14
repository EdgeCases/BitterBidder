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
import grails.plugin.jms.JmsService
import org.junit.Test

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */



class ListingNotificationServiceIntegrationTests {
    def seller
    def winner
    def bids
    @Before
    void init(){
        seller = TestUtility
                    .makeCustomer("bitter", "secret", "bitterbidderdev@google.com" )
                    .save(validate: false,flush:true)

        winner = TestUtility
                .makeCustomer("bitterbuyer", "secret", "EdgeCases@groups.live.com" )
                .save(validate: false,flush:true)

    }

    @Test
    void test_sendListingEndedNotifications_ForEndedListings_SendJMSMessage() {

        //create and save ended listing 1
        def ended1 = TestUtility.getValidListingForCustomer(seller, winner);
        ended1.wasNotificationSent=false;
        ended1.endDateTime = new Date()-1
        ended1.name = "Brewskies-ended"
        ended1.save(validate: false, flush: true)
        bids = [new Bid(amount: 1000, bidder: winner, dateCreated: new Date()),
                new Bid(amount: 1000.50, bidder: winner, dateCreated: new Date())] as Set
        bids.each {b->b.listing=ended1; b.save(flush: true, validate:false)}

        //create and save ended listing 2
        def ended2 =  TestUtility.getValidListingForCustomer(seller, winner);
        ended2.wasNotificationSent=false;
        ended2.endDateTime = new Date()-1
        ended2.name = "Chohibas-ended"
        ended2.save(validate: false, flush: true)
        bids = [new Bid(amount: 1000, bidder: winner, dateCreated: new Date()),
                new Bid(amount: 1000.50, bidder: winner, dateCreated: new Date())] as Set
        bids.each {b->b.listing=ended2; b.save(flush: true, validate:false)}

        //create and save not ended
        def notEnded = TestUtility.getValidListingForCustomer(seller, winner);
        notEnded.name= "notended"
        notEnded.endDateTime=new Date()+1
        notEnded.wasNotificationSent=false

        notEnded.save(validate: false, flush: true)
        bids = [new Bid(amount: 1000, bidder: winner, dateCreated: new Date()),
                new Bid(amount: 1000.50, bidder: winner, dateCreated: new Date())] as Set
        bids.each {b->b.listing=notEnded; b.save(flush: true, validate:false)}


        Listing.getAll().each {Assume.assumeTrue !it.wasNotificationSent}
        def svc = new ListingNotificationService()
        //act
        svc.sendListingEndedNotifications();

        Listing.findAll {name=="Brewskies-ended" || name=="Chohibas-ended"}
                .each {Assert.assertTrue it.wasNotificationSent}
        Assert.assertFalse Listing.get(notEnded.id).wasNotificationSent
    }

    @Test
    void test_sendListingEndedNotifications_WhenMessageHasBeenSent_JMSMessageNotSent() {

        //The ctrl+c / ctrl+v re-usability pattern heard of it? :)  Not very DRY sorry...
        //arrange
        //create and save ended listing 1
        def ended1 = TestUtility.getValidListingForCustomer(seller, winner);
        ended1.wasNotificationSent=false;
        ended1.endDateTime = new Date()-1
        ended1.name = "Brewskies-ended"
        ended1.save(validate: false, flush: true)
        bids = [new Bid(amount: 1000, bidder: winner, dateCreated: new Date()),
                new Bid(amount: 1000.50, bidder: winner, dateCreated: new Date())] as Set
        bids.each {b->b.listing=ended1; b.save(flush: true, validate:false)}

        //create and save ended listing 2
        def ended2 =  TestUtility.getValidListingForCustomer(seller, winner);
        ended2.wasNotificationSent=false;
        ended2.endDateTime = new Date()-1
        ended2.name = "Chohibas-ended"
        ended2.save(validate: false, flush: true)
        bids = [new Bid(amount: 1000, bidder: winner, dateCreated: new Date()),
                new Bid(amount: 1000.50, bidder: winner, dateCreated: new Date())] as Set
        bids.each {b->b.listing=ended2; b.save(flush: true, validate:false)}

        Listing.findAll()
                .each {Assume.assumeTrue !it.wasNotificationSent}

        def svc = new ListingNotificationService()
        svc.sendListingEndedNotifications();

        Listing.findAll {name=="Brewskies-ended" || name=="Chohibas-ended"}
                .each {Assert.assertTrue it.wasNotificationSent}
    }

    @Test
    void test_sendListingEndedNotifications_WhenMessageHasBeenSent_ListingNotificationSentSetToTrue() {
        //arrange
        def alreadySent = TestUtility.getValidListingForCustomer(seller, winner);
        alreadySent.wasNotificationSent=false;
        alreadySent.endDateTime = new Date()-1
        alreadySent.name = "Brewskies-ended"
        alreadySent.save(validate: false, flush: true)
        bids = [new Bid(amount: 1000, bidder: winner, dateCreated: new Date()),
                new Bid(amount: 1000.50, bidder: winner, dateCreated: new Date())] as Set
        bids.each {b->b.listing=alreadySent; b.save(flush: true, validate:false)}


        Assume.assumeTrue(!alreadySent.wasNotificationSent)
        Assume.assumeTrue(alreadySent.isEnded())
        def svc = new ListingNotificationService()

        //act
        svc.sendListingEndedNotifications();
        //assert
        alreadySent = Listing.get(alreadySent.id)

        Assert.assertTrue alreadySent.wasNotificationSent
    }

    @Test
    void test_sendListingEndedNotifications_WhenWinnerIsNotSet_SetWinner() {
        //arrange
        def alreadySent = TestUtility.getValidListingForCustomer(seller, null);

        Customer winner1 = TestUtility
                .makeCustomer("winner1", "secret", "EdgeCases1234@groups.live.com" )
                .save(validate: false,flush:true)

        alreadySent.wasNotificationSent=false;
        alreadySent.name = "Brewskies-ended"
        alreadySent.save(flush: true)

        alreadySent.addToBids(new Bid(listing: alreadySent, bidder: winner, amount: 1000).save(validate: false, flush: true))
        alreadySent.addToBids(new Bid(listing: alreadySent, bidder: winner1, amount: 1000.50).save(validate: false, flush: true))

        alreadySent.endDateTime = new Date()-1
        alreadySent.save(validate: false, flush: true)

        Assume.assumeTrue(!alreadySent.wasNotificationSent)
        Assume.assumeTrue(alreadySent.isEnded())
        def svc = new ListingNotificationService()

        //act
        svc.sendListingEndedNotifications();
        //assert
        alreadySent = Listing.get(alreadySent.id)
        Assert.assertTrue "winner1"==alreadySent.winner.username
    }
}


