package com.example.animat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.animat.models.Anime
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

const val USER_NAME_KEY = "name"
const val USER_AGE_KEY = "age"
class MainActivity : AppCompatActivity() {
    private lateinit var tvWelcome: TextView
    private lateinit var etUserName: EditText
    private lateinit var etUserAge: EditText
    private var name: String = ""
    private var age: Int = 0
    private var matchedAnimes: ArrayList<Anime> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("PREFERENCES", "onCreate")
        val bnContinue = findViewById<Button>(R.id.bnContinue)
        tvWelcome = findViewById(R.id.tvWelcome)
        etUserName = findViewById(R.id.etUserName)
        etUserAge = findViewById(R.id.etUserAge)

        bnContinue.setOnClickListener {
            name = etUserName.text.toString()
            age = etUserAge.text.toString().toInt()
            val sharedPreferences = getSharedPreferences("PREFERENCES", MODE_PRIVATE)
            val editor = sharedPreferences.edit()

            editor.putString(USER_NAME_KEY, name)
            editor.putInt(USER_AGE_KEY, age)
            editor.apply()

            val i = Intent(this, AnimesActivity::class.java)
            startActivity(i)
        }
    }
    override fun onResume() {
        Log.d("PREFERENCES", "onResume")

        if(TextUtils.isEmpty(name)){
            val sharedPreferences = getSharedPreferences("PREFERENCES", MODE_PRIVATE)
            name = sharedPreferences.getString(USER_NAME_KEY, "").toString()
            age = sharedPreferences.getInt(USER_AGE_KEY, 0)
            val gson = Gson()
            val matchedAnimesJson = sharedPreferences.getString(MATCHED_ANIMES_KEY, null)
            if (matchedAnimesJson != null) {
                val type = object : TypeToken<ArrayList<Anime>>() {}.type
                matchedAnimes = gson.fromJson(matchedAnimesJson, type)
            }
            Log.d("LOGIN", matchedAnimes.toString())
        }

        if (name != ""){
            tvWelcome.text = "Bienvenido $name"
            etUserName.setText(name)
            etUserAge.setText(age.toString())
        }

        super.onResume()
    }
    override fun onStart() {
        Log.d("PREFERENCES", "onStart")
        super.onStart()
    }
    override fun onPause() {
        Log.d("PREFERENCES", "onPause")
        super.onPause()
    }
    override fun onDestroy() {
        Log.d("PREFERENCES", "onDestroy")
        super.onDestroy()
    }
}