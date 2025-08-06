package com.example.capstoneproject_su25.models

data class MediaItem(
    val title: String,
    val imageUrl: String,
    val genre: String? = null,
    val status: String? = null,
    val popularity: Double = 0.0,
    val addedDate: String? = null,
    val author: String? = null,
    val releaseDate: String? = null,
    val rating: Double = 0.0
)