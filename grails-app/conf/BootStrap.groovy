import  bitterbidder.Bid
import bitterbidder.Customer
import bitterbidder.Listing
import bitterbidder.Role
import bitterbidder.CustomerRole
import org.springframework.web.servlet.handler.UserRoleAuthorizationInterceptor

class BootStrap {

    def init = { servletContext ->
        def adminRole = new Role(authority: 'ROLE_ADMIN').save(flush: true)
        def userRole = new Role(authority: 'ROLE_USER').save(flush: true)

        def customer1 = new Customer(username: "john", enabled: true, emailAddress: "john@email.com", password: "password").save(flush: true)
        def customer2 = new Customer(username: "larry", enabled: true, emailAddress: "larry@email.com", password: "password").save(flush: true)
        def customer3 = new Customer(username: "adam", enabled: true, emailAddress: "adam@email.com", password: "password").save(flush: true)
        def customer4 = new Customer(username: "eric", enabled: true, emailAddress: "eric@email.com", password: "password").save(flush: true)
        def customer5 = new Customer(username: "danny", enabled: true, emailAddress: "danny@email.com", password: "password").save(flush: true)
        def customer6 = new Customer(username: "mike", enabled: true, emailAddress: "mike@email.com", password: "password").save(flush: true)
        def customer7 = new Customer(username: "michael", enabled: true, emailAddress: "michael@email.com", password: "password").save(flush: true)
        def customer8 = new Customer(username: "jef", enabled: true, emailAddress: "jeff@email.com", password: "password").save(flush: true)
        def customer9 = new Customer(username: "todd", enabled: true, emailAddress: "todd@email.com", password: "password").save(flush: true)
        def customer10 = new Customer(username: "infoguy12", enabled: true, emailAddress: "infoguy12@email.com", password: "password").save(flush: true)
        def customer11 = new Customer(username: "myBids1", enabled: true, emailAddress: "myBids1@email.com", password: "password").save(flush: true)
        def customer12 = new Customer(username: "bidder", enabled: true, emailAddress: "bidder@email.com", password: "password").save(flush: true)
        def customer13 = new Customer(username: "administrator", enabled: true, emailAddress: "administrator@bitterbidder.com", password: "special").save(flush: true)

        CustomerRole.create(customer1, userRole, true)
        CustomerRole.create(customer2, userRole, true)
        CustomerRole.create(customer3, userRole, true)
        CustomerRole.create(customer4, userRole, true)
        CustomerRole.create(customer5, userRole, true)
        CustomerRole.create(customer6, userRole, true)
        CustomerRole.create(customer7, userRole, true)
        CustomerRole.create(customer8, userRole, true)
        CustomerRole.create(customer9, userRole, true)
        CustomerRole.create(customer10, userRole, true)
        CustomerRole.create(customer11, userRole, true)
        CustomerRole.create(customer12, userRole, true)
        CustomerRole.create(customer13, adminRole, true)

        def listing1 = new Listing(name: "Cars", startingPrice: 1.25, endDateTime: new Date(), description: "Cars the movie", seller: customer1, wasNotificationSent: false).save(flush: true)
        def listing2 = new Listing(name: "Iphone", startingPrice: 2.25, endDateTime: new Date(), description: "Most overpriced device", seller: customer2 , wasNotificationSent: false).save(flush: true)
        def listing3 = new Listing(name: "cell", startingPrice: 3.25, endDateTime: new Date(), description: "for sale", seller: customer3 , wasNotificationSent: false).save(flush: true)
        def listing4 = new Listing(name: "docs", startingPrice: 4.25, endDateTime: new Date() + 1, description: "ww2 blue prints", seller: customer4 , wasNotificationSent: false).save(flush: true)
        def listing5 = new Listing(name: "shoes", startingPrice: 5.25, endDateTime: new Date() + 2, description: "chuck Ts", seller: customer5 , wasNotificationSent: false).save(flush: true)
        def listing6 = new Listing(name: "food", startingPrice: 6.25, endDateTime: new Date() + 3, description: "Free Soup", seller: customer6 , wasNotificationSent: false).save(flush: true)
        def listing7 = new Listing(name: "Dogs", startingPrice: 7.25, endDateTime: new Date() + 4, description: "chocolate lab", seller: customer7 , wasNotificationSent: false).save(flush: true)
        def listing8 = new Listing(name: "wine", startingPrice: 8.25, endDateTime: new Date() + 5, description: "case of red blends", seller: customer8 , wasNotificationSent: false).save(flush: true)
        def listing9 = new Listing(name: "cigars", startingPrice: 9.25, endDateTime: new Date() + 6, description: "cohibas", seller: customer9 , wasNotificationSent: false).save(flush: true)
        def listing10 = new Listing(name: "wiskey", startingPrice: 10.25, endDateTime: new Date() + 7, description: "Johnny Gold", seller: customer10 , wasNotificationSent: false).save(flush: true)
        def listing11 = new Listing(name: "rum", startingPrice: 11.25, endDateTime: new Date() + 8, description: "Jamaican rum", seller: customer11 , wasNotificationSent: false).save(flush: true)
        def listing12 = new Listing(name: "brews", startingPrice: 12.25, endDateTime: new Date() + 9, description: "Stella Artois!", seller: customer12 , wasNotificationSent: false).save(flush: true)

        def bid1 = new Bid(amount: 22.00, listing: listing12, bidder: customer1, dateCreated: new Date()).save(flush: true)
        def bid2 = new Bid(amount: 23.00, listing: listing12, bidder: customer1, dateCreated: new Date()).save(flush: true)
        def bid3 = new Bid(amount: 25.00, listing: listing12, bidder: customer2, dateCreated: new Date()).save(flush: true)
    }
    def destroy = {
    }
}
