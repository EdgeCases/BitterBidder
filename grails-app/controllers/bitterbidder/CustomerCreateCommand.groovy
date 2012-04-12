package bitterbidder

import org.codehaus.groovy.grails.validation.Validateable

// apparently there is a bug in grails that doesn't provide the dynamic validate method if a command object is
// in its own class and not in the same class as the controller that uses it.  the only way around that is to
// annotate the command object class as @Validateable
@Validateable
class CustomerCreateCommand {

    String emailAddress
    String password
    String username

    static constraints = {
        //importFrom Customer

        password(size: 6..8, blank: false)
    }
}
