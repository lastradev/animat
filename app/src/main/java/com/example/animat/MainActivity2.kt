package com.example.animat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.animat.api.API
import com.example.animat.models.Animes
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val IMAGE_URL = "https://m.media-amazon.com/images/I/71TfuzTNFwL._AC_UF894,1000_QL80_.jpg"
class MainActivity2 : AppCompatActivity() {
    private lateinit var ivPoster: ImageView
    private lateinit var ivAccept: ImageView
    private lateinit var ivReject: ImageView
    private lateinit var tvAnimeName: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        ivPoster = findViewById(R.id.ivPoster)
        ivReject = findViewById(R.id.ivReject)
        ivAccept = findViewById(R.id.ivAccept)
        tvAnimeName = findViewById(R.id.tvAnimeName)

        Picasso.get().load(IMAGE_URL).into(ivPoster);

        ivAccept.setOnClickListener{
            // TODO: Aquí vamos a guardar el anime en nuestro catálogo
           Toast.makeText(this, "Guardado a tu catálogo!", Toast.LENGTH_SHORT).show()
        }

        ivReject.setOnClickListener{
            // TODO: Aquí vamos a solicitar otro anime de gogoanime
            Toast.makeText(this, "Anime rechazado!", Toast.LENGTH_SHORT).show()
        }

        val apiCall = API().createAPIService()

        apiCall.getTopAiringAnimes().enqueue(object: Callback<Animes> {
            override fun onResponse(call: Call<Animes>, response: Response<Animes>) {
                // Aquí vamos a cargar más información del anime.
                Picasso.get().load(response.body()?.animeList?.get(0)?.animeImg.toString()).into(ivPoster)
                tvAnimeName.text = response.body()?.animeList?.get(0)?.animeTitle.toString()
            }

            override fun onFailure(call: Call<Animes>, t: Throwable) {
                // TODO: Aquí vamos a cargar una pantalla de falla.
            }
        })
    }
}