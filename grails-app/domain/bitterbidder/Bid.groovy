package bitterbidder

import org.springframework.validation.ObjectError

class Bid {
    Float amount
    Date dateCreated

    static  belongsTo = [listing:Listing, bidder: Customer]

    static constraints = {
        // B-5: The Bid amount must be at least .50 higher than the previous Bid for the same listing
        amount(validator: { amount, bid ->
            // only validate new bids (when id is null) and skip validation on the first bid
            if (bid.listing.bids && bid.id==null) {
                if (bid.listing.bids.size()>1){
                    def maxCurrentBid = bid.listing.bids.max({b->b.id!=null}).amount
                    return amount - maxCurrentBid >= bid.listing.MINIMUM_BID_INCREMENT
                }
                return amount - bid.listing.startingPrice >= bid.listing.MINIMUM_BID_INCREMENT
            }
            else{
                return amount - bid.listing.startingPrice >= bid.listing.MINIMUM_BID_INCREMENT
            }
        })
    }
}
