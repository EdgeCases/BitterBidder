package bitterbidder

import static org.junit.Assert.*
import org.junit.*

class CustomerIntegrationTests {

    final static customer1EmailAddress = 'customer1@email.com'
    final static customer2EmailAddress = 'customer2@email.com'
    final static password = 'password'

    @Before
    void setUp() {
        // Setup logic here
    }

    @After
    void tearDown() {
        // Tear down logic here
    }

    @Test   // C-2
    void test_Save_WhenEmailIsUnique_CustomerIsSaved(){
        // arrange
        def customer1 = new Customer(emailAddress: customer1EmailAddress, password: password)
        def customer2 = new Customer(emailAddress: customer2EmailAddress, password: password)

        //act
        customer1.save(flush: true)
        customer2.save(flush: true)

        //assert
        assert Customer.findByEmailAddress(customer1EmailAddress) != null
        assert Customer.findByEmailAddress(customer2EmailAddress) != null
    }

    @Test   // C-2
    void test_Save_WhenEmailIsNotUnique_CustomerIsNotSaved(){
        //arrange
        def customer1 = new Customer(emailAddress: customer1EmailAddress, password: password)

        //act
        customer1.save(flush: true)

        //assert
        assert (new Customer(emailAddress: customer1.emailAddress, password: password).save(flush: true)) == null
    }
}
