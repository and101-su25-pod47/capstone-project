package com.example.capstoneproject_su25

import com.example.capstoneproject_su25.models.MediaItem

object MediaCollections {
    val favorites = mutableListOf<MediaItem>()
    val watchLater = mutableListOf<MediaItem>()

    fun addToFavorites(item: MediaItem) {
        if (!favorites.any { it.title == item.title }) {
            favorites.add(item.copy())
        }
    }

    fun removeFromFavorites(item: MediaItem) {
        favorites.removeAll { it.title == item.title }
    }

    fun addToWatchLater(item: MediaItem) {
        if (!watchLater.any { it.title == item.title }) {
            watchLater.add(item.copy())
        }
    }

    fun removeFromWatchLater(item: MediaItem) {
        watchLater.removeAll { it.title == item.title }
    }
}
