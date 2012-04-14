package bitterbidder

import org.springframework.dao.DataIntegrityViolationException
import grails.plugins.springsecurity.Secured
import grails.validation.ValidationException
import grails.converters.JSON

class ListingController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def listingService
    def springSecurityService
    def bidService
    
    def index() {
        redirect(action: "list", params: params)
    }

    def addBid(){
        def listingInstance = Listing.get(params.id)
        [listingInstance: listingInstance]
    }

    // S-2: Viewing the main landing page and the listing detail page does not require that the user be logged in
    def list() {
        // M-2: The main landing page shows up to 5 listings at a time
        params.max = Math.min(params.max ? params.int('max') : 5, 100)

        // M-1: The main landing page shows listings sorted by the date they were created (most recent first)
        params.sort = params.sort?:'dateCreated'
        params.order = params.order?:'desc'

        // M-4: Only listings with a end date/time that is in the future are visible on the main page
        def listingInstance = Listing.list(params).findAll {it -> it.endDateTime >= new Date()}

        def totalListings = Listing.findListingsEndingInTheFuture.list();
       // def totalListings = Listing.findAll("from Listing as l where l.endDateTime >= ?", [new Date()])

        [listingInstanceList: listingInstance, listingInstanceTotal: totalListings.size()]
    }

    // S-6: A logged in user can create a new listing via a simple form
    @Secured(['ROLE_USER'])
    def create() {
        [listingInstance: new Listing(params)]
    }

    // S-6: A logged in user can create a new listing via a simple form
    @Secured(['ROLE_USER'])
    def save() {
        def listing = new Listing(params)

        try{
            def customer =springSecurityService.getCurrentUser();
            if (customer==null){
                flash.message= message(code:'default.listing.not.logged.in.message')
                redirect(action: "list")
                return
            }

            listing.seller = customer;
            def newListing = listingService.Create(listing)
            listing=newListing
            listing.minimumBid = listingService.getMinimumBidAmount(listing)
            //pattern from: http://grails.org/doc/latest/guide/services.html
        }catch (ValidationException ex){
            listing.errors=ex.errors
            render view:  "create", model:[listingInstance:listing]
        }
            flash.message = message(code: 'default.created.message', args: [message(code: 'listing.label', default: 'Listing'), listing.id])
            redirect(action: "myListings")
    }

    def minimumBidAmount(){

        def listing = Listing.get(params.id);
        def bidAmount = listingService.getMinimumBidAmount(listing);
        def jsonMap = [status: "success", minBidAmount:g.formatNumber(number:bidAmount, type:'currency', currencyCode: 'USD')]
        render jsonMap as JSON
    }

    def show() {
        def listingInstance = Listing.get(params.id)

        if (!listingInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'listing.label', default: 'Listing'), params.id])
            redirect(action: "list")
            return
        }

        // UI-7: Attempting to view the listing detail page for a listing that has expired will display a "Listing Expired" message instead of showing the details of the listing
        if (listingInstance.isEnded()){
            def customer =springSecurityService.getCurrentUser();
            def email = Customer.formatEmail(customer?.emailAddress)
            def message = message(code:'default.listing.expired.message', args:[email]).toString()
            response.setStatus(301, message)
            forward action:"list"
            return
        }

        listingInstance.latestBid = listingInstance?.bids?.max {it->it.dateCreated}

        // UI-3: The action of placing a new bid will update the new minimum bid price stored in the database and displayed to the user on the page
        listingInstance.minimumBid = listingService.getMinimumBidAmount(listingInstance)
        [listingInstance: listingInstance]
    }

    def edit() {
        def listingInstance = Listing.get(params.id)
        if (!listingInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'listing.label', default: 'Listing'), params.id])
            redirect(action: "list")
            return
        }

        [listingInstance: listingInstance]
    }

    def update() {
        def listingInstance = Listing.get(params.id)
        if (!listingInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'listing.label', default: 'Listing'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (listingInstance.version > version) {
                listingInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'listing.label', default: 'Listing')] as Object[],
                          "Another user has updated this Listing while you were editing")
                render(view: "edit", model: [listingInstance: listingInstance])
                return
            }
        }

        listingInstance.properties = params

        if (!listingInstance.save(flush: true)) {
            render(view: "edit", model: [listingInstance: listingInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'listing.label', default: 'Listing'), listingInstance.id])
        redirect(action: "show", id: listingInstance.id)
    }

    def delete() {
        def listingInstance = Listing.get(params.id)
        if (!listingInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'listing.label', default: 'Listing'), params.id])
            redirect(action: "list")
            return
        }

        try {
            listingInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'listing.label', default: 'Listing'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'listing.label', default: 'Listing'), params.id])
            redirect(action: "show", id: params.id)
        }
    }

    def myListings() {
        def user = springSecurityService.currentUser
        def username = (user as Customer).username

        def listingInstance = listingService.getMyListings(username)
        [listingInstanceList: listingInstance]
    }

    @Secured(['ROLE_USER'])
    def newBid() {

        //this id is for the listing we're bidding upon
        def id = params.int('id')
        def amt = params.float('amount')
        def jsonMap = [id];


        if(id){
            // L-7: The detail page for the listing allows a new bid to be placed
            // UI-2: The action of placing a new bid on a listing will happen asynchronously, without leaving the page

            // if we have an id here, it's the id of a listing and we came from the
            // show view of a listing
            def bid = new Bid(id)   //pass in the listing id
            def listing = bid.listing

            def customer

            if(params['customer']) {
                customer = params.Customer('customer')
            }
            else {
                customer = springSecurityService.getCurrentUser();
            }

            def email = customer==null?"Not-Logged-In":customer.emailAddress

            if (listing.isEnded()){
                customer =springSecurityService.getCurrentUser();
                email = Customer.formatEmail(customer?.emailAddress)
                def expired = message(code:'default.listing.expired.message', args:[email]).toString()
                jsonMap=[status: "expired", bid:bid, message:expired]
                render jsonMap as JSON
            }

            bid.bidder = customer
            bid.amount = amt

            try{
                //could not get the unit tests to do this for me
                if (bidService.listingService==null){
                    bidService.listingService = listingService
                }
                def savedBid = bidService.Create(bid)
                bid = savedBid
                // UI-4: The action of placing a new bid will display a message to the user indicating that the bid was successful
                def msg = message(code: 'default.bid.accepted.message', args: [bid.bidder.displayEmailAddress, bid.amount])
                jsonMap = [status: "success", bid:bid, message:msg, minBidAmount:g.formatNumber(number:amt+Listing.MINIMUM_BID_INCREMENT, type:'currency', currencyCode: 'USD')]
            }catch (ValidationException ex){

                bid.errors=ex.errors
                def price

                if (null==amt) {
                    price = g.formatNumber(number:0, type:'currency', currencyCode: 'USD')
                }
                else {
                    price = g.formatNumber(number:amt, type:'currency', currencyCode: 'USD')
                }

                // UI-5: An error message will be displayed if placing a new bid is unsuccessful (for instance if the new bid amount does not pass validation requirements)
                def msg = message(code: 'default.bid.not.accepted.message', args:[Customer.formatEmail(email), price])
                jsonMap = [status:"error", errors:ex.errors, message:msg]
            }
        }
        else {
            def msg = message(code: 'default.request.error.message')
            jsonMap = [status: "error", message: msg]

        }

        render jsonMap as JSON
    }
}
