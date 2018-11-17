package com.izzaweb.dicodingfootbalapps.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Teams(
        @SerializedName("idTeam")
        var teamId: String? = null,

        @SerializedName("strTeam")
        var teamName: String? = null,

        @SerializedName("strDescriptionEN")
        var strDesc: String? = null,

        @SerializedName("intFormedYear")
        var intYear: String? = null,

        @SerializedName("strStadium")
        var strStadium: String? = null,

        @SerializedName("strTeamBadge")
        var teamBadge: String? = null
)