package bitterbidder

class Bid {
    Float amount
    Customer bidder
    Date dateCreated  // this property covers the requirement for "date/time of bid" but can be managed by Grails itself each time the object is created just by naming it "dateCreated"
    static  belongsTo = [listing:Listing] // the "belongsTo" keyword satisfies requirement B-6
    static constraints = {
    }
}
