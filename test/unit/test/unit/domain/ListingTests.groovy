package test.unit.domain;

import bitterbidder.Listing
import grails.test.mixin.TestFor
import org.junit.Test

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Listing)
class ListingTests extends GroovyTestCase {

    void testSomething() {
        fail "Implement me"
    }

    @Test
    void test_Seller_WhenNull_ListingIsInvalid() {
        //arrange
        
        //act
        //assert
        fail "Not implemented"
    }

    @Test
    void test_Seller_WhenSellerIsInvalid_WhatDoWeDoHere() {
        //arrange
        
        //act
        //assert
        fail "Not implemented"
    }
    
    @Test
    void test_Winner_WhenNull_ListingIsValid() {
        //arrange
                    
        //act
        //assert
        fail "Not implemented"
    }

    @Test
    void test_Winner_WhenWinnerIsInvalid_WhatDoWeDoHere_() {
        //arrange

        //act
        //assert
        fail "Not implemented"
    }

    @Test
    void test_Description_WhenInvalid_ListingIsInvalid() {
        //arrange
                
        //act
        //assert
        fail "Not implemented"
    }

    @Test
    void test_Date_WhenInvalid_ListingIsInvalid() {
        //arrange
        
        //act
        //assert
        fail "Not implemented"
    }

    @Test
    void test_Name_WhenInvalid_ListingIsInvalid() {
        //arrange
        def listing = new Listing(name: "name")
        //act
        def result = listing.validate();
        //assert

        fail "Not implemented"
    }
}
