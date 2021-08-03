package com.lolhistory.datamodel

import com.google.gson.annotations.SerializedName

data class SummonerRankInfo (
    @SerializedName("summonerName")
    var summonerName: String,

    @SerializedName("queueType")
    var queueType: String,

    @SerializedName("tier")
    var tier: String,

    @SerializedName("rank")
    var rank: String,

    @SerializedName("leaguePoints")
    var leaguePoints: Int,

    @SerializedName("wins")
    var wins: Int,

    @SerializedName("losses")
    var losses: Int,

    )
//이러한 형식으로 저장할 것 이라는 것을 정의 (RiotAPI.kt 클래스에 있음)
