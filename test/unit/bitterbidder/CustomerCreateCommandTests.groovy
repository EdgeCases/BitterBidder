package bitterbidder

import grails.test.mixin.TestMixin
import grails.test.mixin.web.ControllerUnitTestMixin
import org.junit.Test
import org.junit.Before
import org.junit.After

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestMixin(ControllerUnitTestMixin)
class CustomerCreateCommandTests {

    final static emptyString = ""
    final static invalidPassword_TooLong = "longpassword"
    final static invalidPassword_TooShort = "short"
    final static validEmail = "customer@email.com"
    final static validPassword = "password"
    final static validPassword_MediumLength = "passwrd"
    final static validPassword_MinimumLength = "paswrd"
    final static validUserName = "user"

    CustomerCreateCommand customerCreateCommandUnderTest

    @Before
    void setUp() {
        // Setup logic here
        customerCreateCommandUnderTest = new CustomerCreateCommand(
                username: validUserName,
                emailAddress: validEmail,
                password: validPassword
        )
    }

    @After
    void tearDown() {
        // Tear down logic here
        customerCreateCommandUnderTest = null
    }

    // C-4: Password must be between 6-8 characters (unit test)
    void test_Password_WhenGreaterThanMax_CustomerIsInvalid() {
        //arrange
        customerCreateCommandUnderTest.password = invalidPassword_TooLong

        //act
        customerCreateCommandUnderTest.validate()

        //assert
        assert invalidPassword_TooLong == customerCreateCommandUnderTest.errors["password"].rejectedValue
    }

    // C-4: Password must be between 6-8 characters (unit test)
    void test_Password_WhenLessThanMin_CustomerIsInvalid() {
        //arrange
        customerCreateCommandUnderTest.password = invalidPassword_TooShort

        //act
        customerCreateCommandUnderTest.validate()

        //assert
        assert invalidPassword_TooShort == customerCreateCommandUnderTest.errors["password"].rejectedValue
    }

    // C-4: Password must be between 6-8 characters (unit test)
    void test_Password_WhenEqualsToMin_CustomerIsValid(){
        //arrange
        customerCreateCommandUnderTest.password = validPassword_MinimumLength

        //act
        customerCreateCommandUnderTest.validate()

        //assert
        assert customerCreateCommandUnderTest.errors.errorCount == 0
    }

    // C-4: Password must be between 6-8 characters (unit test)
    void test_Password_WhenLengthInValidRange_CustomerIsValid() {
        //arrange
        customerCreateCommandUnderTest.password = validPassword_MediumLength

        //act
        customerCreateCommandUnderTest.validate()

        //assert
        assert customerCreateCommandUnderTest.errors.errorCount == 0
    }

    // C-4: Password must be between 6-8 characters (unit test)
    void test_Password_WhenNull_CustomerIsInvalid() {
        //arrange
        customerCreateCommandUnderTest.password = null

        //act
        customerCreateCommandUnderTest.validate()

        //assert
        assert null == customerCreateCommandUnderTest.errors["password"].rejectedValue
    }

    // C-4: Password must be between 6-8 characters (unit test)
    void test_Password_WhenEmpty_CustomerIsInvalid() {
        //arrange
        customerCreateCommandUnderTest.password = emptyString

        //act
        customerCreateCommandUnderTest.validate()

        //assert
        assert emptyString == customerCreateCommandUnderTest.errors["password"].rejectedValue
    }
}
