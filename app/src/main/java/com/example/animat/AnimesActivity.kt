package com.example.animat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.animat.api.API
import com.example.animat.models.Anime
import com.example.animat.models.Animes
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val MATCHED_ANIMES_KEY = "matchedAnimes"

class AnimesActivity: AppCompatActivity() {
    private lateinit var ivPoster: ImageView
    private lateinit var ivAccept: ImageView
    private lateinit var ivReject: ImageView
    private lateinit var tvAnimeName: TextView
    private lateinit var animes: List<Anime>
    private var animeIndex: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animes)

        ivPoster = findViewById(R.id.ivPoster)
        ivReject = findViewById(R.id.ivReject)
        ivAccept = findViewById(R.id.ivAccept)
        tvAnimeName = findViewById(R.id.tvAnimeName)
        val apiCall = API().createAPIService()

        apiCall.getTopAiringAnimes().enqueue(object: Callback<Animes> {
            override fun onResponse(call: Call<Animes>, response: Response<Animes>) {
                animes = response.body()?.results!!
                Picasso.get().load(response.body()?.results?.get(animeIndex)?.image).into(ivPoster)
                tvAnimeName.text = response.body()?.results?.get(animeIndex)?.title
            }

            override fun onFailure(call: Call<Animes>, t: Throwable) {
                // TODO: Aquí vamos a cargar un toast de falla
            }
        })

        ivAccept.setOnClickListener{
            saveMatchedAnime()
            showSavedToast()
            showNextAnime()
        }

        ivReject.setOnClickListener{
            showRejectedToast()
            showNextAnime()
        }
    }

    private fun showRejectedToast() {
        Toast.makeText(this, getString(R.string.rejected_anime), Toast.LENGTH_SHORT).show()
    }

    private fun showSavedToast() {
        Toast.makeText(this, getString(R.string.saved_to_your_catalog), Toast.LENGTH_SHORT).show()
    }

    private fun showNextAnime() {
        animeIndex += 1
        Picasso.get().load(animes[animeIndex].image).into(ivPoster)
        tvAnimeName.text = animes[animeIndex].title
    }
    private fun saveMatchedAnime() {
        val currentAnime = animes[animeIndex]
        val matchedAnimes = obtainMatchedAnimes()

        matchedAnimes.add(currentAnime)
        saveMatchedAnimes(matchedAnimes)
    }

    private fun obtainMatchedAnimes(): ArrayList<Anime> {
        val sharedPreferences = getSharedPreferences("PREFERENCES", MODE_PRIVATE)
        val matchedAnimesJson = sharedPreferences.getString(MATCHED_ANIMES_KEY, null)
            ?: return arrayListOf()

        // Gson helps mapping json to class.
        val type = object : TypeToken<ArrayList<Anime>>() {}.type
        return Gson().fromJson(matchedAnimesJson, type)
    }

    private fun saveMatchedAnimes(matchedAnimes: ArrayList<Anime>) {
        val sharedPreferences = getSharedPreferences("PREFERENCES", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val matchedAnimesJson = Gson().toJson(matchedAnimes)

        editor.putString(MATCHED_ANIMES_KEY, matchedAnimesJson)
        editor.apply()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_animes, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.option_menu_list) {
            val i = Intent(this,MatchedAnimesActivity::class.java)
            startActivity(i)
        }
        return super.onOptionsItemSelected(item)
    }
}