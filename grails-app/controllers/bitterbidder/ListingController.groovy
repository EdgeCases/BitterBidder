package bitterbidder

import org.springframework.dao.DataIntegrityViolationException

class ListingController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [listingInstanceList: Listing.list(params), listingInstanceTotal: Listing.count()]
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
