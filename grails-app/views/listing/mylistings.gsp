<!-- S-5: Create a view called "My Listings" that shows listings created by the logged in user -->
<%@ page import="bitterbidder.Listing" %>
<!doctype html>
<html>
    <head>
        <meta name="layout" content="main">
        <g:set var="entityName" value="${message(code: 'listing.label', default: 'Listing')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#list-listing" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
            </ul>
        </div>
        <div id="list-listing" class="content scaffold-list" role="main">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>
            <table>
                <thead>
                    <tr>
                        <g:sortableColumn property="description" title="${message(code: 'listing.description.label', default: 'Description')}" />

                        <g:sortableColumn property="name" title="${message(code: 'listing.name.label', default: 'Name')}" />

                        <g:sortableColumn property="endDateTime" title="${message(code: 'listing.endDateTime.label', default: 'End Date Time')}" />

                        <th><g:message code="listing.seller.label" default="Seller" /></th>

                        <th><g:message code="listing.winner.label" default="Winner" /></th>

                        <g:sortableColumn property="startingPrice" title="${message(code: 'listing.startingPrice.label', default: 'Starting Price')}" />

                        <th><g:message code="listing.bids.count.label" default="# of Bids" /></th>

                    </tr>
                </thead>
                <tbody>
                    <g:each in="${listingInstanceList}" status="i" var="listingInstance">
                        <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

                            <td><g:link action="show" id="${listingInstance.id}">${fieldValue(bean: listingInstance, field: "description")}</g:link></td>

                            <td>${fieldValue(bean: listingInstance, field: "name")}</td>

                            <td><g:formatDate date="${listingInstance.endDateTime}" /></td>

                            <td>${fieldValue(bean: listingInstance, field: "seller.displayEmailAddress")}</td>

                            <td>${fieldValue(bean: listingInstance, field: "winner.displayEmailAddress")}</td>

                            <td> <g:formatNumber number="${listingInstance.startingPrice}" type="currency" currencyCode="USD" /></td>

                            <td>${listingInstance.bids.size()}</td>

                        </tr>
                    </g:each>
                </tbody>
            </table>
        </div>
    </body>
</html>
