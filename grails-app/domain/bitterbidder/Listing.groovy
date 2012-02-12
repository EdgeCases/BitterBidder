package bitterbidder

class Listing {
    String description
    Date endDateTime
    String name
    Customer seller
    Float startingPrice
    Customer winner

    static hasMany = [bids:Bid]

    static constraints = {
        description (nullable: true, size: 0..255)
        name (size: 1..63)
        winner (nullable: true)
    }
}
