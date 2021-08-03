package com.lolhistory.retrofit

import com.lolhistory.datamodel.SummonerIDInfo
import com.lolhistory.datamodel.SummonerRankInfo
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path



interface RiotAPI {
    @Headers("Accept: Application/json","X-Riot-Token:" + BaseUrl.RIOT_API_KEY)
    @GET(BaseUrl.RIOT_API_GET_SUMMONER + "{userId}")
    fun getSummonerIdInfo(@Path("userId")userId: String):Single<SummonerIDInfo>   //이 (SummonerIDInfo로 갈것) 형식에 맞춰 저장한다

    @Headers("Accept: Application/json","X-Riot-Token:" + BaseUrl.RIOT_API_KEY)
    @GET(BaseUrl.RIOT_API_GET_RANK + "{id}")
    fun getSummonerRankInfo (@Path("id")userId: String):Single<List<SummonerRankInfo>>

}
