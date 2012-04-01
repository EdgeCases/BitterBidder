<%@ page import="bitterbidder.Customer" %>


<div class="fieldcontain ${hasErrors(bean: customerInstance, field: 'username', 'error')} ">
    <label for="username">
        <g:message code="customer.emailAddress.label" default="User Name" />

    </label>
    <g:field type="text" name="username" value="${customerInstance?.username}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: customerInstance, field: 'emailAddress', 'error')} ">
	<label for="emailAddress">
		<g:message code="customer.emailAddress.label" default="Email Address" />
		
	</label>
	<g:field type="email" name="emailAddress" value="${customerInstance?.emailAddress}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: customerInstance, field: 'password', 'error')} required">
	<label for="password">
		<g:message code="customer.password.label" default="Password" />
		<span class="required-indicator">*</span>
	</label>
	<g:passwordField name="password" minlength="6" maxlength="8" required="" value="${customerInstance?.password}"/>
</div>

