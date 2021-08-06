package com.lolhistory.retrofit

class BaseUrl {
    companion object{

        const val RIOT_API_KEY ="RGAPI-fcfcbb56-6b1b-4a45-9623-7853b8816380" //이게 라이엇 디벨로퍼 API 키


        const val RIOT_API_BASE_URL = "https://kr.api.riotgames.com/"  //이건 라이엇 주소
        const val RIOT_API_V5_BASE_URL = "https://asia.api.riotgames.com/"  //새로운걸 시도하는 라이엇의 주소 위에 상위호환(?)


        const val RIOT_API_GET_SUMMONER = "/lol/summoner/v4/summoners/by-name/" //이름 검색후 결과를 요청하는 주소
        const val RIOT_API_GET_RANK = "/lol/league/v4/entries/by-summoner/"   //이름 검색후 결과를 가져오는 주소

        const val RIOT_API_GET_MATCH_LIST = "/lol/match/v5/matches/by-puuid/"
        const val RIOT_API_GET_MATCH = "/lol/match/v5/matches/"


        //여기는 챔피언 사진 가져오는 코드
        const val RIOT_DATA_DRAGON_GET_CHAMPION_PORTRAIT = "https://ddragon.leagueoflegends.com/cdn/11.15.1/img/champion/"
        const val RIOT_DATA_DRAGON_GET_ITEM_IMAGE = "https://ddragon.leagueoflegends.com/cdn/11.15.1/img/item/"
        const val RIOT_DATA_DRAGON_GET_SPELL_IMAGE = "https://ddragon.leagueoflegends.com/cdn/11.15.1/img/spell/"
        const val RIOT_DATA_DRAGON_GET_RUNE_IMAGE = "https://ddragon.leagueoflegends.com/cdn/img/"

    }
}
