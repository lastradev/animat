package com.example.animat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
    private var matchedAnimes: ArrayList<Anime> = ArrayList()
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
            Toast.makeText(this, "Guardado a tu catálogo!", Toast.LENGTH_SHORT).show()
            saveMatchedAnime()
            showNextAnime()
        }

        ivReject.setOnClickListener{
            Toast.makeText(this, "Anime rechazado!", Toast.LENGTH_SHORT).show()
            showNextAnime()
        }
    }
    private fun showNextAnime() {
        animeIndex += 1
        Picasso.get().load(animes[animeIndex].image).into(ivPoster)
        tvAnimeName.text = animes[animeIndex].title
    }
    private fun saveMatchedAnime() {
        // Get matched animes saved in sharedPreferences
        val sharedPreferences = getSharedPreferences("PREFERENCES", MODE_PRIVATE)
        val gson = Gson()
        val matchedAnimesJson = sharedPreferences.getString(MATCHED_ANIMES_KEY, null)
        if (matchedAnimesJson != null) {
            val type = object : TypeToken<ArrayList<Anime>>() {}.type
            matchedAnimes = gson.fromJson(matchedAnimesJson, type)
        }
        // Add new matched anime
        matchedAnimes.add(animes[animeIndex])
        // Save matched animes
        val editor = sharedPreferences.edit()
        val matchedAnimesJsonUpdated = gson.toJson(matchedAnimes)
        editor.putString(MATCHED_ANIMES_KEY, matchedAnimesJsonUpdated)
        editor.apply()
    }
}