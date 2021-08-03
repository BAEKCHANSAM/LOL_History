package com.lolhistory.retrofit

class BaseUrl {
    companion object{

        const val RIOT_API_KEY ="RGAPI-7ed8cdec-4168-4c4d-a733-2c58b458fd2c" //이게 라이엇 디벨로퍼 API 키


        const val RIOT_API_BASE_URL = "https://kr.api.riotgames.com/"  //이건 라이엇 주소
        const val RIOT_API_V5_BASE_URL = "https://asia.api.riotgames.com/"  //새로운걸 시도하는 라이엇의 주소 위에 상위호환(?)


        const val RIOT_API_GET_SUMMONER = "/lol/summoner/v4/summoners/by-name/" //이름 검색후 결과를 요청하는 주소
        const val RIOT_API_GET_RANK = "/lol/league/v4/entries/by-summoner/"   //이름 검색후 결과를 가져오는 주소

    }
}
