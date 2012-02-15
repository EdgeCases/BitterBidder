package bitterbidder

import static org.junit.Assert.*
import org.junit.*
import grails.test.mixin.TestFor
import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin

@TestMixin(GrailsUnitTestMixin)
@TestFor(Customer)
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
                emailAddress: validEmail1,
                password: validPassword
        )
        customerUnderTest = new Customer(
                emailAddress: validEmail2,
                password: validPassword
        )
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
        // MikeG: this test doesn't work within the ide but works using grails test-app

        //arrange
        customer.save(flush: true)
        customerUnderTest.emailAddress = customer.emailAddress

        //act
        customerUnderTest.save(flush: true)

        //assert
        assert Customer.findAllByEmailAddress(validEmail1).size() == 1

        //restore
        customerUnderTest.emailAddress = validEmail2
    }
}
