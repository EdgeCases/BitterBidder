package bitterbidder

class Listing {

    String name
    static constraints = {
         name size: 1..64, blank: false, nullable: false
    }
}
