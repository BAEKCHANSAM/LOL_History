package com.lolhistory.repository

import com.lolhistory.datamodel.MatchHistory
import com.lolhistory.datamodel.SummonerIDInfo
import com.lolhistory.datamodel.SummonerRankInfo
import com.lolhistory.retrofit.APIClient
import com.lolhistory.retrofit.RiotAPI
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RiotRepository {
    private val riotAPI = APIClient.getRiotClient().create(RiotAPI::class.java)
    private val riotAPIv5 = APIClient.getRiotV4Client().create(RiotAPI::class.java)

    fun  getSummonerIdInfo(summonerName: String): Single<SummonerIDInfo> = riotAPI
        .getSummonerIdInfo(summonerName)//edittext에 이름을 입력하나ㅡㄴㄴ
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    fun getSummonerRankInfo(summonerId: String): Single<List<SummonerRankInfo>> = riotAPI
        .getSummonerRankInfo(summonerId)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())


    fun getMatchList(puuid: String):Single<ArrayList<String>> = riotAPIv5
        .getMatchHistoryList(puuid)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    fun getMatchHistory(matchId: String):Single<MatchHistory> = riotAPIv5
        .getMatchHistory(matchId)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}

//이 클래스가 mvvm에서 model에 해당 이 클래스에거 viewmodle로 뿌려줌