package com.example.animat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.animat.adapters.MatchedAnimeAdapter
import com.example.animat.models.Anime
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MatchedAnimesActivity: AppCompatActivity() {
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