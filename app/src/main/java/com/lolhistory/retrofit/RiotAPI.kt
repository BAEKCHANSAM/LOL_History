package com.lolhistory.retrofit

import com.lolhistory.datamodel.MatchHistory
import com.lolhistory.datamodel.SummonerIDInfo
import com.lolhistory.datamodel.SummonerRankInfo
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface RiotAPI {
    @Headers("Accept: Application/json","X-Riot-Token:" + BaseUrl.RIOT_API_KEY)
    @GET(BaseUrl.RIOT_API_GET_SUMMONER + "{userId}")
    fun getSummonerIdInfo(@Path("userId")userId: String):Single<SummonerIDInfo>   //이 (SummonerIDInfo 갈것) 형식에 맞춰 저장한다

    @Headers("Accept: Application/json","X-Riot-Token:" + BaseUrl.RIOT_API_KEY)
    @GET(BaseUrl.RIOT_API_GET_RANK + "{id}")
    fun getSummonerRankInfo (@Path("id")userId: String):Single<List<SummonerRankInfo>>//이 (SummonerRankInfo 갈것) 형식에 맞춰 저장한다

    /////////////////////////////////////////////////////////////////////////////////////////

    //매치 리스트
    @Headers("Accept: Application/json","X-Riot-Token: " + BaseUrl.RIOT_API_KEY)
    @GET(BaseUrl.RIOT_API_GET_MATCH_LIST + "{puuid}/ids")
    fun getMatchHistoryList(@Path("puuid")puuid: String): Single<ArrayList<String>>

    //매치 히스토리
    @Headers("Accept: Application/json","X-Riot-Token: " + BaseUrl.RIOT_API_KEY)
    @GET(BaseUrl.RIOT_API_GET_MATCH + "{matchId}")
    fun getMatchHistory(@Path("matchId")matchId: String): Single<MatchHistory>

}
