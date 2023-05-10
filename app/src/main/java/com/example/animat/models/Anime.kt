package com.example.animat.models

data class Anime(
    val id: String,
    val title: String,
    val image: String,
    val url: String,
    val genres: ArrayList<String>
)
