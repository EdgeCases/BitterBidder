package bitterbidder

import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut
import org.codehaus.jackson.annotate.JsonAnySetter
import grails.converters.deep.JSON

class Listing {

    public static final Float MINIMUM_BID_INCREMENT = 0.5

    Date dateCreated
    String description
    Date endDateTime
    String name
    Float startingPrice
    Customer winner
    Float winningPrice
    String listingId

    static hasMany = [bids: Bid] // B-4
    static belongsTo = [seller: Customer]
   // static hasOne =[winner:Customer]

    Boolean wasNotificationSent=false
    Bid latestBid
    Float minimumBid

    static transients = ['latestBid', 'isEnded', 'minimumBid']

    static constraints = {
        description (nullable: true, blank: false, empty:false, size: 0..255)
        name (size: 1..63, empty:false, blank: false)
        endDateTime(min: new Date());
        winningPrice(nullable: true, )
        listingId(nullable: true, blank: true, empty:true)
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

    def isEnded(){
        def now = new Date()
        return now.after(endDateTime)
    }

    def getHighestBidAmount(){
        def amount = bids?.max{it->it.amount}?.amount
        return amount
    }

    static namedQueries = {
        findListingsEndingInTheFuture {
            def now = new Date();
            gt 'endDateTime', now
        };
    }
}
