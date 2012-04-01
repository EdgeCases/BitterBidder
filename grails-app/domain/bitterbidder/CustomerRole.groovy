package bitterbidder

import org.apache.commons.lang.builder.HashCodeBuilder

class CustomerRole implements Serializable {

	Customer customer
	Role role

	boolean equals(other) {
		if (!(other instanceof CustomerRole)) {
			return false
		}

		other.customer?.id == customer?.id &&
			other.role?.id == role?.id
	}

	int hashCode() {
		def builder = new HashCodeBuilder()
		if (customer) builder.append(customer.id)
		if (role) builder.append(role.id)
		builder.toHashCode()
	}

	static CustomerRole get(long customerId, long roleId) {
		find 'from CustomerRole where customer.id=:customerId and role.id=:roleId',
			[customerId: customerId, roleId: roleId]
	}

	static CustomerRole create(Customer customer, Role role, boolean flush = false) {
		new CustomerRole(customer: customer, role: role).save(flush: flush, insert: true)
	}

	static boolean remove(Customer customer, Role role, boolean flush = false) {
		CustomerRole instance = CustomerRole.findByCustomerAndRole(customer, role)
		if (!instance) {
			return false
		}

		instance.delete(flush: flush)
		true
	}

	static void removeAll(Customer customer) {
		executeUpdate 'DELETE FROM CustomerRole WHERE customer=:customer', [customer: customer]
	}

	static void removeAll(Role role) {
		executeUpdate 'DELETE FROM CustomerRole WHERE role=:role', [role: role]
	}

	static mapping = {
		id composite: ['role', 'customer']
		version false
	}
}
