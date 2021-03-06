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

    def static getListing(){
        return new Listing(
                description: "A test listing",
                endDateTime: new Date()+1,
                name: "Default",
                startingPrice: 10,
                wasNotificationSent: false
        );
    }
    def static getValidListing() {
        def listing = getListing()
        //Setup logic here
        def validCustomer = new Customer(emailAddress: "EdgeCases123@groups.live.com", password: "secret", username: "validguy");
        def springSecurityService = new Object()
        springSecurityService.metaClass.encodePassword = {String password -> "ENCODED_PASSWORD"}
        validCustomer.springSecurityService = springSecurityService
        listing.seller = validCustomer;
        listing.winner = validCustomer;
        return listing
    }

    def static getValidListingWithBids(){

        def bidSet = [new Bid(amount: 10, bidder: validCustomer, dateCreated: new Date()),
                new Bid(amount: 10.50, bidder: validCustomer, dateCreated: new Date())] as Set
        return getValidListingWithBids(validCustomer, bidSet)
    }

    def static getValidListingForCustomer(seller, winner){
        def listing = getValidListing()
        listing.seller =seller
        listing.winner = winner
        return listing
    }

    def static getValidListingWithBids(customer, bids){
        def listing = getValidListing()
        listing.seller = customer
        listing.winner = customer
        listing.bids = bids
        return listing
    }

    def static getValidCustomer() {

        return  new Customer(emailAddress: "EdgeCases143@groups.live.com", password: "secret", username: "validguy");
    }

    def static makeCustomer(String username, String password, String email){
        def params = [:]
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