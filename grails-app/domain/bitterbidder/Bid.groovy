package bitterbidder

class Bid {
    Float amount        // B-1
    Date dateCreated    // B-1

    static  belongsTo = [listing:Listing, bidder: Customer] // B-3, B-6

    static constraints = {
        amount(validator: { val, obj ->
            def value
        })
    }
}
