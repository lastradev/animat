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
import com.example.animat.models.TopAiringAnimeResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val MATCHED_ANIMES_KEY = "matchedAnimes"
const val CURRENT_PAGE_KEY = "currentPage"

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

        fetchAnimes()

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

    private fun fetchAnimes() {
        val currentPage = getCurrentPage()
        val apiCall = API().createAPIService()

        apiCall.getTopAiringAnimes(currentPage).enqueue(object: Callback<TopAiringAnimeResponse> {
            override fun onResponse(call: Call<TopAiringAnimeResponse>, response: Response<TopAiringAnimeResponse>) {
                animes = response.body()?.results!!
                Picasso.get().load(response.body()?.results?.get(animeIndex)?.image).into(ivPoster)
                tvAnimeName.text = response.body()?.results?.get(animeIndex)?.title
            }

            override fun onFailure(call: Call<TopAiringAnimeResponse>, t: Throwable) {
                Toast.makeText(this@AnimesActivity, getString(R.string.failure_message), Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getCurrentPage(): Int {
        val sharedPreferences = getSharedPreferences("PREFERENCES", MODE_PRIVATE)

        return sharedPreferences.getInt(CURRENT_PAGE_KEY, 1)
    }

    private fun showRejectedToast() {
        Toast.makeText(this, getString(R.string.rejected_anime), Toast.LENGTH_SHORT).show()
    }

    private fun showSavedToast() {
        Toast.makeText(this, getString(R.string.saved_to_your_catalog), Toast.LENGTH_SHORT).show()
    }

    private fun saveMatchedAnime() {
        val currentAnime = animes[animeIndex]
        val matchedAnimes = obtainMatchedAnimes()

        if (!matchedAnimes.any { it.id == currentAnime.id }) {
            matchedAnimes.add(currentAnime)
            saveMatchedAnimes(matchedAnimes)
        }
    }

    private fun saveMatchedAnimes(matchedAnimes: ArrayList<Anime>) {
        val sharedPreferences = getSharedPreferences("PREFERENCES", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val matchedAnimesJson = Gson().toJson(matchedAnimes)

        editor.putString(MATCHED_ANIMES_KEY, matchedAnimesJson)
        editor.apply()
    }

    private fun obtainMatchedAnimes(): ArrayList<Anime> {
        val sharedPreferences = getSharedPreferences("PREFERENCES", MODE_PRIVATE)
        val matchedAnimesJson = sharedPreferences.getString(MATCHED_ANIMES_KEY, null)
            ?: return arrayListOf()

        // Gson helps mapping json to class.
        val type = object : TypeToken<ArrayList<Anime>>() {}.type
        return Gson().fromJson(matchedAnimesJson, type)
    }

    private fun showNextAnime() {
        forwardAnimeIndex()
        if(animeIndex != 0){
            Picasso.get().load(animes[animeIndex].image).into(ivPoster)
            tvAnimeName.text = animes[animeIndex].title
        }
    }

    private fun forwardAnimeIndex() {
        animeIndex += 1

        if (animeIndex == animes.size) {
            animeIndex = 0
            showNextPage()
        }
    }


    private fun showNextPage() {
        forwardPage()
        fetchAnimes()
    }

    private fun forwardPage() {
        val sharedPreferences = getSharedPreferences("PREFERENCES", MODE_PRIVATE)
        val nextPage = getCurrentPage() + 1
        val editor = sharedPreferences.edit()

        editor.putInt(CURRENT_PAGE_KEY , nextPage)
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