package bitterbidder

import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut

class Listing {

    public static final Float MINIMUM_BID_INCREMENT = 0.5

    Date dateCreated
    String description
    Date endDateTime
    String name
    Float startingPrice
    Customer winner

    static hasMany = [bids: Bid] // B-4
    static belongsTo = [seller: Customer]

    Bid latestBid
    static transients = ['latestBid']

    static constraints = {
        description (nullable: true, blank: false, empty:false, size: 0..255)
        name (size: 1..63, empty:false, blank: false)
        endDateTime(min: new Date());

        seller(validator: {
                            def isValid = it.validate()
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

//   static List<Listing> findListingsEndingInTheFuture(){
//        return Listing.findAllByEndDateTimeGreaterThan(new Date())
//    }

    static namedQueries = {
        findListingsEndingInTheFuture {
            def now = new Date();
            gt 'endDateTime', now
        };

    }
}
