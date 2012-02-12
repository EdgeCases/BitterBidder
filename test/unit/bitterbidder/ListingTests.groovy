package bitterbidder

import static org.junit.Assert.*

import grails.test.mixin.*
import grails.test.mixin.support.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
class ListingTests {

    void setUp() {
        // Setup logic here
    }

    void tearDown() {
        // Tear down logic here
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
