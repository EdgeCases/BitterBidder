package bitterbidder

import grails.validation.ValidationException

// SRV-1: Create a Grails service method that supports creating a new customer (unit test)
class CustomerService {

    def Create(Customer customer) {
        if (!customer.validate()){
            throw new ValidationException("customer is invalid", customer.errors)
        }

        customer.enabled = true;
        return customer.save(flush: true);
    }
}
