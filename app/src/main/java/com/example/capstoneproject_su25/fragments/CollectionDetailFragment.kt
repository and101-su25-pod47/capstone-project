package com.example.capstoneproject_su25.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.capstoneproject_su25.MediaCollections
import com.example.capstoneproject_su25.R
import com.example.capstoneproject_su25.adapters.MediaAdapter

class CollectionDetailFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MediaAdapter
    private var collectionType: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        collectionType = arguments?.getString("collectionType")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_collection_detail, container, false)

        recyclerView = view.findViewById(R.id.recycler_media)
        adapter = MediaAdapter()
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.adapter = adapter

        val listToShow = when (collectionType) {
            "Favorites" -> MediaCollections.favorites
            "Watch Later" -> MediaCollections.watchLater
            else -> emptyList()
        }

        adapter.submitList(listToShow)
        return view
    }

    companion object {
        fun newInstance(type: String): CollectionDetailFragment {
            val fragment = CollectionDetailFragment()
            val bundle = Bundle()
            bundle.putString("collectionType", type)
            fragment.arguments = bundle
            return fragment
        }
    }
}
