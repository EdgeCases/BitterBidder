package bitterbidder

import grails.test.mixin.*
import grails.test.mixin.support.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
@TestFor(Customer)
class CustomerTests {

    final static invalidEmail_MissingAtSign = "customeremail.com"
    final static invalidEmail_MissingDotDomain = "customer@email"
    final static validEmail = "customer@email.com"
    final static validPassword = "password"

    Customer customerUnderTest

    @Before
    void setUp() {
        // Setup logic here
        customerUnderTest = new Customer(
                username: "user",
                emailAddress: validEmail,
                password: validPassword
        )

        def springSecurityService = new Object()
        springSecurityService.metaClass.encodePassword = {String password -> "ENCODED_PASSWORD"}

        customerUnderTest.springSecurityService = springSecurityService
    }

    @After
    void tearDown() {
        // Tear down logic here
        customerUnderTest = null
    }

    // C-1: Customers have email address, password and created date fields (unit test)
    void test_Email_WhenValid_CustomerIsValid(){
        // arrange
        mockForConstraintsTests(Customer, [customerUnderTest])

        //assert
        assert customerUnderTest.validate()
    }

    // C-1: Customers have email address, password and created date fields (unit test)
    void test_Email_WhenNull_CustomerIsInvalid(){
        //arrange
        mockForConstraintsTests(Customer, [customerUnderTest])
        customerUnderTest.emailAddress = null

        //act
        customerUnderTest.validate()

        //assert
        assert "nullable" == customerUnderTest.errors["emailAddress"]

        // restore
        customerUnderTest.emailAddress = validEmail
    }

    // C-1: Customers have email address, password and created date fields (unit test)
    void test_Created_WhenCustomerSaved_DateCreatedIsValid() {
        //arrange
        mockDomain(Customer, [customerUnderTest])

        //act
        customerUnderTest.save(flush: true)

        //assert
        assert customerUnderTest.dateCreated != null
    }

    void test_DisplayEmailAddress_WhenEmailHasAtSign_ReturnsValueBeforeAtSign(){
        def customer = new Customer(emailAddress: 'danny@mail.com')
        assert customer.displayEmailAddress=='danny';
    }

    void test_DisplayEmailAddress_WhenEmailDoesNotHaveAtSign_ReturnEmail(){
        def customer = new Customer(emailAddress: 'dannymail.com')
        assert customer.displayEmailAddress=='dannymail.com';
    }

    void test_DisplayEmailAddress_WhenEmailIsNull_ReturnNull(){

        def customer = new Customer(emailAddress: null)
        assert customer.displayEmailAddress==null;
    }

    // C-3: Email address must be of a valid form (@.*) (unit test)
    void test_Email_WhenNoAtSign_CustomerIsInvalid(){
        //arrange
        customerUnderTest.emailAddress = invalidEmail_MissingAtSign
        mockForConstraintsTests(Customer, [customerUnderTest])
        
        //act
        customerUnderTest.validate()
        
        //assert
        assert "email" == customerUnderTest.errors["emailAddress"]

        // restore
        customerUnderTest.emailAddress = validEmail
    }

    // C-3: Email address must be of a valid form (@.*) (unit test)
    void test_Email_WhenNoDotDomain_CustomerIsInvalid(){
        //arrange
        customerUnderTest.emailAddress = invalidEmail_MissingDotDomain
        mockForConstraintsTests(Customer, [customerUnderTest])

        //act
        customerUnderTest.validate()

        //assert
        assert "email" == customerUnderTest.errors["emailAddress"]

        // restore
        customerUnderTest.emailAddress = validEmail
    }
}
