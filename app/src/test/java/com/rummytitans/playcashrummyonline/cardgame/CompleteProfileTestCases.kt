package com.rummytitans.playcashrummyonline.cardgame

import com.rummytitans.playcashrummyonline.cardgame.utils.isEmptyString
import com.rummytitans.playcashrummyonline.cardgame.utils.validTeamNameTestCase
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import org.junit.Test


class CompleteProfileTestCases {

    @Test
    fun blankTeamName() {
        assertTrue(isEmptyString(""))
    }

    @Test
    fun invalidTeamName() {
        assertFalse(validTeamNameTestCase("asasasasasasasasasasasasasasasasasasasasas"))
    }

    @Test
    fun validTeamName() {
        assertTrue(validTeamNameTestCase("ItsMyTeam"))
    }

    @Test
    fun invalidName() {
        assertTrue(isEmptyString(""))
    }

    @Test
    fun validName() {
        assertTrue(!isEmptyString("fbhfdhdfhfg"))
    }

    @Test
    fun invalidState() {
        assertTrue(isEmptyString(""))
    }

    @Test
    fun validSate() {
        assertTrue(!isEmptyString("fbhfdhdfhfg"))
    }
}

