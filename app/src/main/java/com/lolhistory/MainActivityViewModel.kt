package com.lolhistory

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lolhistory.datamodel.SummonerIDInfo
import com.lolhistory.datamodel.SummonerRankInfo
import com.lolhistory.repository.RiotRepository
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable

class MainActivityViewModel: ViewModel() {

    private val repo = RiotRepository()

    private val summonerIDInfoLiveData = MutableLiveData<SummonerIDInfo>()
    private val summonerRankInfoLiveData = MutableLiveData<SummonerRankInfo>()

    private var summonerName = ""

    fun getSummonerIDInfoLiveDara(): MutableLiveData<SummonerIDInfo>{
        return summonerIDInfoLiveData
    }

    fun getSummonerRankInfoLiveDara(): MutableLiveData<SummonerRankInfo>{
        return summonerRankInfoLiveData
    }

    fun searchSummoner(name: String){
        summonerName = name
        Log.d("TESTLOG", "name: $name")

        repo.getSummonerIdInfo(name).subscribe(object: SingleObserver<SummonerIDInfo>{
            override fun onSubscribe(d: Disposable) {
            }
            override fun onSuccess(summonerIDInfo: SummonerIDInfo) {
                summonerName = summonerIDInfo.name
                getSummonerRankInfo(summonerIDInfo.id)
                //소환자 랭크 정보 가져오기
            }
            override fun onError(e: Throwable) {
                Log.e("TESTLOG","getSummonerIdInfoError: $e")
                summonerIDInfoLiveData.value = null
            }


        })
    }

    fun getSummonerRankInfo(summonerId: String){
        repo.getSummonerRankInfo(summonerId)
            .subscribe(object: SingleObserver<List<SummonerRankInfo>> {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onSuccess(t: List<SummonerRankInfo>) {
                setSummonerRankInfo(t)
                }

                override fun onError(e: Throwable) {
                    Log.e("테스트 로그", "[getSummonerRankInfo] error: $e")
                }
            })
    }

    private fun setSummonerRankInfo(summonerRankInfos: List<SummonerRankInfo>){
        Log.d("TestLog","tier"+summonerRankInfos[0].tier)
        Log.d("TestLog","rank"+summonerRankInfos[0].rank)
        //솔랭/자랭중 티어가 높은걸 표시해줌
    }

}
//여기가 ViewModel