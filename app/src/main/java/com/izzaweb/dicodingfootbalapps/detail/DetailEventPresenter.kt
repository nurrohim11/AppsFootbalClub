package com.izzaweb.dicodingfootbalapps.detail

import com.google.gson.Gson
import com.izzaweb.dicodingfootbalapps.api.ApiRepository
import com.izzaweb.dicodingfootbalapps.api.TheSportDBApi
import com.izzaweb.dicodingfootbalapps.model.EventsResponse
import com.izzaweb.dicodingfootbalapps.util.CoroutineContextProvider
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.custom.async
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class DetailEventPresenter(private val view: DetailEventView,
                         private val apiRepository: ApiRepository,
                         private val gson: Gson,
                           private val context:CoroutineContextProvider= CoroutineContextProvider()){
    fun getDetailEvent(idEvent: String?){
        view.showLoading()
//        doAsync {
//            val data = gson.fromJson(apiRepository
//                    .doRequest(TheSportDBApi.getEventDetail(idEvent)),
//                    EventsResponse::class.java)
//            uiThread {
//                view.hideLoading()
//                view.showDetailEvent(data.events)
//            }
//        }
      async(context.main) {
          val data= bg {
              gson.fromJson(apiRepository
                      .doRequest(TheSportDBApi.getEventDetail(idEvent)),
                      EventsResponse::class.java)
          }
          view.showDetailEvent(data.await().events)
          view.hideLoading()
      }
    }
}