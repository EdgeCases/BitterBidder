package bitterbidder

import grails.plugin.mail.MailService
import grails.converters.deep.JSON
import grails.converters.deep.XML


class EmailService {

    def mailService
    boolean transactional = false
    static exposes = ['jms']
    static destination = "queue.listingended"
    def onMessage(it){
        def listingxml = it.toString()
        mailService.sendMail {
            to "EdgeCases@groups.live.com"
            from "bitterbidderdev@gmail.com"
            subject "You are a winner!"
            body EmailMessageHelper.MakeListingWinnerMessage(listingxml)
        }

        println "sent " + listingxml
    }
}
//to "fred@g2one.com","ginger@g2one.com"
//from "john@g2one.com"
//cc "marge@g2one.com", "ed@g2one.com"
//bcc "joe@g2one.com"
//subject "Hello John"
//body 'this is some text'