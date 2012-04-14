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
        def listing = new XmlSlurper().parseText(listingxml)
        def winner = listing."winner"."emailAddress".text()

        mailService.sendMail {
            to winner
            from "bitterbidderdev@gmail.com"
            subject "You are a winner!"
            body EmailMessageHelper.MakeListingWinnerMessage(listingxml)
        }
        return null
    }
}