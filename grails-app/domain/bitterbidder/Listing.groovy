package bitterbidder

class Listing {
    String name
    Float startingPrice
    Date endDateTime
    String description
    Customer winner
    //Customer seller
    static hasMany = [bids:Bid] // B-4
    static belongsTo = [seller: Customer]


    static constraints = {
        description (nullable: true, blank: false, empty:false, size: 0..255)
        name (size: 1..63, empty:false, blank: false)
        endDateTime(min: new Date());
        bids(minSize: 2)
        seller(validator: {val, obj ->
                            println("validating seller")
                            def isValid = obj.seller.validate()
                            println("Seller is"+isValid.toString())
                            return isValid
                          })
        winner(nullable: true, validator: {val, obj ->
            def isValid =true
            if (obj.winner!=null){
                isValid = obj.winner.validate()
            }
            return isValid
        })
    }
}
