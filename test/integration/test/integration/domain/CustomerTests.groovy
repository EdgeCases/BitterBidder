package test.integration.domain;


import org.junit.After
import org.junit.Before
import org.junit.Test
import static org.junit.Assert.fail

class CustomerTests {

    @Before
    void setUp() {
        // Setup logic here
    }

    @After
    void tearDown() {
        // Tear down logic here
    }

    @Test
    void testSomething() {
        fail "Implement me"
    }

    @Test
    void test_Save_WhenEmailIsUnique_CustomerIsSaved(){
        //arrange
        //assume
        //act
        //assert
    }

    @Test
    void test_Save_WhenEmailIsUnique_CustomerHasNoValidationErrors(){
        //arrange
        //assume
        //act
        //assert
    }
    @Test
    void test_Save_WhenEmailIsNotUnique_CustomerIsNull(){
        //arrange
        //assume
        //act
        //assert
    }

    @Test
    void test_Save_WhenEmailIsNotUnique_CustomerHasValidationErrors(){
        //arrange
        //assume
        //act
        //assert
    }
}