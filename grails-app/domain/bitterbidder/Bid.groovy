package bitterbidder

class Bid {
    Float amount
    Date dateCreated
    static  belongsTo = [listing:Listing]
    static hasOne = [bidder:Customer]
    static constraints = {
    }
}
