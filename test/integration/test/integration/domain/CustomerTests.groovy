package test.integration.domain;


import org.junit.After
import org.junit.Before
import org.junit.Test
import bitterbidder.Customer
import org.springframework.test.annotation.ExpectedException
import grails.test.mixin.TestFor

class CustomerTests extends GroovyTestCase{

    @Before
    void setUp() {
        // Setup logic here
    }

    @After
    void tearDown() {
        // Tear down logic here
    }

    @Test
    void test_Save_WhenEmailIsUnique_CustomerIsSaved(){
        //arrange
        //assume
        //act
        //assert
        //arrange
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
        Customer customer1 = new Customer(emailAddress: 'customer@email.com', password: 'password')
        Customer customer2 = new Customer(emailAddress: 'customer@email.com', password: 'password')

        //act
        customer1.save()
        customer2.save()

        //assert
        assertNotNull (customer2.errors["emailAddress"])
    }
}