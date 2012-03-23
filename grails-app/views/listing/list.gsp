
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
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
                <li><g:link class="list" action="list" controller="customer" >Customers</g:link> </li>
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
					
						<!-- M-10: Listings can be sorted by end date/time -->
                        <g:sortableColumn property="endDateTime" title="${message(code: 'listing.endDateTime.label', default: 'End Date Time')}" />
					
						<th><g:message code="listing.seller.label" default="Seller" /></th>
					
						<th><g:message code="listing.winner.label" default="Winner" /></th>
					
						<!-- M-9: Listings can be sorted by starting price -->
                        <g:sortableColumn property="startingPrice" title="${message(code: 'listing.startingPrice.label', default: 'Starting Price')}" />

                        <th><g:message code="listing.bids.count.label" default="# of Bids" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${listingInstanceList}" status="i" var="listingInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<!-- M-11: A user can click on a listing to see additional details about that listing -->
                        <td><g:link action="show" id="${listingInstance.id}">${fieldValue(bean: listingInstance, field: "description")}</g:link></td>
					
						<!-- M-5: The name is visible for each listing -->
                        <td>${fieldValue(bean: listingInstance, field: "name")}</td>
					
						<!-- M-8: The end date/time is visible for each listing -->
                        <td><g:formatDate date="${listingInstance.endDateTime}" /></td>
					
						<td>${fieldValue(bean: listingInstance, field: "seller")}</td>
					
						<td>${fieldValue(bean: listingInstance, field: "winner")}</td>

                        <!-- M-7: The starting price is visible for each listing -->
                        <td> <g:formatNumber number="${listingInstance.startingPrice}" type="currency" currencyCode="USD" /></td>
                        %{--<td>${fieldValue(bean: listingInstance, field: "startingPrice")}</td>--}%

                        <!-- M-6: The number of bids is visible for each listing -->
                        <td>${listingInstance.bids.size()}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
                <!-- M-3: If more than 5 listings exist, pagination links will be available to let the user page through the listings -->
                <g:paginate total="${listingInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
