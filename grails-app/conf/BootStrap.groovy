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


        def john = new Customer(username: "john", enabled: true, emailAddress: "john@email.com", password: "password").save(flush: true)
        def larry = new Customer(username: "larry", enabled: true, emailAddress: "EdgeCases@groups.live.com", password: "password").save(flush: true)
        def adam = new Customer(username: "adam", enabled: true, emailAddress: "adam@email.com", password: "password").save(flush: true)
        def eric = new Customer(username: "eric", enabled: true, emailAddress: "bitterbidderdev@gmail.com", password: "password").save(flush: true)
        def danny = new Customer(username: "danny", enabled: true, emailAddress: "danny.alcantara@gmail.com", password: "password").save(flush: true)
        def customer6 = new Customer(username: "mike", enabled: true, emailAddress: "mike@email.com", password: "password").save(flush: true)
        def mike = new Customer(username: "michael", enabled: true, emailAddress: "michael_gressman@yahoo.com", password: "password").save(flush: true)
        def customer8 = new Customer(username: "jeff", enabled: true, emailAddress: "jeff@email.com", password: "password").save(flush: true)
        def customer9 = new Customer(username: "todd", enabled: true, emailAddress: "todd@email.com", password: "password").save(flush: true)
        def customer10 = new Customer(username: "infoguy12", enabled: true, emailAddress: "infoguy12@email.com", password: "password").save(flush: true)
        def customer11 = new Customer(username: "myBids1", enabled: true, emailAddress: "myBids1@email.com", password: "password").save(flush: true)
        def customer12 = new Customer(username: "bidder", enabled: true, emailAddress: "bidder@email.com", password: "password").save(flush: true)
        def customer13 = new Customer(username: "administrator", enabled: true, emailAddress: "administrator@bitterbidder.com", password: "special").save(flush: true)

        CustomerRole.create(john, userRole, true)
        CustomerRole.create(larry, userRole, true)
        CustomerRole.create(adam, userRole, true)
        CustomerRole.create(eric, userRole, true)
        CustomerRole.create(danny, userRole, true)
        CustomerRole.create(customer6, userRole, true)
        CustomerRole.create(mike, userRole, true)
        CustomerRole.create(customer8, userRole, true)
        CustomerRole.create(customer9, userRole, true)
        CustomerRole.create(customer10, userRole, true)
        CustomerRole.create(customer11, userRole, true)
        CustomerRole.create(customer12, userRole, true)
        CustomerRole.create(customer13, adminRole, true)

        def soonEnding = new Date()
        soonEnding.setMinutes(soonEnding.minutes+2)
        def cars = new Listing(name: "Cars", startingPrice: 1.25, endDateTime: soonEnding, description: "Cars the movie", seller: john, winner: larry, wasNotificationSent: false).save(flush: true)
        def Iphone = new Listing(name: "Iphone", startingPrice: 2.25, endDateTime: new Date(), description: "Most overpriced device", seller: eric, winner: danny, wasNotificationSent: false).save(flush: true)
        def cellphone = new Listing(name: "cell", startingPrice: 3.25, endDateTime: new Date(), description: "for sale", seller: danny, winner: eric, wasNotificationSent: false).save(flush: true)

        soonEnding = new Date()
        soonEnding.setMinutes(soonEnding.minutes+3)
        def shoes = new Listing(name: "shoes", startingPrice: 5.25, endDateTime: soonEnding, description: "chuck Ts - ends soon", seller: danny , wasNotificationSent: false).save(flush: true)

        soonEnding = new Date()
        soonEnding.setMinutes(soonEnding.minutes+5)
        def cohibas = new Listing(name: "cigars", startingPrice: 9.25, endDateTime: soonEnding, description: "cohibas - ends soon", seller: mike , wasNotificationSent: false).save(flush: true)

        def wiskey = new Listing(name: "wiskey", startingPrice: 10.25, endDateTime: soonEnding, description: "Johnny Gold - get it now", seller: danny , wasNotificationSent: false).save(flush: true)


        def listing4 = new Listing(name: "docs", startingPrice: 4.25, endDateTime: new Date() + 1, description: "ww2 blue prints", seller: eric , wasNotificationSent: false).save(flush: true)

        def listing6 = new Listing(name: "food", startingPrice: 6.25, endDateTime: new Date() + 3, description: "Free Soup", seller: customer6 , wasNotificationSent: false).save(flush: true)
        def listing7 = new Listing(name: "Dogs", startingPrice: 7.25, endDateTime: new Date() + 4, description: "chocolate lab", seller: mike , wasNotificationSent: false).save(flush: true)
        def listing8 = new Listing(name: "wine", startingPrice: 8.25, endDateTime: new Date() + 5, description: "case of red blends", seller: customer8 , wasNotificationSent: false).save(flush: true)

        def listing11 = new Listing(name: "rum", startingPrice: 11.25, endDateTime: new Date() + 8, description: "Jamaican rum", seller: customer11 , wasNotificationSent: false).save(flush: true)
        def listing12 = new Listing(name: "brews", startingPrice: 12.25, endDateTime: new Date() + 9, description: "Stella Artois!", seller: customer12 , wasNotificationSent: false).save(flush: true)
        def listing13 = new Listing(name: "test1", startingPrice: 16.25, endDateTime: new Date() + 10, description: "Description 1", seller: john, wasNotificationSent: false).save(flush: true)
        def listing14 = new Listing(name: "test2", startingPrice: 17.25, endDateTime: new Date() + 11, description: "Description 2", seller: john, wasNotificationSent: false).save(flush: true)
        def listing15 = new Listing(name: "test3", startingPrice: 18.25, endDateTime: new Date() + 12, description: "Description 3", seller: john, wasNotificationSent: false).save(flush: true)
        def listing16 = new Listing(name: "test4", startingPrice: 19.25, endDateTime: new Date() + 13, description: "Description 4", seller: john, wasNotificationSent: false).save(flush: true)
        def listing17 = new Listing(name: "test5", startingPrice: 20.25, endDateTime: new Date() + 14, description: "Description 5", seller: john, wasNotificationSent: false).save(flush: true)
        def listing18 = new Listing(name: "test6", startingPrice: 21.25, endDateTime: new Date() + 15, description: "Description 6", seller: john, wasNotificationSent: false).save(flush: true)

        soonEnding = new Date()
        soonEnding.setMinutes(soonEnding.minutes+1)
        def mercedez = new Listing(name: "mercedez", startingPrice: 3000.25, endDateTime: soonEnding, description: "Pimped out Mercedez - ending soon", seller: mike , wasNotificationSent: false).save(flush: true)

        def bid1 = new Bid(amount: 22.00, listing: listing12, bidder: john, dateCreated: new Date()).save(flush: true)
        def bid2 = new Bid(amount: 23.00, listing: listing12, bidder: john, dateCreated: new Date()).save(flush: true)
        def bid3 = new Bid(amount: 25.00, listing: listing12, bidder: larry, dateCreated: new Date()).save(flush: true)

        def bid4 = new Bid(amount: 220.00, listing: cars, bidder: john, dateCreated: new Date()).save(flush: true)
        def bid5 = new Bid(amount: 230.00, listing: cars, bidder: john, dateCreated: new Date()).save(flush: true)
        def bid6 = new Bid(amount: 250.00, listing: cars, bidder: larry, dateCreated: new Date()).save(flush: true)

        def bid7 = new Bid(amount: 220.00, listing: Iphone, bidder: john, dateCreated: new Date()).save(flush: true)
        def bid8 = new Bid(amount: 230.00, listing: Iphone, bidder: john, dateCreated: new Date()).save(flush: true)
        def bid9 = new Bid(amount: 250.00, listing: Iphone, bidder: danny, dateCreated: new Date()).save(flush: true)

    }
    def destroy = {
    }
}
