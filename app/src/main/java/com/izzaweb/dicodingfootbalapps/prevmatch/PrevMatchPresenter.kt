package com.izzaweb.dicodingfootbalapps.prevmatch

import com.google.gson.Gson
import com.izzaweb.dicodingfootbalapps.api.ApiRepository
import com.izzaweb.dicodingfootbalapps.api.TheSportDBApi
import com.izzaweb.dicodingfootbalapps.model.EventsResponse
import com.izzaweb.dicodingfootbalapps.util.CoroutineContextProvider
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class PrevMatchPresenter(private val view: PrevMatchView,
                         private val apiRepository: ApiRepository,
                         private val gson: Gson,
                         private val context:CoroutineContextProvider = CoroutineContextProvider()) {
    fun getPrevMatchList(league: String?) {
        view.showLoading()
//        doAsync {
//            val data = gson.fromJson(apiRepository
//                    .doRequest(TheSportDBApi.getPrevMatch(league)),
//                    EventsResponse::class.java)
//            uiThread {
//                view.hideLoading()
//                view.showMatchLust(data.events)
//            }
//        }
        async(context.main) {
            val data = bg{
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getPrevMatch(league)),
                        EventsResponse::class.java)
            }
            view.showMatchLust(data.await().events)
            view.hideLoading()
        }
    }
}