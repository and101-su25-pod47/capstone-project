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
import com.example.capstoneproject_su25.R
import com.example.capstoneproject_su25.adapters.MediaAdapter
import com.example.capstoneproject_su25.models.MediaItem
import com.google.android.material.chip.Chip
import com.google.android.material.textfield.TextInputEditText

class CollectionsFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var mediaAdapter: MediaAdapter
    private val collectionList = mutableListOf<MediaItem>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_collections, container, false)

        // Set up header
        view.findViewById<TextView>(R.id.tv_screen_title).text = "My Collections"

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

        loadCollections()
        return view
    }

    private fun performSearch(query: String) {
        val filteredList = collectionList.filter {
            it.title.contains(query, ignoreCase = true)
        }
        mediaAdapter.submitList(filteredList)
    }

    private fun setupSortOptions(container: LinearLayout) {
        val sortOptions = listOf("Recently Added", "A-Z", "Rating")

        sortOptions.forEach { option ->
            val chip = Chip(requireContext()).apply {
                text = option
                isCheckable = true
                setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        sortCollections(option)
                    }
                }
            }
            container.addView(chip)
        }
    }

    private fun sortCollections(sortBy: String) {
        val sortedList = when (sortBy) {
            "Recently Added" -> collectionList.sortedByDescending { it.addedDate }
            "A-Z" -> collectionList.sortedBy { it.title }
            "Rating" -> collectionList.sortedByDescending { it.rating }
            else -> collectionList
        }
        mediaAdapter.submitList(sortedList)
    }

    private fun loadCollections() {
        // TODO: Replace with your actual collection loading logic
        // This could come from a database, shared preferences, or API

        // Sample data - replace with your actual collection items
        val sampleCollections = listOf(
            MediaItem(
                title = "Favorite Movies",
                imageUrl = "https://example.com/path/to/image1.jpg",
                addedDate = "2023-10-15",  // You'll need to add this field to MediaItem
                rating = 4.5
            ),
            MediaItem(
                title = "Watch Later",
                imageUrl = "https://example.com/path/to/image2.jpg",
                addedDate = "2023-10-10",
                rating = 4.0
            ),
            // Add more collection items as needed
        )

        collectionList.addAll(sampleCollections)
        mediaAdapter.submitList(collectionList)
    }
}