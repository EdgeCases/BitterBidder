package bitterbidder

class Customer {
    
    String emailAddress // C-1
    Date dateCreated    // C-1
    String password     // C-1
    
    static constraints = {
        emailAddress(unique: true, email: true) // C-2, C-3
        password(size: 6..8, blank: false)  // C-4
    }
}
