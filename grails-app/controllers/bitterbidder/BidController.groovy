package bitterbidder

import org.springframework.dao.DataIntegrityViolationException
import grails.plugins.springsecurity.Secured

class BidController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def bidService

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [bidInstanceList: Bid.list(params), bidInstanceTotal: Bid.count()]
    }

    //UI-1: The listing detail page will asynchronously load and display a list of the last
    //10 bids placed on the item showing the user, date/time, and amount.
    //The implementation of the lookup of these results must be done with a Named Query.
    def lastTen() {

        assert null != params

        //def latest = Bid.getLastTen(params.id).list().sort{(it.amount)}
        def latest = Bid.getLastTen(params.id).list(max: 10, sort: "amount", order: "desc")

        render(contentType: "text/json") {

            amounts = array {

                for(bid in latest) {

                    def bidAmount = g.formatNumber(number:bid.amount, type:'currency', currencyCode: 'USD')
                    def user = bid.bidder.getUserName()
                    def time = g.formatDate(date: bid.dateCreated, timeStyle: "short")
                    xyz amt: bidAmount + " by "+ user + " at " + time
                }
            }
        }
    }

    // S-3: Bidding on an item requires that the user be logged in
    @Secured(['ROLE_USER'])
    def create() {

        //this id is for the listing we're bidding upon
        def id = params.id

        if(id){
            //L-7: The detail page for the listing allows a new bid to be placed
            //if we have an id here, it's the id of a listing and we came from the
            //show view of a listing
           
            //[bidInstance: new Bid(params.id)]
            bidService.Create(new Bid(id))
        }
        else{
            [bidInstance: new Bid(params)]
        }
    }

    def save() {
        def bidInstance = new Bid(params)

        if (!bidInstance.save(flush: true)) {

            //todo-add a good message

            //L-8: Validation errors will be displayed on the listing detail page if an added bid does not pass validation
            flash.message = message(code: 'default.invalid.validator.message', args: [message(code: 'bid.label', default: 'Bid'), bidInstance.id])
            redirect action: "show", controller: "Listing", params: [id: bidInstance.listing.id]

            return
        }

        bidInstance.listing.latestBid = bidInstance

        flash.message = message(code: 'default.created.message', args: [message(code: 'bid.label', default: 'Bid'), bidInstance.id])
        redirect action: "show", controller: "Listing", params: [id: bidInstance.listing.id]
    }

    def show() {
        def bidInstance = Bid.get(params.id)
        if (!bidInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'bid.label', default: 'Bid'), params.id])
            redirect action: "list"
            return
        }

        [bidInstance: bidInstance]
    }

    def edit() {
        def bidInstance = Bid.get(params.id)
        if (!bidInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'bid.label', default: 'Bid'), params.id])
            redirect action: "list"
            return
        }

        [bidInstance: bidInstance]
    }

    def update() {
        def bidInstance = Bid.get(params.id)
        if (!bidInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'bid.label', default: 'Bid'), params.id])
            redirect action: "list"
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (bidInstance.version > version) {
                bidInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'bid.label', default: 'Bid')] as Object[],
                        "Another user has updated this Bid while you were editing")
                render view: "edit", model: [bidInstance: bidInstance]
                return
            }
        }

        bidInstance.properties = params

        if (!bidInstance.save(flush: true)) {
            render view: "edit", model: [bidInstance: bidInstance]
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'bid.label', default: 'Bid'), bidInstance.id])
        redirect(action: "show", id: bidInstance.id)
    }

    def delete() {
        def bidInstance = Bid.get(params.id)
        if (!bidInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'bid.label', default: 'Bid'), params.id])
            redirect(action: "list")
            return
        }

        try {
            bidInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'bid.label', default: 'Bid'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'bid.label', default: 'Bid'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
