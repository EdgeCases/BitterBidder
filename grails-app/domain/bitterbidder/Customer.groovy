package bitterbidder

class Customer {
    
    String emailAddress
    Date dateCreated
    String password
    //String user

    def getUserName() {

        def parts = emailAddress.split('@')

        if (2 == parts.length){
            //get only the user portion of the address
            return parts[0]
        }

        return "Unknown"
    }
//    def beforeInsert = {
//
//        if(null==emailAddress) {
//            user = ""
//        }
//        else {
//            def parts = emailAddress.split('@')
//
//            if (2 == parts.length){
//                //get only the user portion of the address
//                user = parts[0]
//            }
//        }
//    }
    static constraints = {
        emailAddress(unique: true, email: true)
        password(size: 6..8, blank: false)
    }
}
