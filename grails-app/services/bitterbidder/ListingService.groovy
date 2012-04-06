package bitterbidder

class ListingService {

    def Create(Listing listing) {

        if (listing.validate()) {
            return listing.save(flush: true);
        }
        return listing;
    }

    def getMyListings(String userName){
        def result =Listing.findAll("from Listing as l where l.seller.username = ?", [userName])
        return result
    }
}
