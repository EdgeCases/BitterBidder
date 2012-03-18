import  bitterbidder.Bid
import bitterbidder.Customer
import bitterbidder.Listing

class BootStrap {

    def init = { servletContext ->
        def customer1 = new Customer(emailAddress: "customer1@email.com", password: "password").save()
        def customer2 = new Customer(emailAddress: "customer2@email.com", password: "password").save()
        def customer3 = new Customer(emailAddress: "customer3@email.com", password: "password").save()
        def customer4 = new Customer(emailAddress: "customer4@email.com", password: "password").save()
        def customer5 = new Customer(emailAddress: "customer5@email.com", password: "password").save()
        def customer6 = new Customer(emailAddress: "customer6@email.com", password: "password").save()
        def customer7 = new Customer(emailAddress: "customer7@email.com", password: "password").save()
        def customer8 = new Customer(emailAddress: "customer8@email.com", password: "password").save()
        def customer9 = new Customer(emailAddress: "customer9@email.com", password: "password").save()
        def customer10 = new Customer(emailAddress: "customer10@email.com", password: "password").save()
        def customer11 = new Customer(emailAddress: "customer11@email.com", password: "password").save()
        def customer12 = new Customer(emailAddress: "customer12@email.com", password: "password").save()

        def listing1 = new Listing(name: "Listing1", startingPrice: 1.25, endDateTime: new Date(), description: "Description1", seller: customer1).save()
        def listing2 = new Listing(name: "Listing2", startingPrice: 2.25, endDateTime: new Date(), description: "Description2", seller: customer2).save()
        def listing3 = new Listing(name: "Listing3", startingPrice: 3.25, endDateTime: new Date(), description: "Description3", seller: customer3).save()
        def listing4 = new Listing(name: "Listing4", startingPrice: 4.25, endDateTime: new Date() + 1, description: "Description4", seller: customer4).save()
        def listing5 = new Listing(name: "Listing5", startingPrice: 5.25, endDateTime: new Date() + 2, description: "Description5", seller: customer5).save()
        def listing6 = new Listing(name: "Listing6", startingPrice: 6.25, endDateTime: new Date() + 3, description: "Description6", seller: customer6).save()
        def listing7 = new Listing(name: "Listing7", startingPrice: 7.25, endDateTime: new Date() + 4, description: "Description7", seller: customer7).save()
        def listing8 = new Listing(name: "Listing8", startingPrice: 8.25, endDateTime: new Date() + 5, description: "Description8", seller: customer8).save()
        def listing9 = new Listing(name: "Listing9", startingPrice: 9.25, endDateTime: new Date() + 6, description: "Description9", seller: customer9).save()
        def listing10 = new Listing(name: "Listing10", startingPrice: 10.25, endDateTime: new Date() + 7, description: "Description10", seller: customer10).save()
        def listing11 = new Listing(name: "Listing11", startingPrice: 11.25, endDateTime: new Date() + 8, description: "Description11", seller: customer11).save()
        def listing12 = new Listing(name: "Listing12", startingPrice: 12.25, endDateTime: new Date() + 9, description: "Description12", seller: customer12).save()
        
        def bid1 = new Bid(amount: 22.00, listing: listing12, bidder: customer1, dateCreated: new Date()).save()
        def bid2 = new Bid(amount: 23.00, listing: listing12, bidder: customer1, dateCreated: new Date()).save()
        def bid3 = new Bid(amount: 25.00, listing: listing12, bidder: customer2, dateCreated: new Date()).save()
    }
    def destroy = {
    }
}
