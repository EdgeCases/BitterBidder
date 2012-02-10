package bitterbidder

class Listing {
    String description
    Date endDateTime
    String name
    Float startingPrice
    Customer winner
    static belongsTo = [seller:Customer]
    static hasMany = [bids:Bid]
    static constraints = {
        description (nullable: true, size: 0..255)
        name (size: 1..64)
        winner (nullable: true)
    }
}
