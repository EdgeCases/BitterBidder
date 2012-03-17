<%@ page import="bitterbidder.Listing" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'listing.label', default: 'Listing')}" />
    %{--<title><g:message code="default.edit.label" args="[entityName]" /></title>--}%
    <title>Place Bid</title>
</head>
    <body>
        <a href="#edit-listing" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>

        <div class="nav" role="navigation">
            <ul>
                <li>
                    <a class="home" href="${createLink(uri: '/')}">
                        <g:message code="default.home.label"/></a></li>
            </ul>
        </div>

        <div id="edit-listing" class="content scaffold-edit" role="main">
            %{--<h1><g:message code="default.edit.label" args="[entityName]" /></h1>--}%
            <h1>Place Bid</h1>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${listingInstance}">
                <ul class="errors" role="alert">
                    <g:eachError bean="${listingInstance}" var="error">
                        <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>>
                            <g:message error="${error}"/></li>
                    </g:eachError>
                </ul>
            </g:hasErrors>

            <g:form method="post" >
                <g:hiddenField name="id" value="${listingInstance?.id}" />
                <g:hiddenField name="version" value="${listingInstance?.version}" />
                <fieldset class="form" controller="Listing">
                    %{--<g:render template="form"/>--}%
                    <input size="10" type="text" id="latestBid" name="latestBid" />
                </fieldset>
                <fieldset class="buttons">
                    <g:actionSubmit class="save" action="placeBid" value="Place Bid" />
                </fieldset>
            </g:form>
        </div>
    </body>
</html>
