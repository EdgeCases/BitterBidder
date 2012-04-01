package bitterbidder

import org.springframework.dao.DataIntegrityViolationException

class ListingController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def listingService

    def index() {
        redirect(action: "list", params: params)
    }

    def addBid(){
        def listingInstance = Listing.get(params.id)
        [listingInstance: listingInstance]
    }

    def list() {
        // M-2: The main landing page shows up to 5 listings at a time
        params.max = Math.min(params.max ? params.int('max') : 5, 100)

        // M-1: The main landing page shows listings sorted by the date they were created (most recent first)
        params.sort = params.sort?:'dateCreated'
        params.order = params.order?:'desc'

        // M-4: Only listings with a end date/time that is in the future are visible on the main page
        def listingInstance = Listing.list(params).findAll {it -> it.endDateTime >= new Date()}
        def totalListings = Listing.findAll("from Listing as l where l.endDateTime >= ?", [new Date()])

        [listingInstanceList: listingInstance, listingInstanceTotal: totalListings.size()]
    }

    def create() {
        [listingInstance: new Listing(params)]
    }

    def save() {
        def listingInstance = new Listing(params)
        if (!listingInstance.save(flush: true)) {
            render(view: "create", model: [listingInstance: listingInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'listing.label', default: 'Listing'), listingInstance.id])
        redirect(action: "show", id: listingInstance.id)
    }

    def show() {
        def listingInstance = Listing.get(params.id)        
        if (!listingInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'listing.label', default: 'Listing'), params.id])
            redirect(action: "list")
            return
        }
        listingInstance.latestBid = listingInstance?.bids?.max {it->it.dateCreated}
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
}
