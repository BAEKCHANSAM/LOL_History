package com.lolhistory

import android.content.Context
import android.media.AsyncPlayer
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lolhistory.databinding.ActivityMainBinding
import com.lolhistory.databinding.MatchHistoryItemBinding
import com.lolhistory.datamodel.MatchHistory
import com.lolhistory.parser.QueueParser
import com.lolhistory.parser.RuneParser
import com.lolhistory.parser.SpellParser
import com.lolhistory.retrofit.BaseUrl
import java.util.*
import kotlin.collections.ArrayList

class HistoryAdapter (

    private var matchHistories: ArrayList<MatchHistory>,
    private val puuid: String)
            : RecyclerView.Adapter<HistoryAdapter.ViewHolder>(){

    private lateinit var context: Context

    inner class ViewHolder(
        private val binding: MatchHistoryItemBinding)
        :RecyclerView.ViewHolder(binding.root){
            fun bind(matchHistory: MatchHistory, playerIndex: Int){

                //승패색깔 및 글자
            if(matchHistory.info.participants[playerIndex].win){
                binding.resultLayout.setBackgroundColor(context.getColor(R.color.colorWin))//승 패 색깔바꿔주는 코드
                binding.tvResult.setText(R.string.win)
              }else{
                binding.resultLayout.setBackgroundColor(context.getColor(R.color.colorDefeat))//승 패 색깔바꿔주는 코드
                binding.tvResult.setText(R.string.defalt)
            }

                //게임시간
                binding.tvDurationTime.text = getDurationTime(matchHistory.info.gameDuration)

                //큐 타입
                binding.tvGameType.text = getQueueType(matchHistory.info.queueId)

                //스펠
                Glide.with(context)
                    .load(getSpellImageURL(matchHistory, playerIndex,1))
                    .into(binding.ivSummonerSpell1)
                Glide.with(context)
                    .load(getSpellImageURL(matchHistory, playerIndex,2))
                    .into(binding.ivSummonerSpell2)

                //룬

                Glide.with(context)
                    .load(getRuneImageURL(matchHistory, playerIndex,1))
                    .into(binding.ivKeystoneRune)
                Glide.with(context)
                    .load(getRuneImageURL(matchHistory, playerIndex,2))
                    .into(binding.ivSecondaryRune)

                //KDA
                binding.tvKda.text = getKDA(matchHistory, playerIndex)

                //아이템
                val itemArray = arrayOf(binding.tvItem0,binding.tvItem1,binding.tvItem2,
                    binding.tvItem3,binding.tvItem4,binding.tvItem5,binding.tvItem6)
                for(i in itemArray.indices){
                    val itemUrl = getItemImageURL(matchHistory, playerIndex,i)
                    if (itemUrl.isNotEmpty()) Glide.with(context).load(itemUrl).into(itemArray[i])
                }

                //챔피언 초상화
                Glide.with(context)
                    .load(getChampionPortait(matchHistory, playerIndex))
                    .into(binding.ivChampionPortrait)

            }
          }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryAdapter.ViewHolder {
       context = parent.context

        matchHistories.sortByDescending { it.info.gameCreation } //최신 게임이 제일 위로가는 코드

        val binding = MatchHistoryItemBinding
            .inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: HistoryAdapter.ViewHolder, position: Int) {

        val matchHistory = matchHistories[position]//몇번째를 보고 있는지 알려주는 코드
        holder.bind(matchHistory, getPlayerIndex(matchHistory))
    }

    override fun getItemCount(): Int {
        return matchHistories.size  //전체 갯수를 반환하는 코드
    }

    private fun getPlayerIndex (matchHistory: MatchHistory):Int{
        var i =0

        for (participants in matchHistory.info.participants){
            if(participants.puuid == puuid)break
            else i++
        }
        return i
    }

    private fun getChampionPortait(
        matchHistory: MatchHistory,
        playerIndex: Int): String {
        val championName = matchHistory.info.participants[playerIndex].championName
        return BaseUrl.RIOT_DATA_DRAGON_GET_CHAMPION_PORTRAIT + championName + ".png"
    }

    private fun getQueueType(queueId: Int):String{
        val parser = QueueParser(context)
        return parser.getQueueType(queueId)
    }

    private fun getDurationTime(millisecondTime: Long): String{

        val secondTime = millisecondTime /1000
        val min = secondTime /60
        val second = secondTime %60

        return (String.format(Locale.getDefault(),"%02d", min)+":"
                +String.format(Locale.getDefault(),"%02d", second))

    }
    private fun getKDA(matchHistory: MatchHistory, playerIndex: Int): String{
        val kills = matchHistory.info.participants[playerIndex].kills
        val deaths = matchHistory.info.participants[playerIndex].deaths
        val assists = matchHistory.info.participants[playerIndex].assists
        return "$kills / $deaths / $assists"
    }

    private fun getSpellImageURL(
        matchHistory: MatchHistory,
        playerIndex: Int,
        spellIndex: Int
    ): String {
        var spellId = 0
        if (spellIndex == 1) {
            //D
            spellId = matchHistory.info.participants[playerIndex].summoner1Id
        } else if (spellIndex == 2) {
            //F
            spellId = matchHistory.info.participants[playerIndex].summoner2Id
        }

        val parser = SpellParser(context)
        val spellName = parser.getSpellName(spellId)

        return BaseUrl.RIOT_DATA_DRAGON_GET_SPELL_IMAGE + "$spellName.png"
    }

    private fun getRuneImageURL(
        matchHistory: MatchHistory,
        playerIndex: Int,
        runeIndex: Int
    ):String {
        var runeId = 0
        for (style in matchHistory.info.participants[playerIndex].perks.styles){
            if(runeIndex == 1){
                if(style.description == "primaryStyle")runeId = style.selections[0].perk
            }else if (runeIndex == 2){
                if (style.description == "subStyle")runeId = style.style
            }
        }

        val parser = RuneParser(context)
        val icon = parser.getRuneIcon(runeId)
        return BaseUrl.RIOT_DATA_DRAGON_GET_RUNE_IMAGE + icon
    }

    private fun getItemImageURL(
        matchHistory: MatchHistory,
        playerIndex: Int,
        itemIndex: Int
    ):String {
        var itemId = 0
        when(itemIndex){
            0 -> itemId = matchHistory.info.participants[playerIndex].item0
            1 -> itemId = matchHistory.info.participants[playerIndex].item1
            2 -> itemId = matchHistory.info.participants[playerIndex].item2
            3 -> itemId = matchHistory.info.participants[playerIndex].item3
            4 -> itemId = matchHistory.info.participants[playerIndex].item4
            5 -> itemId = matchHistory.info.participants[playerIndex].item5
            6 -> itemId = matchHistory.info.participants[playerIndex].item6
        }
        return if (itemId != 0){
            Log.d("TESTLOG", "url: " + BaseUrl.RIOT_DATA_DRAGON_GET_ITEM_IMAGE + "$itemId.png")
            BaseUrl.RIOT_DATA_DRAGON_GET_ITEM_IMAGE + "$itemId.png"
        }else{
            ""
        }
    }

}
