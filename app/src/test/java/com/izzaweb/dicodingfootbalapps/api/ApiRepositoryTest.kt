package com.izzaweb.dicodingfootbalapps.api

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class ApiRepositoryTest{
    @Test
    fun testDoRequest(){
        val apiRepository = mock(ApiRepository::class.java)
        val url ="https://thesportdb.com/api/v1/json/1/eventspastleague.php?l=English%20Premier%20League"
        apiRepository.doRequest(url)
        verify(apiRepository).doRequest(url)
    }
}