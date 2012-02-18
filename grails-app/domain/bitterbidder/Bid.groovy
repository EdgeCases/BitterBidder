package bitterbidder

import org.springframework.validation.ObjectError

class Bid {
    Float amount
    Date createdDate

    static  belongsTo = [listing:Listing, bidder: Customer]

    static constraints = {
        // B-5: The Bid amount must be at least .50 higher than the previous Bid for the same listing
        //Only validate new bids (when id is null) and skip validation on the first bid
        amount(validator: { amount, bid ->
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

    def beforeInsert() {
        def latest = Listing.findById(listing.id);
        latest.addToBids(this)
        def isvalid = latest.validate()
        if (!isvalid) {
            if (latest.errors.hasFieldErrors("bids")){
                //We need to figure out a better way to handle this.  maybe write a error in the
                //listing object and skip the saving.
                throw new IllegalStateException("cannot add bid with amount less than threshold")
//                listing.errors.getAllErrors().add(new ObjectError("bid", "the bid is invalid"))
//                listing.removeFromBids(this)
            }
        }
    }

}
