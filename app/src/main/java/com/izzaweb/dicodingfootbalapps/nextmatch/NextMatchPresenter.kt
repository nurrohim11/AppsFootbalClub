package com.izzaweb.dicodingfootbalapps.nextmatch

import com.google.gson.Gson
import com.izzaweb.dicodingfootbalapps.api.ApiRepository
import com.izzaweb.dicodingfootbalapps.api.TheSportDBApi
import com.izzaweb.dicodingfootbalapps.model.EventsResponse
import com.izzaweb.dicodingfootbalapps.util.CoroutineContextProvider
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class NextMatchPresenter(private val view: NextMatchView,
                         private val apiRepository: ApiRepository,
                         private val gson: Gson,
                         private val context: CoroutineContextProvider = CoroutineContextProvider()){
    fun getMatchList(league: String?){
        view.showLoading()
//        doAsync {
//            val data = gson.fromJson(apiRepository
//                    .doRequest(TheSportDBApi.getNextMatch(league)),
//                    EventsResponse::class.java)
//            uiThread {
//                view.hideLoading()
//                view.showMatchLust(data.events)
//            }
//        }
        async (context.main){
            val data = bg{
                gson.fromJson(apiRepository
                    .doRequest(TheSportDBApi.getNextMatch(league)),
                    EventsResponse::class.java)
            }
            view.showMatchLust(data.await().events)
            view.hideLoading()
        }
    }
}