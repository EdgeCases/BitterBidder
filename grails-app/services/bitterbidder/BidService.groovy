package bitterbidder

class BidService {

//    def Create(Bid bid) {
//
//        if (bid.validate()) {
//
//            return bid.save(flush: true)
//        }
//
//        return bid
//    }

    def Create(Bid bid) {

        if(bid.validate()) {

            return bid.save(flush: true)
        }

        [bidInstance: new Bid(params.id)]
    }

    //SRV-3: Create a Grails service method that supports creating a new bid for a listing (unit test)
    def createBidForListing(Listing listing, Customer bidder, int amount) {

        //Bid bid = new Bid(listing.id)
        Bid bid = new Bid()
        bid.listing = listing
        bid.bidder = bidder
        bid.amount = amount

        if(bid.validate()) {

            return bid.save(flush: true)
        }

        return bid
    }
}
