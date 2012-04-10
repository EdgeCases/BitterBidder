package bitterbidder

/**
 * Created with IntelliJ IDEA.
 * User: dalcantara
 * Date: 4/8/12
 * Time: 1:52 PM
 * To change this template use File | Settings | File Templates.
 */
class EmailMessageHelper {

    def static MakeListingWinnerMessage(listing){

//        look into this:  XMLParsingParameterCreationListener.groovy. & XmlSlurper()

//        def winningBidAmount = listing.latestBid.amount;
//        def winner = listing.winner.username
//        def seller = listing.seller.username
//        //TODO: Don't know how else make the test pass
//        def message = 'Congratulations' + winner + 'you are the winner of' + listing.description + '! Please send your payment of \$' + winningBidAmount + 'to:' + listing.seller.emailAddress
//        return message.toString();
        return listing
    }
}
