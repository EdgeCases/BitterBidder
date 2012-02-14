package bitterbidder

import static org.junit.Assert.*

import grails.test.mixin.*
import grails.test.mixin.support.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
@TestFor(Customer)
class CustomerTests {

    final static validCustomerEmail = 'customer@email.com'
    final static validPassword = 'password'
    final static emptyString = "";

    void setUp() {
        // Setup logic here
    }

    void tearDown() {
        // Tear down logic here
    }

    @Test
    void test_Email_WhenValid_CustomerIsValid(){
        //arrange
        def customer = new Customer(emailAddress: validCustomerEmail, password: validPassword)
        mockForConstraintsTests(Customer, [customer])

        //act
        def validationResult = customer.validate()

        //assert
        assert validationResult
    }

    @Test   // C-1
    void test_Email_WhenNullOrEmpty_CustomerIsInvalid(){
        //arrange
        def customer = new Customer()
        mockForConstraintsTests(Customer, [customer])

        //act
        customer.validate()

        //assert
        assert "nullable" == customer.errors["emailAddress"]
    }

    @Test   // C-3
    void test_Email_WhenNoAtSign_CustomerIsInvalid(){
        //arrange
        def customer = new Customer(emailAddress: "email.com", password: validPassword)
        mockForConstraintsTests(Customer, [customer])
        
        //act
        customer.validate()
        
        //assert
        assert "email" == customer.errors["emailAddress"]
    }

    @Test   // C-3
    void test_Email_WhenNoDotDomain_CustomerIsInvalid(){
        //arrange
        def customer = new Customer(emailAddress: "customer@email", password: validPassword)
        mockForConstraintsTests(Customer, [customer])

        //act
        customer.validate()

        //assert
        assert "email" == customer.errors["emailAddress"]
    }

    @Test   // C-4
    void test_Password_WhenGreaterThanMax_CustomerIsInvalid() {
        //arrange
        def customer = new Customer(emailAddress: validCustomerEmail, password: "longpassword")
        mockForConstraintsTests(Customer, [customer])

        //act
        customer.validate()

        //assert
        assert "size" == customer.errors["password"]
    }

    @Test   // C-4
    void test_Password_WhenLessThanMin_CustomerIsInvalid() {
        //arrange
        def customer = new Customer(emailAddress: validCustomerEmail, password: "short")
        mockForConstraintsTests(Customer, [customer])

        //act
        customer.validate()

        //assert
        assert "size" == customer.errors["password"]
    }

    @Test   // C-4
    void test_Password_WhenEqualsToMax_CustomerIsValid() {
        //arrange
        def customer = new Customer(emailAddress: validCustomerEmail, password: validPassword)
        mockForConstraintsTests(Customer, [customer])

        //act
        def validationResults = customer.validate()

        //assert
        assert validationResults
    }

    @Test   // C-4
    void test_Password_WhenEqualsToMin_CustomerIsValid(){
        //arrange
        def customer = new Customer(emailAddress: validCustomerEmail, password: "paswrd")
        mockForConstraintsTests(Customer, [customer])

        //act
        def validationResults = customer.validate()

        //assert
        assert validationResults
    }

    @Test   // C-4
    void test_Password_WhenLengthInValidRange_CustomerIsValid() {
        //arrange
        def customer = new Customer(emailAddress: validCustomerEmail, password: "passwrd")
        mockForConstraintsTests(Customer, [customer])

        //act
        def validationResults = customer.validate()

        //assert
        assert validationResults
    }

    @Test   // C-4
    void test_Password_WhenNull_CustomerIsInvalid() {
        //arrange
        def customer = new Customer(emailAddress: validCustomerEmail)
        mockForConstraintsTests(Customer, [customer])

        //act
        customer.validate()

        //assert
        assert "nullable" == customer.errors["password"]
    }

    @Test   // C-4
    void test_Password_WhenEmpty_CustomerIsInvalid() {
        //arrange
        def customer = new Customer(emailAddress: validCustomerEmail, password: emptyString)
        mockForConstraintsTests(Customer, [customer])

        //act
        customer.validate()

        //assert
        assert "blank" == customer.errors["password"]
    }

    @Test   // C-1
    void test_Created_WhenCustomerSaved_DateCreatedIsValid() {
        //arrange
        def customer = new Customer(emailAddress: validCustomerEmail, password: validPassword)
        mockDomain(Customer, [customer])

        //act
        customer.save()

        //assert
        assert customer.dateCreated != null
    }
}
