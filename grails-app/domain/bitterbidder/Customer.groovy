package bitterbidder

class Customer {
    String emailAddress
    Date dateCreated
    String password
    static constraints = {
        emailAddress(unique: true, email: true)
        password(size: 6..8)
    }
}
