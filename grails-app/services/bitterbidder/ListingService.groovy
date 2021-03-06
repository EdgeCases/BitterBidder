package bitterbidder

import grails.validation.ValidationException

class ListingService {

    // SRV-2: Create a Grails service method that supports creating a new listing
    def Create(Listing listing) {

        //pattern from: http://grails.org/doc/latest/guide/services.html
        if (!listing.validate()){
            throw new ValidationException("listing is invalid", listing.errors)
        }
        listing = listing.save(flush: true)
        listing.minimumBid = getMinimumBidAmount(listing)
        return listing
    }

    def getMyListings(String userName){
        def result = Listing.findAll("from Listing as l where l.seller.username = ?", [userName])
        return result
    }

    def hasListingEnded(long id){
        def listing = Listing.get(id)
        return listing.isEnded()
    }

    def getMinimumBidAmount(long id){

        def listing = Listing.get(id)

        return getMinimumBidAmount(listing)
    }

    def getMinimumBidAmount(Listing listing){
        def highestAmount = listing.getHighestBidAmount()

        if (highestAmount>0){

            return highestAmount + Listing.MINIMUM_BID_INCREMENT
            //return g.formatNumber(number:price, type:'currency', currencyCode: 'USD')
        }

        //return g.formatNumber(number:listing.startingPrice, type:'currency', currencyCode: 'USD')
        return listing.startingPrice
    }
}
