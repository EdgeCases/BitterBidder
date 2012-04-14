
<!doctype html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->

<head>
    %{--<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>--}%
    %{--<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.18/jquery-ui.min.js"></script>--}%
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title><g:layoutTitle default="Grails"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon">
    <link rel="apple-touch-icon" href="${resource(dir: 'images', file: 'apple-touch-icon.png')}">
    <link rel="apple-touch-icon" sizes="114x114" href="${resource(dir: 'images', file: 'apple-touch-icon-retina.png')}">
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'main.css')}" type="text/css">
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'mobile.css')}" type="text/css">
    %{--<link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.6/themes/overcast/jquery-ui.css" type="text/css" rel="stylesheet"/>--}%
    <g:javascript library="jquery" plugin="jquery"></g:javascript>
    <r:require module="jquery-ui"/>
    <r:layoutResources />
    <g:layoutHead/>
</head>
<body>
<!-- L-1: A consistent header and footer will be displayed on every screen -->
<div id="bitterbidderLogo" role="banner">
    <img src="${resource(dir: 'images', file: 'bitterbidder_logo.png')}" alt="BitterBidder"/>
</div>
<div>
    <sec:ifNotLoggedIn>
        <!-- L-2: If user is not logged in, show a "Login" link on every screen -->
        <g:link action="auth" controller="Login" >[Login]</g:link>
        <!-- L-3: If user is not logged in, show a "Register" link on every screen -->
        <!-- S-1: A new visitor to the site can register themselves as a new user -->
        <g:link action="create" controller="Customer" >[Register]</g:link>
    </sec:ifNotLoggedIn>

    <sec:ifLoggedIn>
        <!-- L-5: If user is logged in, show the logged in username on every screen -->
        Welcome <sec:loggedInUserInfo field="username"/>
        <!-- L-4: If user is logged in, show a "Logout" link on every screen -->
        <g:link action="index" controller="Logout" >[Logout]</g:link>
    </sec:ifLoggedIn>
</div>
<g:layoutBody/>
<!-- L-1: A consistent header and footer will be displayed on every screen -->
<div class="footer" role="contentinfo">
    <p>&copy; EdgeCases</p>
</div>
<div id="spinner" class="spinner" style="display:none;"><g:message code="spinner.alt" default="Loading&hellip;"/></div>
<g:javascript library="application"/>
<g:javascript src="bitterbidder.ui.bid.js"/>
<g:javascript src="bitterbidder.ui.listing.js"/>
<r:layoutResources />
</body>
</html>
