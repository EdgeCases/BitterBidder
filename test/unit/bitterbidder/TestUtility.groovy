package bitterbidder

/**
 * Created with IntelliJ IDEA.
 * User: dalcantara
 * Date: 4/2/12
 * Time: 11:02 PM
 * To change this template use File | Settings | File Templates.
 */
 class TestUtility {
    def static getValidListing() {
        //Setup logic here
        def validCustomer = new Customer(emailAddress: "validguy@valid.com", password: "secret", username: "validguy");

        def springSecurityService = new Object()
        springSecurityService.metaClass.encodePassword = {String password -> "ENCODED_PASSWORD"}

        validCustomer.springSecurityService = springSecurityService

        def bidSet = [new Bid(amount: 10, bidder: validCustomer, dateCreated: new Date()),
                new Bid(amount: 10.50, bidder: validCustomer, dateCreated: new Date())] as Set

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

    def static getValidCustomer() {

        return  new Customer(emailAddress: "validguy@valid.com", password: "secret", username: "validguy");
    }
}
