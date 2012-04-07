package bitterbidder

class CustomerService {

    def createNewCustomer(Customer customer) {
        customer.save(flush: true)
    }
}
