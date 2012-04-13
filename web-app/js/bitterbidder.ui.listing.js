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
//            var $dialog=$('#listingResultsDialog');
//            $dialog.dialog('open').delay(2000);
//            $dialog.dialog('close');
//            $dialog.dialog('destroy');
//            $('#listingResultsDialogMessage').html="";
            //window.location.href = this.href;
            return false;

//            $('#resultsDialog').dialog('open');
//            var href = this.href;
//            setTimeout(function() {
//                $('#resultsDialog').dialog('close');
//                window.open(href);
//            }, 2000);
    //            return false;
    });
    });
}

showListingDetails=function(urlString){
    $.ajax({
        type:'GET',
        url:urlString,
        dataType: 'html',
        cache:false,
        success: function(data, textStatus, jqXHR) {
            window.location.href = urlString
        },
        error: function(jqXHR, textStatus, errorThrown){

        },
        complete: function(jqXHR, textStatus){

        }
    })
};

