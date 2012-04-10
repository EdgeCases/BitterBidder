package bitterbidder


class MonitorListingsJob {

    def listingNotificationService

    static triggers = {
      simple name: "endedListings", repeatInterval: 30000, repeatCount: 10// execute job once in 5 seconds
    }

    def execute() {
        print "checking for ended listings..."
        //TODO: Make sure this uncommented out!
       //listingNotificationService.sendListingEndedNotifications()
    }
}
