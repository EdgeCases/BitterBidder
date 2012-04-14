package bitterbidder

import grails.converters.deep.XML
import org.apache.commons.logging.LogFactory


class ListingNotificationService {
    private static final log = LogFactory.getLog(this)
    def callBack
    def listingEndedQueue = "queue.listingended";

    def sendListingEndedNotification(Listing listing) {

        if (listing.bids!=null && listing.bids.size()>0){
            def winner = listing.bids.max{b->b.amount}.bidder
            listing.winner = winner;
        }else{
            listing.winner =listing.seller
        }

        listing.latestBid = listing?.bids?.max {it->it.dateCreated}
        def messageString = (listing as XML).toString()
        log.info "sending jms message to queue: $listingEndedQueue for listing: $listing.name"
        sendJMSMessage(listingEndedQueue, messageString)
        log.info "sent jms message for $listing.name"
        listing.wasNotificationSent=true;
        //don't care about this since this listing is already ended it was probably valid anyways.
        listing.save(validate:false, flush: true);
        log.info "saved: $listing.name"
    }
    def sendListingEndedNotifications(){
        log.info "searching for ended listings..."
        def now =new Date();

        def endedListingsQuery = Listing.where {
            wasNotificationSent==false && endDateTime<now && bids.size()>0
        }
        def listings = endedListingsQuery.list()
        listings.each {sendListingEndedNotification(it)}
    }

}
