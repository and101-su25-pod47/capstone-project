package com.example.capstoneproject_su25.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.example.capstoneproject_su25.R
import com.example.capstoneproject_su25.adapters.MediaAdapter
import com.example.capstoneproject_su25.models.MediaItem
import com.google.android.material.chip.Chip
import com.google.android.material.textfield.TextInputEditText
import okhttp3.Headers
import org.json.JSONObject

class MoviesFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var mediaAdapter: MediaAdapter
    private val movieList = mutableListOf<MediaItem>()
    private val totalPages = 5
    private var pagesFetched = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movies, container, false)

        // Set up header
        view.findViewById<TextView>(R.id.tv_screen_title).text = "Movies"

        // Set up search - using the new ID 'search_input'
        val searchEditText = view.findViewById<TextInputEditText>(R.id.search_input)
        searchEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch(searchEditText.text.toString())
                true
            } else {
                false
            }
        }

        // Set up sort chips
        setupSortOptions(view.findViewById(R.id.sort_options_container))

        // Set up RecyclerView
        recyclerView = view.findViewById(R.id.recycler_media)
        mediaAdapter = MediaAdapter()
        recyclerView.adapter = mediaAdapter
        recyclerView.layoutManager = GridLayoutManager(context, 2)

        fetchMovies()
        return view
    }

    private fun performSearch(query: String) {
        val filteredList = movieList.filter {
            it.title.contains(query, ignoreCase = true)
        }
        mediaAdapter.submitList(filteredList)
    }

    private fun setupSortOptions(container: LinearLayout) {
        val sortOptions = listOf("Popular", "Latest", "A-Z", "Rating")

        sortOptions.forEach { option ->
            val chip = Chip(requireContext()).apply {
                text = option
                isCheckable = true
                setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        sortMovies(option)
                    }
                }
            }
            container.addView(chip)
        }
    }

    private fun sortMovies(sortBy: String) {
        val sortedList = when (sortBy) {
            "Popular" -> movieList.sortedByDescending { it.popularity }
            "Latest" -> movieList.sortedByDescending { it.releaseDate }
            "A-Z" -> movieList.sortedBy { it.title }
            "Rating" -> movieList.sortedByDescending { it.rating }
            else -> movieList
        }
        mediaAdapter.submitList(sortedList)
    }

    private fun fetchMovies() {
        val client = AsyncHttpClient()
        val baseUrl = "https://api.themoviedb.org/3/movie/popular"
        val apiKey = "eb6dd43ffb9137832e34d129da57f67a" // Replace with your actual API key

        for (page in 1..totalPages) {
            val params = RequestParams()
            params.put("api_key", apiKey)
            params.put("page", page)

            client.get(baseUrl, params, object : JsonHttpResponseHandler() {
                override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON) {
                    try {
                        val resultsArray = json.jsonObject.getJSONArray("results")
                        for (i in 0 until resultsArray.length()) {
                            val movieObj = resultsArray.getJSONObject(i)
                            movieList.add(
                                MediaItem(
                                    title = movieObj.getString("title"),
                                    imageUrl = "https://image.tmdb.org/t/p/w500${movieObj.getString("poster_path")}",
                                    genre = movieObj.optString("genre_ids", ""),
                                    status = movieObj.optString("release_date", ""),
                                    popularity = movieObj.optDouble("popularity", 0.0),
                                    releaseDate = movieObj.optString("release_date", ""),
                                    rating = movieObj.optDouble("vote_average", 0.0)
                                )
                            )
                        }

                        pagesFetched++
                        if (pagesFetched == totalPages) {
                            mediaAdapter.submitList(movieList)
                        }
                    } catch (e: Exception) {
                        Log.e("Movie Error", "JSON parsing error: ${e.message}")
                    }
                }

                override fun onFailure(
                    statusCode: Int,
                    headers: Headers?,
                    response: String?,
                    throwable: Throwable?
                ) {
                    Log.e("Movie Error", "Failed page $page: $response")
                }
            })
        }
    }
}