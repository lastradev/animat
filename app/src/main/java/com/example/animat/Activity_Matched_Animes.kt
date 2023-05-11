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
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Activity_Matched_Animes: AppCompatActivity() {
    private lateinit var matchedAnimes : ArrayList<Anime>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_matched_animes)


        matchedAnimes = arrayListOf<Anime>()
        // Dummy Array para probar, borrar despues
        matchedAnimes.add(Anime("dlsmd","Kimetsu no yaiba","https://http2.mlstatic.com/D_NQ_NP_690431-MLM48877492720_012022-O.jpg"
        , genres = arrayListOf("Shonen","Shoyo")))
        matchedAnimes.add(Anime("sads","Haikyuu","https://http2.mlstatic.com/D_NQ_NP_690431-MLM48877492720_012022-O.jpg"
        , genres = arrayListOf("Shonen","Shoyo")))
        matchedAnimes.add(Anime("Dadad","Jujutsu Kaisen","https://http2.mlstatic.com/D_NQ_NP_690431-MLM48877492720_012022-O.jpg"
        , genres = arrayListOf("Shonen","Shoyo")))
        matchedAnimes.add(Anime("adad","Horimiya","https://http2.mlstatic.com/D_NQ_NP_690431-MLM48877492720_012022-O.jpg"
        , genres = arrayListOf("Shonen","Shoyo")))

        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val recycler = findViewById<RecyclerView>(R.id.recyclerAnime)

        recycler.layoutManager = linearLayoutManager
        recycler.adapter = MatchedAnimeAdapter(matchedAnimes,this)
    }
}