package bitterbidder

import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut

class Listing {
    public static final Float MINIMUM_BID_INCREMENT = 0.5
    String name
    Float startingPrice
    Date endDateTime
    String description
    Customer winner
    static hasMany = [bids:Bid]
    static belongsTo = [seller: Customer]


    static constraints = {
        description (nullable: true, blank: false, empty:false, size: 0..255)
        name (size: 1..63, empty:false, blank: false)
        endDateTime(min: new Date());

        seller(validator: {
                            println("validating seller")
                            def isValid = it.validate()
                            println("Seller is"+ isValid.toString())
                            return isValid
                          }
        )

        winner(nullable: true, validator: {
            def isValid =true            
            if (it!=null){
                isValid = it.validate()
            }
            return isValid
        })
    }
}
