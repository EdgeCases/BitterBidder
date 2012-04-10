/**
 * Created with IntelliJ IDEA.
 * User: dalcantara
 * Date: 4/9/12
 * Time: 1:21 AM
 * To change this template use File | Settings | File Templates.
 */

bindControls= function (){

    $('#newBidButton').live("click", function(){
        var $bidAmount=null;
        $bidAmount=$('#newBidAmount');
        var $listingId = null;
        $listingId = $('#listingId');
        postNewBid($bidAmount.val(), $listingId.val());
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
        data: {
            id: listingId,
            amount: amount
        },
        success: function(data, textStatus, jqXHR) {
            showResults("Congratulations ", "Success!");
            //getLatestBids(listingId);
        },
        error: function(jqXHR, textStatus, errorThrown){
            showResults(errorThrown, "An Error Ocurred");
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
        height:200,
        width:200,
        modal:true,
        autoOpen:false,
        show:"pulsate",
        hide:"explode",
        title:caption,
        open:function(event, ui){
            $('#resultsMessage').html(message);
        },
        close:function(event, ui){
            $("#resultsDialog").dialog('destroy');
        }

    });
    $("#resultsDialog").dialog('open');
} ;
