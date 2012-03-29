package bitterbidder

import org.springframework.dao.DataIntegrityViolationException

class BidController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [bidInstanceList: Bid.list(params), bidInstanceTotal: Bid.count()]
    }

//    def lastTen(){
//
//        assert null != params
//
//        //this id is for the listing we're bidding upon
//        def id = params.id
//        def shortList// = new StringBuffer()
//
//        if(id){
//
//            def bidList = Bid.findAll()
//
//            for (it in bidList) {
//                if (id == it.listing.id) {
//
////                    shortList <<= '<p>'
////                    shortList <<= it.amount
////                    shortList <<= '</p>'
//                    shortList = shortList + '<br />'
//                }
//            }
//        }
//
//        return shortList.toString()
//    }

    def lastTen() {

        def listing = Listing.findById(params.id)
        def bids = listing.bids
        def shortList = new StringBuilder()

        for (it in bids) {
            shortList <<= '<p>'
            shortList <<= it.amount
            shortList <<= '</p>'
        }
        
        render shortList
    }

    def create() {

        //this id is for the listing we're bidding upon
        def id = params.id

        if(id){
            //L-7: The detail page for the listing allows a new bid to be placed
            //if we have an id here, it's the id of a listing and we came from the
            //show view of a listing
            [bidInstance: new Bid(params.id)]
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
