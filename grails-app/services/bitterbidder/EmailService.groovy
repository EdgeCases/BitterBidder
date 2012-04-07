package bitterbidder

import grails.plugin.mail.MailService
import grails.converters.JSON

class EmailService {

    def mailService
    boolean transactional = false
    static exposes = ['jms']
    static destination = "queue.listingended"
    def onMessage(it){
        def listing = (JSON.parse(it) as Listing)
        def message = "Congratulations you'vd won: $listing.name!  Please send your payment of usd"
        mailService.sendMail {
            to "EdgeCases@groups.live.com"
            from "bitterbidderdev@gmail.com"
            subject "You are a winner!"
            body message
        }
        println message
    }
}
//to "fred@g2one.com","ginger@g2one.com"
//from "john@g2one.com"
//cc "marge@g2one.com", "ed@g2one.com"
//bcc "joe@g2one.com"
//subject "Hello John"
//body 'this is some text'