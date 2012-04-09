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

    static hasMany = [bids: Bid] // B-4
    static belongsTo = [seller: Customer]

    Boolean wasNotificationSent=false
    Bid latestBid
    static transients = ['latestBid', 'isEnded']

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

    def isEnded(){
        def now = new Date()
        return now.after(endDateTime)
    }

    static namedQueries = {
        findListingsEndingInTheFuture {
            def now = new Date();
            gt 'endDateTime', now
        };
    }

    def static fromXML(listingString){

        def bidSet = new ArrayList<Bid>()
        def root = new XmlSlurper().parseText(listingString)

        def sellerNode = root.seller
        def seller = new Customer(name:sellerNode.@username)

        def nodes = root.bids


        root.listing.bids.each {
            bid -> bidSet.add(new Bid(bid.attributes()))
        }

       // listing.bids = bidSet
        return new Listing()
    }
}
