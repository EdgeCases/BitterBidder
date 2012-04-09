package bitterbidder

import grails.plugins.springsecurity.SpringSecurityService

/**
 * Created with IntelliJ IDEA.
 * User: dalcantara
 * Date: 4/6/12
 * Time: 10:37 PM
 * To change this template use File | Settings | File Templates.
 */
class TestUtility {
    def static getValidListing() {
        //Setup logic here
        def validCustomer = new Customer(emailAddress: "validguy@valid.com", password: "secret", username: "validguy");

        def springSecurityService = new Object()
        springSecurityService.metaClass.encodePassword = {String password -> "ENCODED_PASSWORD"}

        validCustomer.springSecurityService = springSecurityService

        return new Listing(
                description: "A test listing",
                seller: validCustomer,
                winner: validCustomer,
                endDateTime: new Date()+1,
                name: "Default",
                startingPrice: 10,
                wasNotificationSent: false
        )
    }

    def static getValidListingWithBids(){
        def listing = getValidListing()
        def bidSet = [new Bid(amount: 10, bidder: validCustomer, dateCreated: new Date()),
                new Bid(amount: 10.50, bidder: validCustomer, dateCreated: new Date())] as Set
        listing.latestBid = bidSet.max {it->it.amount}
        listing.bids = bidSet
        return listing
    }
    def static getValidCustomer() {

        return  new Customer(emailAddress: "validguy@valid.com", password: "secret", username: "validguy");
    }

    def static makeCustomer(String username, String password, String email){
        def params = new Object();
        params.username=username
        params.password=password
        params.emailAddress=email
        return makeCustomer(params)
    }

    def static makeCustomer(params){
        def customer = new Customer(params)
        def springSecurityService = new SpringSecurityService()
        springSecurityService.metaClass.encodePassword = {String pwd -> "ENCODED_PASSWORD"}
        customer.springSecurityService = springSecurityService
        return customer
    }

}