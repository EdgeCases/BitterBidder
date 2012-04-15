package bitterbidder

import org.apache.commons.logging.LogFactory


class MonitorListingsJob {

    def listingNotificationService
    private static final log = LogFactory.getLog(this)

    static triggers = {
      simple name: "endedListings", repeatInterval: 10000, repeatCount: 100000// execute job once in 5 seconds
    }

    def execute() {
        log.info("checking for expired listings...")
        listingNotificationService.sendListingEndedNotifications()
    }
}
