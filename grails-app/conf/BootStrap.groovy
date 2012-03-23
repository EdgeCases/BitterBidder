import  bitterbidder.Bid
import bitterbidder.Customer
import bitterbidder.Listing

class BootStrap {

    def init = { servletContext ->
        def customer1 = new Customer(emailAddress: "john@email.com", password: "password").save()
        def customer2 = new Customer(emailAddress: "larry@email.com", password: "password").save()
        def customer3 = new Customer(emailAddress: "adam@email.com", password: "password").save()
        def customer4 = new Customer(emailAddress: "eric@email.com", password: "password").save()
        def customer5 = new Customer(emailAddress: "danny@email.com", password: "password").save()
        def customer6 = new Customer(emailAddress: "mike@email.com", password: "password").save()
        def customer7 = new Customer(emailAddress: "michael@email.com", password: "password").save()
        def customer8 = new Customer(emailAddress: "jeff@email.com", password: "password").save()
        def customer9 = new Customer(emailAddress: "todd@email.com", password: "password").save()
        def customer10 = new Customer(emailAddress: "infoguy12@email.com", password: "password").save()
        def customer11 = new Customer(emailAddress: "myBids1@email.com", password: "password").save()
        def customer12 = new Customer(emailAddress: "bidder@email.com", password: "password").save()

        def listing1 = new Listing(name: "Cars", startingPrice: 1.25, endDateTime: new Date(), description: "Cars the movie", seller: customer1).save()
        def listing2 = new Listing(name: "Iphone", startingPrice: 2.25, endDateTime: new Date(), description: "Most overpriced device", seller: customer2).save()
        def listing3 = new Listing(name: "cell", startingPrice: 3.25, endDateTime: new Date(), description: "for sale", seller: customer3).save()
        def listing4 = new Listing(name: "docs", startingPrice: 4.25, endDateTime: new Date() + 1, description: "ww2 blue prints", seller: customer4).save()
        def listing5 = new Listing(name: "shoes", startingPrice: 5.25, endDateTime: new Date() + 2, description: "chuck Ts", seller: customer5).save()
        def listing6 = new Listing(name: "food", startingPrice: 6.25, endDateTime: new Date() + 3, description: "Free Soup", seller: customer6).save()
        def listing7 = new Listing(name: "Dogs", startingPrice: 7.25, endDateTime: new Date() + 4, description: "chocolate lab", seller: customer7).save()
        def listing8 = new Listing(name: "wine", startingPrice: 8.25, endDateTime: new Date() + 5, description: "case of red blends", seller: customer8).save()
        def listing9 = new Listing(name: "cigars", startingPrice: 9.25, endDateTime: new Date() + 6, description: "cohibas", seller: customer9).save()
        def listing10 = new Listing(name: "wiskey", startingPrice: 10.25, endDateTime: new Date() + 7, description: "Johnny Gold", seller: customer10).save()
        def listing11 = new Listing(name: "rum", startingPrice: 11.25, endDateTime: new Date() + 8, description: "Jamaican rum", seller: customer11).save()
        def listing12 = new Listing(name: "brews", startingPrice: 12.25, endDateTime: new Date() + 9, description: "Stella Artois!", seller: customer12).save()
        
        def bid1 = new Bid(amount: 22.00, listing: listing12, bidder: customer1, dateCreated: new Date()).save()
        def bid2 = new Bid(amount: 23.00, listing: listing12, bidder: customer1, dateCreated: new Date()).save()
        def bid3 = new Bid(amount: 25.00, listing: listing12, bidder: customer2, dateCreated: new Date()).save()
    }
    def destroy = {
    }
}
