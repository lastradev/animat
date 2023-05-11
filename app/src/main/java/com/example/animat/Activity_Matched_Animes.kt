package com.example.animat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.animat.adapters.MatchedAnimeAdapter
import com.example.animat.api.API
import com.example.animat.models.Anime
import com.example.animat.models.Animes
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Activity_Matched_Animes: AppCompatActivity() {
    private var matchedAnimes: ArrayList<Anime> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_matched_animes)

        val sharedPreferences = getSharedPreferences("PREFERENCES", MODE_PRIVATE)
        val gson = Gson()
        val matchedAnimesJson = sharedPreferences.getString(MATCHED_ANIMES_KEY, null)
        if (matchedAnimesJson != null) {
            val type = object : TypeToken<ArrayList<Anime>>() {}.type
            matchedAnimes = gson.fromJson(matchedAnimesJson, type)
        }

        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val recycler = findViewById<RecyclerView>(R.id.recyclerAnime)

        recycler.layoutManager = linearLayoutManager
        recycler.adapter = MatchedAnimeAdapter(matchedAnimes,this)
    }
}