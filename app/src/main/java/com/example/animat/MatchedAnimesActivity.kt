package com.example.animat

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
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

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val orientation = resources.configuration.orientation
        val sharedPreferences = getSharedPreferences("PREFERENCES", MODE_PRIVATE)
        val gson = Gson()
        val matchedAnimesJson = sharedPreferences.getString(MATCHED_ANIMES_KEY, null)
        if (matchedAnimesJson != null) {
            val type = object : TypeToken<ArrayList<Anime>>() {}.type
            matchedAnimes = gson.fromJson(matchedAnimesJson, type)
        }

        val recycler = findViewById<RecyclerView>(R.id.recyclerAnime)

        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            val gridLayoutManager = GridLayoutManager(this, 2)
            recycler.layoutManager = gridLayoutManager
        } else {
            val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            recycler.layoutManager = linearLayoutManager
        }
        recycler.adapter = MatchedAnimeAdapter(matchedAnimes, this)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_animes, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.option_menu_list) {
            val i = Intent(this,AnimesActivity::class.java)
            startActivity(i)
        }
        return super.onOptionsItemSelected(item)
    }
}