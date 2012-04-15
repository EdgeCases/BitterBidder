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
        def listing = new XmlSlurper().parseText(listingXML)
        def winner = listing."winner"."username".text()
        def description = listing."description".text()
        def amount = listing."winningPrice".text()
        def listingId = listing."listingId".text()
        def listingUrl = "http://localhost:8080/BitterBidder/listing/show/" + listingId
        def message = "Congratulations $winner, You are the winner of $description! Please send your payment of \$$amount to us.  You can view the details here $listingUrl"

        return message.toString();
    }

    def static MakeListingSellerMessage(listingXML){

        def listing = new XmlSlurper().parseText(listingXML)
        def description = listing."description".text()
        def amount = listing."winningPrice".text()
        def sellerName = listing."seller"."username".text()
        def listingId = listing."listingId".text()
        def listingUrl = "http://localhost:8080/BitterBidder/listing/show/" + listingId
        def message = "Congratulations $sellerName, item $description sold for \$$amount your can the details here: $listingUrl"
        return message.toString();
    }
}
