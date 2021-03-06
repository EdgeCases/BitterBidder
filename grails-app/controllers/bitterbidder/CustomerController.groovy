package bitterbidder

import org.springframework.dao.DataIntegrityViolationException
import grails.plugins.springsecurity.Secured
import grails.validation.ValidationException

class CustomerController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def customerService

    def index() {
        redirect(action: "list", params: params)
    }

    // S-4: Only a user with an admin role is able to view / edit customer screens created in requirements C-1 through C-4 in assignment 2
    @Secured(['ROLE_ADMIN'])
    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [customerInstanceList: Customer.list(params), customerInstanceTotal: Customer.count()]
    }

    // C-1: A new customer can be created through the web interface
    def create() {
        [customerInstance: new Customer(params)]
    }

    def save = { CustomerCreateCommand cmd ->

        if (cmd.validate()){
            def customer = new Customer(params)

            try{
                def newCustomer = customerService.Create(customer)
                customer = newCustomer
            }catch (ValidationException ex){

                customer.errors = ex.errors
                render view:  "create", model:[customerInstance: customer]
                return

            }

            flash.message = message(code: 'default.created.message', args: [message(code: 'customer.label', default: 'Customer'), customer.id])
            redirect(controller:'listing', action: 'list')
        }
        else {
            render view:  "create", model:[customerInstance: cmd]
        }

    }

    def passwordMinusDomain(){

        def customerInstance = Customer.get(params.id)

        if (!customerInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'customer.label', default: 'Customer'), params.id])
            redirect(action: "list")
            return
        }

        def user = customerInstance.emailAddress //default
        
        try{
            def parts = customerInstance.emailAddress.split('@')

            if (2 == parts.length){
                //get only the user portion of the address
                user = parts[0]
            }
            else {
                throw "bad email"
            }

        }
        catch(Error e) {
            log.error(e.message + ' bad email address')
        }

        return user
    }

    // S-4: Only a user with an admin role is able to view / edit customer screens created in requirements C-1 through C-4 in assignment 2
    @Secured(['ROLE_ADMIN'])
    def show() {
        def customerInstance = Customer.get(params.id)

        if (!customerInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'customer.label', default: 'Customer'), params.id])
            redirect(action: "list")
            return
        }

        // L-6: The detail page for the listing shows only the user portion of the email address of the user who created the listing
        customerInstance.emailAddress = passwordMinusDomain()

        [customerInstance: customerInstance]
    }

    // C-2: An existing customer can be updated through the web interface
    // S-4: Only a user with an admin role is able to view / edit customer screens created in requirements C-1 through C-4 in assignment 2
    @Secured(['ROLE_ADMIN'])
    def edit() {
        def customerInstance = Customer.get(params.id)

        if (!customerInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'customer.label', default: 'Customer'), params.id])
            redirect(action: "list")
            return
        }

        [customerInstance: customerInstance]
    }

    // S-4: Only a user with an admin role is able to view / edit customer screens created in requirements C-1 through C-4 in assignment 2
    @Secured(['ROLE_ADMIN'])
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

    // S-4: Only a user with an admin role is able to view / edit customer screens created in requirements C-1 through C-4 in assignment 2
    @Secured(['ROLE_ADMIN'])
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

class CustomerCreateCommand {

    String emailAddress
    String password
    String username

    static constraints = {
        importFrom Customer

        password(size: 6..8, blank: false)
    }
}
