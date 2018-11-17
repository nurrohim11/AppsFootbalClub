package com.izzaweb.dicodingfootbalapps.api

import android.net.Uri
import com.izzaweb.dicodingfootbalapps.BuildConfig

object TheSportDBApi {

    private const val SportsApiUrl = BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}"

    fun getPrevMatch(league:String?) = "$SportsApiUrl/eventspastleague.php?id=$league"

    fun getNextMatch(league:String?) = "$SportsApiUrl/eventsnextleague.php?id=$league"

    fun getEventDetail(eventId:String?) = "$SportsApiUrl/lookupevent.php?id=$eventId"
}