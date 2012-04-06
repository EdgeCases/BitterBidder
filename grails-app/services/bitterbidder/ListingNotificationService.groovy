package bitterbidder

import grails.converters.JSON

class ListingNotificationService {

    String listingEndedQueue = "queue.listingended";

    def sendListingEndedNotification(Listing listing) {
        def message = (listing as JSON).toString()
        sendJMSMessage listingEndedQueue, message
        listing.wasNotificationSent=true;
        //don't care about this since this listing is already ended it was probably valid anyways.
        listing.save(false);
    }
    def sendListingEndedNotifications(){
        def now =new Date();
        def listings = Listing.findAllByEndDateTimeLessThanAndWasNotificationSent(now, false);
        listings.each {sendListingEndedNotification(it)}
    }

}
