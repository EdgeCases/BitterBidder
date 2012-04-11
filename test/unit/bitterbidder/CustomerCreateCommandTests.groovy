package bitterbidder

import static org.junit.Assert.*

import grails.test.mixin.*
import grails.test.mixin.support.*
import org.junit.*
import org.codehaus.groovy.grails.validation.Validateable

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
class CustomerCreateCommandTests {

    final static invalidPassword_TooLong = "longpassword"
    final static invalidPassword_TooShort = "short"
    final static validEmail = "customer@email.com"
    final static validPassword = "password"
    final static validPassword_MediumLength = "passwrd"
    final static validPassword_MinimumLength = "paswrd"
    final static validUserName = "user"

    CustomerCreateCommand customerCreateCommandUnderTest

    void setUp() {
        // Setup logic here
        customerCreateCommandUnderTest = new CustomerCreateCommand(
                username: validUserName,
                emailAddress: validEmail,
                password: validPassword
        )
    }

    void tearDown() {
        // Tear down logic here
        customerCreateCommandUnderTest = null
    }

    @Test   // C-4: Password must be between 6-8 characters (unit test)
    void test_Password_WhenGreaterThanMax_CustomerIsInvalid() {
        //arrange
        customerCreateCommandUnderTest.password = invalidPassword_TooLong

        mockForConstraintsTests(CustomerCreateCommand, [customerCreateCommandUnderTest])

        //act
        customerCreateCommandUnderTest.validate()

        //assert
        assert "size" == customerCreateCommandUnderTest.errors["password"]
    }
}
