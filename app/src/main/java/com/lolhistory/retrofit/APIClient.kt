package com.lolhistory.retrofit

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class APIClient {
    companion object{
        fun getRiotClient(): Retrofit{
            return Retrofit.Builder()
                .baseUrl(BaseUrl.RIOT_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }
    }
}

//서버에서 주는 정보를 만들수있는 그릇(?) 가튼거