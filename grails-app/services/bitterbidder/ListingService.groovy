package bitterbidder

class ListingService {

    def CreateListing(Listing listing) {

        if (listing.validate()) {
            return listing.save(flush: true);
        }
        return listing;
    }
}
