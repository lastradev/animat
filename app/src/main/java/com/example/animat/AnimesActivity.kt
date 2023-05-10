package com.example.animat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.animat.api.API
import com.example.animat.models.Anime
import com.example.animat.models.Animes
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
            // TODO: Aquí vamos a guardar el anime en nuestro catálogo
           Toast.makeText(this, "Guardado a tu catálogo!", Toast.LENGTH_SHORT).show()
        }

        ivReject.setOnClickListener{
            // TODO: Aquí vamos a solicitar otro anime de gogoanime
            animeIndex+=1
            Toast.makeText(this, "Anime rechazado!", Toast.LENGTH_SHORT).show()
            Picasso.get().load(animes[animeIndex].image).into(ivPoster)
            tvAnimeName.text = animes[animeIndex].title
        }
    }
}