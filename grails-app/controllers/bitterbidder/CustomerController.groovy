package bitterbidder

import org.springframework.dao.DataIntegrityViolationException
import grails.test.mixin.TestFor
import grails.test.mixin.Mock

class CustomerController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [customerInstanceList: Customer.list(params), customerInstanceTotal: Customer.count()]
    }

    // C-1: A new customer can be created through the web interface
    def create() {
        [customerInstance: new Customer(params)]
    }

    def save() {
        def customerInstance = new Customer(params)
        if (!customerInstance.save(flush: true)) {
            render(view: "create", model: [customerInstance: customerInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'customer.label', default: 'Customer'), customerInstance.id])
        redirect(action: "show", id: customerInstance.id)
    }

    def show() {
        def customerInstance = Customer.get(params.id)
        if (!customerInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'customer.label', default: 'Customer'), params.id])
            redirect(action: "list")
            return
        }

        [customerInstance: customerInstance]
    }

    // C-2: An existing customer can be updated through the web interface
    def edit() {
        def customerInstance = Customer.get(params.id)
        if (!customerInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'customer.label', default: 'Customer'), params.id])
            redirect(action: "list")
            return
        }

        [customerInstance: customerInstance]
    }

    def update() {
        def customerInstance = Customer.get(params.id)
        if (!customerInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'customer.label', default: 'Customer'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (customerInstance.version > version) {
                customerInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'customer.label', default: 'Customer')] as Object[],
                        "Another user has updated this Customer while you were editing")
                render(view: "edit", model: [customerInstance: customerInstance])
                return
            }
        }

        customerInstance.properties = params

        if (!customerInstance.save(flush: true)) {
            render(view: "edit", model: [customerInstance: customerInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'customer.label', default: 'Customer'), customerInstance.id])
        redirect(action: "show", id: customerInstance.id)
    }

    def delete() {
        def customerInstance = Customer.get(params.id)
        if (!customerInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'customer.label', default: 'Customer'), params.id])
            redirect(action: "list")
            return
        }

        // C-4: An existing customer can only be deleted through the web interface if they have 0 listings.
        // The system will present an error message to the user if the delete cannot be performed
        if(Listing.findBySeller(customerInstance)) {
            flash.error = message(code: 'default.not.deleted.listing.exists.message', args: [message(code: 'customer.label', default: 'Customer'), params.id])
            redirect(action: "list")
            return
        }

        // C-4: An existing customer can only be deleted through the web interface if they have 0 bids.
        // The system will present an error message to the user if the delete cannot be performed
        if (Bid.findByBidder(customerInstance) != null) {
            flash.error = message(code: 'default.not.deleted.bid.exists.message', args: [message(code: 'customer.label', default: 'Customer'), params.id])
            redirect(action: "list")
            return
        }

        try {
            customerInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'customer.label', default: 'Customer'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'customer.label', default: 'Customer'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
