package bitterbidder

class CustomerCreateCommand {
    String password

    static constraints = {
        password: size: 6..8
    }
}
