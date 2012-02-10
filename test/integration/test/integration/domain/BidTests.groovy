package test.integration.domain

import grails.test.mixin.TestFor
import bitterbidder.Bid
import org.junit.Test

@TestFor(Bid)
class BidTests extends GroovyTestCase {

    @Test
    void test_Save_WhenAmountIsGreaterThanLastBidThreshold_SavesSuccessfully() {
        //arrange

        //act
        //assert
        fail "Not implemented"
    }

    @Test
    void test_Save_WhenBidNotAssociatedWithListing_SaveFails() {
        //arrange

        //act
        //assert
        fail "Not implemented"
    }

    @Test
    void test_Save_WhenAmountIsLessThanLastBidThreshold_SavesSuccessfully() {
        //arrange

        //act
        //assert
        fail "Not implemented"
    }
}
