package bitterbidder

class Customer {
    
    String emailAddress
    Date dateCreated
    String password
    //String passwordMinusDomain = (password.split('@'))[0]

    static constraints = {
        emailAddress(unique: true, email: true)
        password(size: 6..8, blank: false)
    }
}
