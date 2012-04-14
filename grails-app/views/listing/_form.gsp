<%@ page import="bitterbidder.Listing" %>



<div class="fieldcontain ${hasErrors(bean: listingInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="listing.description.label" default="Description" />
		
	</label>
	<g:textArea name="description" cols="40" rows="5" maxlength="255" value="${listingInstance?.description}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: listingInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="listing.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" maxlength="63" required="" value="${listingInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: listingInstance, field: 'endDateTime', 'error')} required">
	<label for="endDateTime">
		<g:message code="listing.endDateTime.label" default="End Date Time" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="endDateTime" precision="day"  value="${listingInstance?.endDateTime}"  />
</div>

%{--<div class="fieldcontain ${hasErrors(bean: listingInstance, field: 'seller', 'error')} required">--}%
	%{--<label for="seller">--}%
		%{--<g:message code="listing.seller.label" default="Seller" />--}%
		%{--<span class="required-indicator">*</span>--}%
	%{--</label>--}%
	%{--<g:select id="seller" name="seller.id" from="${bitterbidder.Customer.list()}" optionKey="id" required="" value="${listingInstance?.seller?.id}" class="many-to-one"/>--}%
%{--</div>--}%

%{--<div class="fieldcontain ${hasErrors(bean: listingInstance, field: 'winner', 'error')} ">--}%
	%{--<label for="winner">--}%
		%{--<g:message code="listing.winner.label" default="Winner" />--}%
		%{----}%
	%{--</label>--}%
	%{--<g:select id="winner" name="winner.id" from="${bitterbidder.Customer.list()}" optionKey="id" value="${listingInstance?.winner?.id}" class="many-to-one" noSelection="['null': '']"/>--}%
%{--</div>--}%

%{--<div class="fieldcontain ${hasErrors(bean: listingInstance, field: 'bids', 'error')} ">--}%
	%{--<label for="bids">--}%
		%{--<g:message code="listing.bids.label" default="Bids" />--}%
		%{----}%
	%{--</label>--}%
	%{----}%
%{--<ul class="one-to-many">--}%
%{--<g:each in="${listingInstance?.bids?}" var="b">--}%
    %{--<li><g:link controller="bid" action="show" id="${b.id}">${b?.encodeAsHTML()}</g:link></li>--}%
%{--</g:each>--}%
%{--<li class="add">--}%
%{--<g:link controller="bid" action="create" params="['listing.id': listingInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'bid.label', default: 'Bid')])}</g:link>--}%
%{--</li>--}%
%{--</ul>--}%

%{--</div>--}%

<div class="fieldcontain ${hasErrors(bean: listingInstance, field: 'startingPrice', 'error')} required">
	<label for="startingPrice">
		<g:message code="listing.startingPrice.label" default="Starting Price" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="startingPrice" required="" value="${fieldValue(bean: listingInstance, field: 'startingPrice')}"/>
</div>

