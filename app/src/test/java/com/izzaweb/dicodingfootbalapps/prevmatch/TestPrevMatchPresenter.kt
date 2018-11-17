package com.izzaweb.dicodingfootbalapps.prevmatch

import com.google.gson.Gson
import com.izzaweb.dicodingfootbalapps.TestContextProvider
import com.izzaweb.dicodingfootbalapps.api.ApiRepository
import com.izzaweb.dicodingfootbalapps.api.TheSportDBApi
import com.izzaweb.dicodingfootbalapps.model.Events
import com.izzaweb.dicodingfootbalapps.model.EventsResponse
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class TestNextMatchPresenter{
    @Mock
    private lateinit var view: PrevMatchView
    @Mock
    private lateinit var gson: Gson
    @Mock
    private lateinit var apiRepository: ApiRepository
    @Mock
    private lateinit var presenter: PrevMatchPresenter

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        presenter = PrevMatchPresenter(view, apiRepository, gson, TestContextProvider())
    }
    @Test
    fun testGetNextMatchLisy(){
        val events :MutableList<Events> = mutableListOf()
        val response = EventsResponse(events)
        val league="English Premiere League"

        Mockito.`when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getPrevMatch(league)),
                EventsResponse::class.java)).thenReturn(response)
        presenter.getPrevMatchList(league)

        Mockito.verify(view).showLoading()
        Mockito.verify(view).showMatchLust(events)
        Mockito.verify(view).hideLoading()
    }
}