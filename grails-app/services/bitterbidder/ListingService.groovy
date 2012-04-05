package bitterbidder

class ListingService {

    def Create(Listing listing) {

        if (listing.validate()) {
            return listing.save(flush: true);
        }
        return listing;
    }

    def getMyListings(String userName){
        return query.findAll {
            it->it.seller.username==userName
        }
    }
}
