package com.example.capstoneproject_su25.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.capstoneproject_su25.MediaCollections
import com.example.capstoneproject_su25.R
import com.example.capstoneproject_su25.adapters.CollectionFolderAdapter


class CollectionsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CollectionFolderAdapter
    private val folderData = listOf(
        "Favorites" to MediaCollections.favorites,
        "Saved for Later" to MediaCollections.watchLater
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_collections, container, false)

        // Set screen title
        view.findViewById<TextView>(R.id.tv_screen_title).text = "My Collections"

        // Set up RecyclerView
        recyclerView = view.findViewById(R.id.recycler_media)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = CollectionFolderAdapter(folderData) { folderName ->
            val fragment = CollectionDetailFragment.newInstance(folderName)
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }

        recyclerView.adapter = adapter

        return view
    }
}
