if (typeof jQuery !== 'undefined') {
    (function($) {
        $('#spinner').ajaxStart(function() {
            $(this).fadeIn();
        }).ajaxStop(function() {
                $(this).fadeOut();
            });
    })(jQuery);
}

getLatestBids = function(id) {

    $.ajax({
        url: '/BitterBidder/bid/lastTen/' + id,
        success: function(result){
            processAndInsertBidAmounts(result);
        }
    });
};

//
//take the json list of bid amounts and inject them into
//the dom
//
processAndInsertBidAmounts = function(values) {

    if(null !== values) {

        //clear out the previous list of bids
        $('#lastBids').html('');

        //reset the bid value
        $('#newBidAmount').val('Bid Amount');

        //add each of the results
        $.each(values.amounts, function(index, value) {

            var amt = '<p>' + value.amt + '</p>';
            $(amt).appendTo('#lastBids');
        });
    }
};

postNewBid = function(listingId) {

    $.ajax({
        type: 'POST',
        url: '/BitterBidder/listing/newBid/',
        dataType: 'json',
        data: {
            id: listingId,
            amount: $('#newBidAmount').val()
        },
        success: function() {
            getLatestBids(listingId);
        },
        complete: function(xhr, status) {
            //Console.log('postNewBid completed')
        }
    });

    return false;   //todo - check if this is necessary
};
