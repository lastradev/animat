package com.example.animat.api

import com.example.animat.models.Animes
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET("top-airing")
    fun getTopAiringAnimes(): Call<Animes>
}