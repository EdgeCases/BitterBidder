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
        description (nullable: true, blank: true, empty:true, size: 0..255)
        name (size: 1..63, empty:false, blank: false)
        winner (nullable: true)
        endDateTime(min: new Date());
    }
}
