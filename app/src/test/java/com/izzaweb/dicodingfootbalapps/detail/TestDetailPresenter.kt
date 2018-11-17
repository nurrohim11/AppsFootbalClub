package com.izzaweb.dicodingfootbalapps.detail

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
    private lateinit var view: DetailEventView
    @Mock
    private lateinit var gson: Gson
    @Mock
    private lateinit var apiRepository: ApiRepository
    @Mock
    private lateinit var presenter: DetailEventPresenter

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        presenter = DetailEventPresenter(view, apiRepository, gson, TestContextProvider())
    }
    @Test
    fun testGetNextMatchLisy(){
        val events :MutableList<Events> = mutableListOf()
        val response = EventsResponse(events)
        val id="1100"

        Mockito.`when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getEventDetail(id)),
                EventsResponse::class.java)).thenReturn(response)
        presenter.getDetailEvent(id)

        Mockito.verify(view).showLoading()
        Mockito.verify(view).showDetailEvent(events)
        Mockito.verify(view).hideLoading()
    }
}