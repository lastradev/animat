package com.example.animat.api

import com.example.animat.models.TopAiringAnimeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("top-airing")
    fun getTopAiringAnimes(@Query("page") page: Int): Call<TopAiringAnimeResponse>
}