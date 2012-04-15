package bitterbidder

import grails.converters.deep.XML
import org.apache.commons.logging.LogFactory


class ListingNotificationService {
    private static final log = LogFactory.getLog(this)
    def listingEndedQueue = "queue.listingended";

    def sendListingEndedNotification(Listing listing) {

        //check for the highest bid and set the winner...
        //this should probably be in the listings service but that's a 2do for phase IV :)
        if (listing.bids!=null && listing.bids.size()>0){
            def winner = listing.bids.max{b->b.amount}.bidder
            listing.winner = winner;
        }else{
            //Set the winner to the seller and send him the email. Too bad no one bid on your listing.
            listing.winner =listing.seller
        }

        listing.latestBid = listing?.bids?.max {it->it.dateCreated}
        def messageString = (listing as XML).toString()
        log.info "sending jms message to queue: $listingEndedQueue for listing: $listing.name to winner: $listing.winner"
        sendJMSMessage(listingEndedQueue, messageString)
        log.info "sent jms message for $listing.name"
        listing.wasNotificationSent=true;
        //don't care about this since this listing is already ended it was probably valid anyways.
        listing.save(validate:false, flush: true);
        log.info "saved: $listing.name"
    }
    def sendListingEndedNotifications(){
        def now =new Date();

        def endedListingsQuery = Listing.where {
            wasNotificationSent==false && endDateTime<now
        }

        def listings = endedListingsQuery.list()

        log.info("Sending notificationf for: $listings.size() listings")
        listings.each {sendListingEndedNotification(it)}
    }

}
