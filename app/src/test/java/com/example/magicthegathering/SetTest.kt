package com.example.magicthegathering

import com.example.magicthegathering.network.MagicApi
import com.example.magicthegathering.network.RetrofitService
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class SetTest {

    @Test
    suspend fun givenInitialRequest_shouldHaveListSets() {
        val api = RetrofitService.createService(MagicApi::class.java)
        val response = api.getSetsAsync().await()

        assertTrue(response.isSuccessful)
    }
}