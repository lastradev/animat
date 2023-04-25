package com.example.animat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.squareup.picasso.Picasso

class MainActivity2 : AppCompatActivity() {
    private lateinit var ivPoster: ImageView
    private lateinit var ivAccept: ImageView
    private lateinit var ivReject: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        ivPoster = findViewById(R.id.ivPoster)
        ivAccept = findViewById(R.id.ivAccept)
        ivReject = findViewById(R.id.ivReject)

        Picasso.get().load("https://m.media-amazon.com/images/I/71TfuzTNFwL._AC_UF894,1000_QL80_.jpg").into(ivPoster);

    }
}