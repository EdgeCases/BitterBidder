package bitterbidder

/**
 * Created with IntelliJ IDEA.
 * User: dalcantara
 * Date: 4/8/12
 * Time: 1:52 PM
 * To change this template use File | Settings | File Templates.
 */
class EmailMessageHelper {

    def static MakeListingWinnerMessage(listingXML){

//        look into this:  XMLParsingParameterCreationListener.groovy. & XmlSlurper()
        def listing = new XmlSlurper().parseText(listingXML)

        def winner = listing."winner"."username".text()
        def description = listing."description".text()
        //def amount = listing.bids.bid[listing.bids.size()].amount.text()    //todo - this isn't quite right
        def amount = listing."startingPrice".text()
        def sellerEmail = listing."seller"."emailAddress".text()

        def message = "Congratulations $winner, You are the winner of $description! Please send your payment of \$$amount to: $sellerEmail"

        return message.toString();
    }
}
