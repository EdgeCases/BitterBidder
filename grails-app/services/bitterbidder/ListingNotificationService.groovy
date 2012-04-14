package bitterbidder

import grails.converters.deep.XML
import org.apache.commons.logging.LogFactory


class ListingNotificationService {
    private static final log = LogFactory.getLog(this)
    def callBack
    def listingEndedQueue = "queue.listingended";

    def sendListingEndedNotification(Listing listing) {
        listing.latestBid = listing?.bids?.max {it->it.dateCreated}
        def messageString = (listing as XML).toString()
        log.info "sending jms message to queue: $listingEndedQueue for listing: $listing.name"
        sendJMSMessage(listingEndedQueue, messageString)
        log.info "sent jms message for $listing.name"
        listing.wasNotificationSent=true;
        //don't care about this since this listing is already ended it was probably valid anyways.
        listing.save(validate:false);
        log.info "saved: $listing.name"
    }
    def sendListingEndedNotifications(){
        log.info "searching for ended listings..."
        def now =new Date();
        def listings = Listing.findAllByEndDateTimeLessThanAndWasNotificationSent(now, false);
        listings.each {sendListingEndedNotification(it)}
    }

}
