if (typeof jQuery !== 'undefined') {
	(function($) {
		$('#spinner').ajaxStart(function() {
			$(this).fadeIn();
		}).ajaxStop(function() {
			$(this).fadeOut();
		});
	})(jQuery);
};

//
//take the json list of bid amounts and inject them into
//the dom
//
processAndInsertBidAmounts = function(values) {

    //var amountList = JSON.parse(values);

    if(null !== values) {

        $.each(values.amounts, function(index, value) {

            $('<p />').html(value.amt).appendTo('#lastBids');
        });
    }
};
