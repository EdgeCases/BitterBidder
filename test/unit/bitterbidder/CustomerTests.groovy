package bitterbidder.test.unit



import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor()
class CustomerTests {

    void testSomething() {
       fail "Implement me"

    }

    @Test
    void test_Email_WhenValid_NoValidationError(){
        fail "Implement me"
    }

    @Test
    void test_Email_WhenNullOrEmpty_HasValidationErrors(){
        fail "Implement me"
    }
    @Test
    void test_Email_WhenNoAtSign_HasValidationError(){
        fail "Implement me"
    }

    @Test
    void test_Email_WhenNoDotDomain_HasValidationError(){
        fail "Implement me"
    }
}
