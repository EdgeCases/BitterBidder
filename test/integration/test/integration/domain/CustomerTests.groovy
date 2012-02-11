package test.integration.domain;

import bitterbidder.Customer

class CustomerTests extends GroovyTestCase{

    final static duplicateCustomer1EmailAddress = 'customer1@email.com'
    final static password = 'password'
    final static uniqueCustomer1EmailAddress = 'customer1@email.com'
    final static uniqueCustomer2EmailAddress = 'customer2@email.com'

    private duplicateCustomer1
    private uniqueCustomer1
    private uniqueCustomer2

    void setUp() {
        // Setup logic here
        duplicateCustomer1 = new Customer(emailAddress: duplicateCustomer1EmailAddress, password: password)
        uniqueCustomer1 = new Customer(emailAddress: uniqueCustomer1EmailAddress, password: password)
        uniqueCustomer2 = new Customer(emailAddress: uniqueCustomer2EmailAddress, password: password)
    }

    void tearDown() {
        // Tear down logic here
    }

    void test_Save_WhenEmailIsUnique_CustomerIsSaved(){
        //act
        uniqueCustomer1.save()
        uniqueCustomer2.save()
        
        //assert
        assertNotNull(Customer.findByEmailAddress(uniqueCustomer1EmailAddress))
        assertNotNull(Customer.findByEmailAddress(uniqueCustomer2EmailAddress))
     }

    void test_Save_WhenEmailIsNotUnique_CustomerIsNotSaved(){
        //act
        uniqueCustomer1.save()
        duplicateCustomer1.save()

        //assert
        assertNotNull (duplicateCustomer1.errors["emailAddress"])
    }
}