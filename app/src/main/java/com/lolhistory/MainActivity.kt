package com.lolhistory
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethod
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.lolhistory.databinding.ActivityMainBinding
import com.lolhistory.datamodel.SummonerRankInfo
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainActivityViewModel

    lateinit var inputMethodManager: InputMethodManager

    private var inVisibleInfoLayout = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        viewModel.getSummonerIDInfoLiveDara().observe(this,{summonerIDIfo ->
            if (summonerIDIfo == null){
                val notExistsToast = Toast.makeText(
                    applicationContext,
                    R.string.not_exist_summoner, Toast.LENGTH_SHORT
                )
                notExistsToast.show()
            }
        })

        viewModel.getSummonerRankInfoLiveData().observe(this, { summonerRankInfo ->
            if (summonerRankInfo != null) {
                setRankInfoView(summonerRankInfo)
                inVisibleInfoLayout = true
                binding.inputLayout.visibility = View.GONE
            }
        })

        viewModel.getHistoryAdapterLiveData().observe(this, {historyAdapter ->
            if (historyAdapter != null){
                binding.rvHistory.adapter = historyAdapter
                binding.swipeLayout.isRefreshing = false
            }else{

                val historyErrorToast = Toast.makeText(
                    applicationContext,
                    R.string.history_error, Toast.LENGTH_SHORT
                )
                historyErrorToast.show()

            }

        })
        binding.rvHistory.layoutManager = LinearLayoutManager(this)
        binding.rvHistory.setHasFixedSize(true)

        binding.swipeLayout.setOnRefreshListener { ->
            viewModel.searchSummoner(binding.tvSummonerName.text.toString())//위로 스와이프시 새로운 정보를 갱신하는 코드
        }

        binding.btnInputSummoner.setOnClickListener{ v ->
            viewModel.searchSummoner(binding.etInputSummoner.text.toString())
        }

    }

    override fun onBackPressed() {
        if (inVisibleInfoLayout){
            binding.inputLayout.visibility = View.GONE
            binding.inputLayout.visibility = View.VISIBLE
        }else{
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun setRankInfoView(rankInfo: SummonerRankInfo) {
        setTierEmblem(rankInfo.tier)
        binding.tvSummonerName.text = rankInfo.summonerName

        var tierRank = rankInfo.tier + " " + rankInfo.rank  // ex) PLATINUM IV
        binding.tvTier.text = tierRank

        if(rankInfo.tier == "UNRANKED") {  // 언랭일때
            binding.tvRankType.text = ""
            binding.tvLp.text = ""
            binding.tvTotalWinLose.text = ""
            binding.tvTotalWinRate.text = ""
        } else {
            binding.tvRankType.text = rankInfo.queueType

            val point = rankInfo.leaguePoints.toString() + " LP"
            binding.tvLp.text = point

            // 승률 소수점 2번째 자리까지 xx.xx
            val rate = rankInfo.wins.toDouble() / (rankInfo.wins + rankInfo.losses).toDouble() * 100
            binding.tvTotalWinRate.text = String.format(Locale.getDefault(), "%.2f%%", rate)

            //승패 ex) x승x패
            val winAndLosses = rankInfo.wins.toString() + "승 " + rankInfo.losses.toString() + "패"
            binding.tvTotalWinLose.text = winAndLosses
        }
        binding.infoLayout.visibility = View.VISIBLE
    }
    private  fun setTierEmblem(tier: String){
        when(tier){
            "UNRANKED" ->    binding.ivTierEmblem.setImageResource(R.drawable.emblem_unranked)
            "IRON" ->        binding.ivTierEmblem.setImageResource(R.drawable.emblem_iron)
            "BRONZE" ->      binding.ivTierEmblem.setImageResource(R.drawable.emblem_bronze)
            "SILVER" ->      binding.ivTierEmblem.setImageResource(R.drawable.emblem_silver)
            "GOLD" ->        binding.ivTierEmblem.setImageResource(R.drawable.emblem_gold)
            "PLATINUM" ->    binding.ivTierEmblem.setImageResource(R.drawable.emblem_platinum)
            "DIAMOND" ->     binding.ivTierEmblem.setImageResource(R.drawable.emblem_diamond)
            "MASTER" ->      binding.ivTierEmblem.setImageResource(R.drawable.emblem_master)
            "GRANDMASTER" -> binding.ivTierEmblem.setImageResource(R.drawable.emblem_grandmaster)
            "CHALLENGE" ->   binding.ivTierEmblem.setImageResource(R.drawable.emblem_challenger)

        }
    }
}
