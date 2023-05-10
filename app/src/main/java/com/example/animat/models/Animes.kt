package com.example.animat.models

data class Animes(
    val currentPage: Int,
    val hasNextPage: Boolean,
    val results: List<Anime>
)
