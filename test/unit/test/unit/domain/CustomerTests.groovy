package test.unit.domain;

import grails.test.mixin.TestFor
import org.junit.Test

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor()
class CustomerTests extends GroovyTestCase {

    void testSomething() {
       fail "Implement me"

    }
    @Test
    void test_Email_WhenValid_CustomerIsValid(){
        fail "Implement me"
    }

    @Test
    void test_Email_WhenNullOrEmpty_CustomerIsInvalids(){
        fail "Implement me"
    }
    @Test
    void test_Email_WhenNoAtSign_CustomerIsInvalid(){
        fail "Implement me"
    }

    @Test
    void test_Email_WhenNoDotDomain_CustomerIsInvalid(){
        fail "Implement me"
        //
    }

    @Test
    void test_Password_WhenGreaterThanMax_CustomerIsInvalid() {
        //arrange
      
        //act
        //assert
        fail "Not implemented"
    }

    @Test
    void test_Password_WhenLessThanMin_CustomerIsInvalid() {
        //arrange

        //act
        //assert
        fail "Not implemented"
    }

    @Test
    void test_Password_WhenEqualsToMax_CustomerIsValid() {
        //arrange
        
        //act
        //assert
        fail "Not implemented"
    }
    @Test
    void test_Password_WhenEqualsToMin_CustomerIsValid(){
    }

    @Test
    void test_Password_WhenLengthInValidRange_CustomerIsValid() {
        //arrange
        //act
        //assert
        fail "Not implemented"
    }

    @Test
    void test_Password_WhenNull_CustomerIsInvalid() {
        //arrange
        
        //act
        //assert
        fail "Not implemented"
    }

    @Test
    void test_Password_WhenEmpty_CustomerIsInvalid() {
        //arrange
        
        //act
        //assert
        fail "Not implemented"
    }

    @Test
    void test_Password_WhenAllSpaces_CustomerIsInvalid() {
        //arrange

        //act
        //assert
        fail "Not implemented"
    }

    @Test
    void test_Password_WhenAllTabs_CustomerIsInvalid() {
        //arrange

        //act
        //assert
        fail "Not implemented"
    }

}
