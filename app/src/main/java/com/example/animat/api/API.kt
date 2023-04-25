package com.example.animat.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val URL_BASE = "https://gogoanime.consumet.stream/"

class API {
    fun createAPIService(): ApiInterface {
        val retrofit = Retrofit.Builder()
            .baseUrl(URL_BASE)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(ApiInterface::class.java)
    }
}
