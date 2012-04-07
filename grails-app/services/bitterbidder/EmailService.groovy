package bitterbidder

class EmailService {

    def mailService
    boolean transactional = false
    static exposes = ['jms']
    static destination = "queue.listingended"
    def onMessage(it){
        println "GOT MESSAGE: $it"
    }
}
