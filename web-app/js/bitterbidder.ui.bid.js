/**
 * Created with IntelliJ IDEA.
 * User: dalcantara
 * Date: 4/9/12
 * Time: 1:21 AM
 * To change this template use File | Settings | File Templates.
 */

bindControls= function (){
    jQuery.ajaxSetup({cache: false});
    $('#newBidButton').live("click", function(){
        var $bidAmount=null;
        $bidAmount=$('#newBidAmount');
        var $listingId = null;
        $listingId = $('#listingId');
        postNewBid($listingId.val(), $bidAmount.val());
    });

    var $bidAmount=null;
    $bidAmount=$('#newBidAmount');
    $bidAmount.focus();
    $bidAmount.live("click" ,function(){
        if (!$.isNumeric(($bidAmount.val()))) {
            $bidAmount.val("");
        }
    });
    $bidAmount.live('blur', function(){
        if (!$.isNumeric(($bidAmount.val()))) {
            $bidAmount.val("Enter Bid Amount");
        }
    });
};

postNewBid = function(listingId, amount) {

    $.ajax({
        type: 'POST',
        url: '/BitterBidder/listing/newBid/',
        dataType: 'json',
        cache:false,
        data: {
            id: listingId,
            amount: amount
        },
        success: function(data, textStatus, jqXHR) {

            showResults(data.message, data.status);
            if (data.status=="success")    {
                $('#minimumBid').text;("");
                $('#minimumBid').text(data.minBidAmount);
            }

            getLatestBids(listingId);

        },
        error: function(jqXHR, textStatus, errorThrown){
            showResults("" + "Error Message - " + errorThrown, "Error");
        }
    });

    return false;   //todo - check if this is necessary
};

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

showResults = function(message, caption){

    $("#resultsDialog").dialog({
        height:130,
        width:550,
        modal:true,
        autoOpen:true,
        show: {effect: 'fadeIn', duration: 400},
        hide: {effect: 'fadeOut', duration: 500},
        title:caption,
       // title:'<img src="http://localhost:8080/BitterBidder/static/images/skin/house.png"> Bitter Bidder </img>',
        open:function(event, ui){
            $('#resultsMessage').html(message);
        },
        close:function(event, ui){
            $('#resultsMessage').html("");
            $("#resultsDialog").dialog('destroy');
        }
    });
    $("#resultsDialog").dialog('open');
} ;
