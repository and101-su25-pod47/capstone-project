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

class BooksFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var mediaAdapter: MediaAdapter
    private val bookList = mutableListOf<MediaItem>()
    private val totalPages = 3  // Fewer pages for books since API might have less data
    private var pagesFetched = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_books, container, false)

        // Set up header
        view.findViewById<TextView>(R.id.tv_screen_title).text = "Books"

        // Set up search
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

        fetchBooks()
        return view
    }

    private fun performSearch(query: String) {
        val filteredList = bookList.filter {
            it.title.contains(query, ignoreCase = true)
        }
        mediaAdapter.submitList(filteredList)
    }

    private fun setupSortOptions(container: LinearLayout) {
        val sortOptions = listOf("Popular", "New Releases", "A-Z", "Author")

        sortOptions.forEach { option ->
            val chip = Chip(requireContext()).apply {
                text = option
                isCheckable = true
                setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        sortBooks(option)
                    }
                }
            }
            container.addView(chip)
        }
    }

    private fun sortBooks(sortBy: String) {
        val sortedList = when (sortBy) {
            "Popular" -> bookList.sortedByDescending { it.popularity }
            "New Releases" -> bookList.sortedByDescending { it.releaseDate }
            "A-Z" -> bookList.sortedBy { it.title }
            "Author" -> bookList.sortedBy { it.author }  // You'll need to add author field to MediaItem
            else -> bookList
        }
        mediaAdapter.submitList(sortedList)
    }

    private fun fetchBooks() {
        val client = AsyncHttpClient()
        val baseUrl = "https://www.googleapis.com/books/v1/volumes"  // Google Books API
        val apiKey = "YOUR_API_KEY"  // Replace with your actual API key

        for (page in 0 until totalPages) {  // Google Books uses startIndex instead of page
            val params = RequestParams()
            params.put("q", "popular")  // Search query - adjust as needed
            params.put("maxResults", "20")  // Items per page
            params.put("startIndex", (page * 20).toString())
            params.put("key", apiKey)

            client.get(baseUrl, params, object : JsonHttpResponseHandler() {
                override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON) {
                    try {
                        val itemsArray = json.jsonObject.getJSONArray("items")
                        for (i in 0 until itemsArray.length()) {
                            val bookObj = itemsArray.getJSONObject(i)
                            val volumeInfo = bookObj.getJSONObject("volumeInfo")
                            val imageLinks = volumeInfo.optJSONObject("imageLinks")

                            bookList.add(
                                MediaItem(
                                    title = volumeInfo.getString("title"),
                                    imageUrl = imageLinks?.optString("thumbnail", "") ?: "",
                                    author = volumeInfo.optJSONArray("authors")?.optString(0) ?: "Unknown Author",
                                    releaseDate = volumeInfo.optString("publishedDate", ""),
                                    popularity = bookObj.optDouble("averageRating", 0.0),
                                    rating = bookObj.optDouble("averageRating", 0.0)
                                )
                            )
                        }

                        pagesFetched++
                        if (pagesFetched == totalPages) {
                            mediaAdapter.submitList(bookList)
                        }
                    } catch (e: Exception) {
                        Log.e("Book Error", "JSON parsing error: ${e.message}")
                    }
                }

                override fun onFailure(
                    statusCode: Int,
                    headers: Headers?,
                    response: String?,
                    throwable: Throwable?
                ) {
                    Log.e("Book Error", "Failed page $page: $response")
                }
            })
        }
    }
}