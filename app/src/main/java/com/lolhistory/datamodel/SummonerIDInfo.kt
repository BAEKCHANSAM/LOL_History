package com.lolhistory.datamodel

import android.accounts.Account
import com.google.gson.annotations.SerializedName

data class SummonerIDInfo (
    @SerializedName("id")
    var id: String,
    // id == 내 아이디

    @SerializedName("accountId")
    var account: String,

        @SerializedName("puuid")
    var puuid: String,

    @SerializedName("name")
    var name: String,

    @SerializedName("summonerLevel")
    var summonerLevel: Int,

        )
//이러한 형식으로 저장할 것 이라는 것을 정의 (RIOTAPI.kt 클래스에 있음)