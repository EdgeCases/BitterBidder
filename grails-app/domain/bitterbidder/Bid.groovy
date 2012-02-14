package bitterbidder

class Bid {
    Float amount        // B-1
    Date dateCreated    // B-1

    static  belongsTo = [listing:Listing, bidder: Customer] // B-3, B-6

    static constraints = {
        // B-5: The Bid amount must be at least .50 higher than the previous Bid for the same listing
        amount(validator: { amount, bid ->
            if (bid.listing.bids) {
                def maxCurrentBid = bid.listing.bids.max( { a, b -> a.amount <=> b.amount } as Comparator ).amount
                return amount - maxCurrentBid >= bid.listing.MINIMUM_BID_INCREMENT
            }
            else{
                return amount - bid.listing.startingPrice >= bid.listing.MINIMUM_BID_INCREMENT
            }
        })
    }
}
