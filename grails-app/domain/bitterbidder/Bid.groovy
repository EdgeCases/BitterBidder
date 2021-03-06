package bitterbidder

import org.springframework.validation.ObjectError

class Bid {
    Float amount
    Date dateCreated

    static belongsTo = [listing:Listing, bidder: Customer]

    static constraints = {
        // B-5: The Bid amount must be at least .50 higher than the previous Bid for the same listing
        amount(validator: { amount, bid ->

            // only validate new bids (when id is null) and skip validation on the first bid
            if (bid.listing.bids && bid.id == null) {

                if (bid.listing.bids.size() > 0){
                    def maxCurrentBid = bid.listing.bids.max( { a, b -> a.amount <=> b.amount } as Comparator ).amount
                    return amount - maxCurrentBid >= bid.listing.MINIMUM_BID_INCREMENT
                }
                return amount - bid.listing.startingPrice >= bid.listing.MINIMUM_BID_INCREMENT
            }
            else{
                return amount - bid.listing.startingPrice >= bid.listing.MINIMUM_BID_INCREMENT
            }
        })
    }
    
    Bid() {}
    
    Bid(listingId) {

        if(listingId){
            def listing = Listing.get(listingId)
            this.listing = listing
        }
    }

    static namedQueries = {

        getLastTen { id ->
            eq("listing", Listing.findById(id))
            maxResults 10
//            sort("amount")
            order("amount", "desc")
        }
    }
}
