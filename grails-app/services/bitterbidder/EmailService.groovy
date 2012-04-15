package bitterbidder

import grails.plugin.mail.MailService
import grails.converters.deep.JSON
import grails.converters.deep.XML
import org.apache.commons.logging.LogFactory


class EmailService {

    def mailService
    boolean transactional = false
    static exposes = ['jms']
    static destination = "queue.listingended"
    private static final log = LogFactory.getLog(this)

    def onMessage(it){
        def listingxml = it.toString()
        def listing = new XmlSlurper().parseText(listingxml)
        def winnerEmailAddress = listing."winner"."emailAddress".text()
        log.info("sending email to: $winnerEmailAddress")
        mailService.sendMail {
            to winnerEmailAddress
            from "bitterbidderdev@gmail.com"
            subject "You are a winner!"
            body EmailMessageHelper.MakeListingWinnerMessage(listingxml)
        }
        log.info("Email sent!")
        return null
    }
}