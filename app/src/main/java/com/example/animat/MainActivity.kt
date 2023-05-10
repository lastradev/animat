package com.example.animat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

const val USER_NAME_KEY = "name"
const val USER_AGE_KEY = "age"

class MainActivity : AppCompatActivity() {
    private lateinit var tvWelcome: TextView
    private lateinit var etUserName: EditText
    private lateinit var etUserAge: EditText
    private var name: String = ""
    private var age: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("PREFERENCIAS", "onCreate")
        val bnContinue = findViewById<Button>(R.id.bnContinue)
        tvWelcome = findViewById(R.id.tvWelcome)
        etUserName = findViewById(R.id.etUserName)
        etUserAge = findViewById(R.id.etUserAge)

        bnContinue.setOnClickListener {
            name = etUserName.text.toString()
            age = etUserAge.text.toString().toInt()
            val miSharedPreferences = getSharedPreferences("PERSISTENCIA", MODE_PRIVATE)
            val editor = miSharedPreferences.edit()

            editor.putString(USER_NAME_KEY, name)
            editor.putInt(USER_AGE_KEY, age)
            editor.apply()

            val i = Intent(this, AnimesActivity::class.java)
            startActivity(i)
        }
    }
    override fun onResume() {
        Log.d("PREFERENCIAS", "onResume")

        if(TextUtils.isEmpty(name)){
            val miSharedPreferences = getSharedPreferences("PERSISTENCIA", MODE_PRIVATE)
            name = miSharedPreferences.getString(USER_NAME_KEY, "").toString()
            age = miSharedPreferences.getInt(USER_AGE_KEY, 0)
        }

        if (name != ""){
            tvWelcome.text = "Bienvenido $name"
            etUserName.setText(name)
            etUserAge.setText(age.toString())
        }

        super.onResume()
    }
    override fun onStart() {
        Log.d("PREFERENCIAS", "onStart")
        super.onStart()
    }
    override fun onPause() {
        Log.d("PREFERENCIAS", "onPause")
        super.onPause()
    }
    override fun onDestroy() {
        Log.d("PREFERENCIAS", "onDestroy")
        super.onDestroy()
    }
}