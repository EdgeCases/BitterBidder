/**
 * Created with IntelliJ IDEA.
 * User: dalcantara
 * Date: 4/9/12
 * Time: 1:21 AM
 * To change this template use File | Settings | File Templates.
 */

initializeUI=function(){

    jQuery.ajaxSetup({cache: false});
    $(document).ready(function(){
        $('a[href*="BitterBidder/listing/show"]').click(function(event) {
            event.preventDefault();
            var $url = this.href;
            showListingDetails($url);
            return false;
        });
    });
}

showListingDetails=function(urlString){
    jQuery.ajaxSetup({cache: false});
    $.ajax({
        type:'GET',
        url:urlString,
        dataType: 'html',
        cache:false,
        success: function(data, textStatus, jqXHR) {
            window.location.replace(urlString);
        },
        error: function(jqXHR, textStatus, errorThrown){
            showListingExpiredDialog(jqXHR.statusText);
        }
    })
};

//We were trying to get this message and redirect URl to come from the controller but had no luck trying to
//set a custom message on the response.setStatus was suppose to do it but it didn't work.
showListingExpiredDialog=function(message){

    $("#listingResultsDialog").dialog({
        height:130,
        width:550,
        cache:false,
        modal: true,
        caption:"Listing Expired",
        open: function(event, ui){
            $expiredMessage = "We are sorry. This listing has expired. You'll now be redirected to an updated listings page."
            $('#listingResultsDialogMessage').html($expiredMessage);
            setTimeout("$('#listingResultsDialog').dialog('close')",2500);
        },
        close:function(event, ui){
            $listingList= "http://"+document.location.host +"/BitterBidder/listing/list"
            window.location.replace($listingList);
            $('#listingResultsDialogMessage').html("");
            $("#listingResultsDialog").dialog('destroy');

        }
    });
}
