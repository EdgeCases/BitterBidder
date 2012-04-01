package bitterbidder

class CustomerService {

    def createNewCustomer(String username, String emailAddress, String password) {
        def newCustomer = new Customer(username: username, enabled: true, emailAddress: emailAddress, password: password).save(flush: true)
    }
}
