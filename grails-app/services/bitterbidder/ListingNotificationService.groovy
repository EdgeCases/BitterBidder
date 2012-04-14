package bitterbidder

import grails.converters.deep.XML
import grails.plugin.jms.JmsService

class ListingNotificationService {

    def callBack
    def listingEndedQueue = "queue.listingended";

    def sendListingEndedNotification(Listing listing) {
        listing.latestBid = listing?.bids?.max {it->it.dateCreated}
        def messageString = (listing as XML).toString()
        sendJMSMessage(listingEndedQueue, messageString)
        listing.wasNotificationSent=true;
        //don't care about this since this listing is already ended it was probably valid anyways.
        listing.save(false);
    }
    def sendListingEndedNotifications(){
        println "searching for ended listings..."
        def now =new Date();
        def listings = Listing.findAllByEndDateTimeLessThanAndWasNotificationSent(now, false);
        listings.each {sendListingEndedNotification(it)}
    }

}
