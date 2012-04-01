package bitterbidder

import static org.junit.Assert.*
import org.junit.*
import grails.test.mixin.TestFor
import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin

class CustomerIntegrationTests {

    final static validEmail1 = "customer1@email.com"
    final static validEmail2 = "customer2@email.com"
    final static validPassword = "password"

    Customer customer
    Customer customerUnderTest

    @Before
    void setUp() {
        // Setup logic here
        customer = new Customer(
                username: "customer1",
                emailAddress: validEmail1,
                password: validPassword
        )

        def customerSpringSecurityService = new Object()
        customerSpringSecurityService.metaClass.encodePassword = {String password -> "ENCODED_PASSWORD"}

        customer.springSecurityService = customerSpringSecurityService

        customerUnderTest = new Customer(
                username: "customerUnderTest",
                emailAddress: validEmail2,
                password: validPassword
        )

        def customerUnderTestSpringSecurityService = new Object()
        customerUnderTestSpringSecurityService.metaClass.encodePassword = {String password -> "ENCODED_PASSWORD"}

        customerUnderTest.springSecurityService = customerUnderTestSpringSecurityService
    }

    @After
    void tearDown() {
        // Tear down logic here
        customer = null;
        customerUnderTest = null;
    }

    @Test   // C-2: Email address must be a unique field (integration test)
    void test_Save_WhenEmailIsUnique_CustomerIsSaved(){
        // arrange

        //act
        customer.save(flush: true)
        customerUnderTest.save(flush: true)

        //assert
        assert Customer.findByEmailAddress(validEmail1) != null
        assert Customer.findByEmailAddress(validEmail2) != null
    }

    @Test   // C-2: Email address must be a unique field (integration test)
    void test_Save_WhenEmailIsNotUnique_CustomerIsNotSaved(){
        //arrange
        customer.save(flush: true)
        customerUnderTest.emailAddress = customer.emailAddress

        //act
        customerUnderTest.save(flush: true)

        //assert
        assert Customer.findAllByEmailAddress(validEmail1).size() == 1
    }
}
