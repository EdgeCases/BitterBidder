package bitterbidder

import grails.validation.ValidationException

class ListingService {

    def Create(Listing listing) {

        if (!listing.validate()){
            throw new ValidationException("listing is invalid", listing.errors)
        }

        return listing.save(flush: true);
    }

    def getMyListings(String userName){
        def result = Listing.findAll("from Listing as l where l.seller.username = ?", [userName])
        return result
    }

    def hasListingEnded(long id){
        def listing = Listing.get(id)
        return listing.isEnded()
    }
}
