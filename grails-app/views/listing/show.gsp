
<%@ page import="bitterbidder.Listing" %>
<%@ page import="bitterbidder.Bid" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'listing.label', default: 'Listing')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-listing" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-listing" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list listing">
			
				<g:if test="${listingInstance?.description}">
				<li class="fieldcontain">
					<span id="description-label" class="property-label">
                        <g:message code="listing.description.label" default="Description" /></span>
					<span class="property-value" aria-labelledby="description-label">
                        <g:fieldValue bean="${listingInstance}" field="description"/></span>
				</li>
				</g:if>
			
				<g:if test="${listingInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label">
                        <g:message code="listing.name.label" default="Name" /></span>
					<span class="property-value" aria-labelledby="name-label">
                        <g:fieldValue bean="${listingInstance}" field="name"/></span>
				</li>
				</g:if>
			
				<g:if test="${listingInstance?.endDateTime}">
				<li class="fieldcontain">
					<span id="endDateTime-label" class="property-label">
                        <g:message code="listing.endDateTime.label" default="End Date Time" /></span>
					<span class="property-value" aria-labelledby="endDateTime-label">
                        <g:formatDate date="${listingInstance?.endDateTime}" /></span>
				</li>
				</g:if>
			
				<g:if test="${listingInstance?.seller}">
				<li class="fieldcontain">
					<span id="seller-label" class="property-label">
                        <g:message code="listing.seller.label" default="Seller" /></span>
					<span class="property-value" aria-labelledby="seller-label">
                        <g:link controller="customer" action="show" id="${listingInstance?.seller?.id}">${listingInstance?.seller?.encodeAsHTML()}</g:link></span>
				</li>
				</g:if>
			
				<g:if test="${listingInstance?.winner}">
				<li class="fieldcontain">
					<span id="winner-label" class="property-label">
                        <g:message code="listing.winner.label" default="Winner" /></span>
					<span class="property-value" aria-labelledby="winner-label">
                        <g:link controller="customer" action="show" id="${listingInstance?.winner?.id}">${listingInstance?.winner?.encodeAsHTML()}</g:link></span>
				</li>
				</g:if>

                <g:if test="${listingInstance?.latestBid}">
                    <li class="fieldcontain">
                        <span id="bids-label" class="property-label">
                            <g:message code="listing.latest.label" default="Latest Bid" /></span>
                        <span class="property-value" aria-labelledby="bids-label">
                            <g:link controller="bid" action="show" id="${listingInstance.latestBid.id}">${listingInstance.latestBid?.amount.encodeAsHTML()}</g:link></span>
                     </li>
                </g:if>

				<g:if test="${listingInstance?.startingPrice}">
				<li class="fieldcontain">
					<span id="startingPrice-label" class="property-label">
                        <g:message code="listing.startingPrice.label" default="Starting Price" /></span>
					<span class="property-value" aria-labelledby="startingPrice-label">
                        <g:fieldValue bean="${listingInstance}" field="startingPrice"/></span>
				</li>
				</g:if>

			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${listingInstance?.id}" />
					%{---<g:link class="addBid" action="addBid" id="${listingInstance?.id}"><g:message code="default.button.edit.label" default="Bid on item" /></g:link>}--}%
                    <g:link class="addBid" controller="bid" action="create" id="${listingInstance?.id}"><g:message code="default.button.edit.label" default="Bid on item" /></g:link>
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
