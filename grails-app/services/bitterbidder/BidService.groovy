package bitterbidder

import grails.validation.ValidationException

class BidService {

    def listingService
    //SRV-3: Create a Grails service method that supports creating a new bid for a listing (unit test)
    def Create(Bid bid) {

        if (listingService.hasListingEnded(bid.listing.id)){
            throw new ValidationException("Sorry this listing has ended", bid.errors)
        }

        if(bid.validate()) {
            return bid.save(flush: true)
        }
    }

    //SRV-3: Create a Grails service method that supports creating a new bid for a listing (unit test)
    //todo - eval if we need this one at all
    def createBidForListing(Listing listing, Customer bidder, int amount) {

        Bid bid = new Bid()

        bid.listing = listing
        bid.bidder = bidder
        bid.amount = amount

        if (listingService.hasListingEnded(listing.id)){
            throw new ValidationException("Sorry this listing has ended", bid.errors)
        }
        if(bid.validate()) {

            return bid.save(flush: true)
        }

        return bid
    }
}
