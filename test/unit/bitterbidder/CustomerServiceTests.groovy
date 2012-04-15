package bitterbidder

import grails.test.mixin.*
import org.junit.*
import grails.validation.ValidationException

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(CustomerService)
@Mock(Customer)
class CustomerServiceTests {

    // SRV-1: Create a Grails service method that supports creating a new customer (unit test)
    void test_Create_WhenCustomerIsValid_CustomerIsSaved(){
        // arrange
        def customerService = new CustomerService()
        Customer newCustomer = TestUtility.getValidCustomer()
        def customerUnderTest = TestUtility.makeCustomer(newCustomer.username, newCustomer.password, newCustomer.emailAddress)
        def Role = new Object()
        Role.metaClass.findByAuthority = {Role role -> new Role(authority: 'ROLE_USER')}

        // act
        def saved = customerService.Create(customerUnderTest)
        
        // assert
        assert customerUnderTest.id
        assert !saved.hasErrors()
    }

    // SRV-1: Create a Grails service method that supports creating a new customer (unit test)
    void test_Create_WhenCustomerIsInValid_CustomerIsNotSaved(){
        // arrange
        def customerService = new CustomerService()
        def customerUnderTest = new Customer(username: "customer", emailAddress: "invalidemailaddress", password: "password")

        // act

        // assert
        shouldFail(ValidationException) {
            customerService.Create(customerUnderTest)
        }
    }
}
