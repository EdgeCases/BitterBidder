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

        if (emailAddress == null) return null

        def atSign = emailAddress.indexOf('@')
        if (atSign<=0){
            return emailAddress
        }
        return emailAddress.substring(0, atSign)

    }


    static constraints = {
        emailAddress unique: true, email: true
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
