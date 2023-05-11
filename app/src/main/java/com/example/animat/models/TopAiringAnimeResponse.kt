package com.example.animat.models

data class TopAiringAnimeResponse(
    val currentPage: Int,
    val hasNextPage: Boolean,
    val results: List<Anime>
)
