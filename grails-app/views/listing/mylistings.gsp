
<%@ page import="bitterbidder.Listing" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <title><g:message code="default.mylistings.label"/></title>
</head>
<body>
<a href="#list-listing" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
    </ul>
</div>
<div id="list-listing" class="content scaffold-list" role="main">
</div>
</body>
</html>
