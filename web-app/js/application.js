if (typeof jQuery !== 'undefined') {
	(function($) {
		$('#spinner').ajaxStart(function() {
			$(this).fadeIn();
		}).ajaxStop(function() {
			$(this).fadeOut();
		});
	})(jQuery);
}

//
//take the json list of bid amounts and inject them into
//the dom
//
processAndInsertBidAmounts = function(values) {

    //var amountList = JSON.parse(values);

    if(null !== values) {

        $.each(values.amounts, function(index, value) {
            var amt = '<p>' + value.amt + '</p>';
            $(amt).appendTo('#lastBids');
            //$('')
            //$('<p />').html(value.amt).appendTo('#lastBids');
            //<g:formatNumber number="${33.0}" type="currency" currencyCode="USD" ></g:formatNumber>

//            $($.camelCase("<g:format-Number></g:format-Number>"))
//                .attr('number', value.amt)
//                .attr('type', 'currency')
//                .attr('currencyCode', 'USD')
//                .appendTo('#lastBids');
        });
    }
};
