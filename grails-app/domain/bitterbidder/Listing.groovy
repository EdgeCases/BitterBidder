package bitterbidder

class Listing {
    public static final Float MINIMUM_BID_INCREMENT = 0.5

    String description
    Date endDateTime
    String name
    Float startingPrice
    Customer winner

    static hasMany = [bids:Bid] // B-4

    static belongsTo = [seller: Customer]

    static constraints = {
        description (nullable: true, size: 0..255)
        name (size: 1..63, empty:false, blank: false)
        winner (nullable: true)
        endDateTime(min: new Date());
    }
}
