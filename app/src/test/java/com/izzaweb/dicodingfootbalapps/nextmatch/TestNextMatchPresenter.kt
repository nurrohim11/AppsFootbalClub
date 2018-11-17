package com.izzaweb.dicodingfootbalapps.nextmatch

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
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class TestNextMatchPresenter{
    @Mock
    private lateinit var view: NextMatchView
    @Mock
    private lateinit var gson: Gson
    @Mock
    private lateinit var apiRepository: ApiRepository
    @Mock
    private lateinit var presenter: NextMatchPresenter

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        presenter = NextMatchPresenter(view, apiRepository, gson, TestContextProvider())
    }
    @Test
    fun testGetNextMatchLisy(){
        val events :MutableList<Events> = mutableListOf()
        val response = EventsResponse(events)
        val league="English Premiere League"

        `when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getNextMatch(league)),
                EventsResponse::class.java)).thenReturn(response)
        presenter.getMatchList(league)

        Mockito.verify(view).showLoading()
        Mockito.verify(view).showMatchLust(events)
        Mockito.verify(view).hideLoading()
    }
}