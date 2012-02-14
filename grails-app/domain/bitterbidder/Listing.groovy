package bitterbidder

class Listing {
    static  final minimumBidIncrement = 0.5

    String description
    Date endDateTime
    String name
    Float startingPrice
    Customer winner

    static hasMany = [bids:Bid] // B-4

    static belongsTo = [seller: Customer]

    static constraints = {
        description (nullable: true, size: 0..255)
        name (size: 1..63)
        winner (nullable: true)
    }
}
