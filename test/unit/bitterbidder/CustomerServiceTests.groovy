package bitterbidder

import grails.test.mixin.*
import org.junit.*

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
        def newCustomer = TestUtility.getValidCustomer()
        def customerUnderTest = TestUtility.makeValidCustomer(newCustomer.username, newCustomer.password, newCustomer.emailAddress)
        
        // act
        def saved = customerService.createNewCustomer(customerUnderTest)
        
        // assert
        assert customerUnderTest.id
        assert !saved.hasErrors()
    }
}
