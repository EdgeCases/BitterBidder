package bitterbidder

class Customer {

    transient springSecurityService

    String emailAddress
    Date dateCreated
    String username
    String password
    boolean enabled
    boolean accountExpired
    boolean accountLocked
    boolean passwordExpired
    String displayEmailAddress

    static transients = ['displayEmailAddress']

    String getDisplayEmailAddress(){
        return Customer.formatEmail(emailAddress)
    }

    static formatEmail(email){
        if (email == null) return null

        def atSign = email.indexOf('@')
        if (atSign<=0){
            return email
        }
        return email.substring(0, atSign)
    }

    static constraints = {
        emailAddress unique: true, email: true, blank: false
        username blank: false, unique: true
        password blank: false
    }

    static mapping = {
        password column: '`password`'
    }

    Set<Role> getAuthorities() {
        CustomerRole.findAllByCustomer(this).collect { it.role } as Set
    }

    def beforeInsert() {
        encodePassword()
    }

    def beforeUpdate() {
        if (isDirty('password')) {
            encodePassword()
        }
    }

    protected void encodePassword() {
        password = springSecurityService.encodePassword(password)
    }
}
