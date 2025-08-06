package com.example.capstoneproject_su25.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.capstoneproject_su25.MediaCollections
import com.example.capstoneproject_su25.R
import com.example.capstoneproject_su25.models.MediaItem

class MediaAdapter : ListAdapter<MediaItem, MediaAdapter.ViewHolder>(MediaDiffCallback()) {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.img_poster)
        val titleView: TextView = view.findViewById(R.id.tv_title)
        val statusView: TextView = view.findViewById(R.id.tv_status)
        val favoriteIcon: ImageButton = view.findViewById(R.id.btn_favorite)
        val bookmarkIcon: ImageButton = view.findViewById(R.id.iv_bookmark)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_media, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        Glide.with(holder.itemView)
            .load(item.imageUrl)
            .placeholder(R.drawable.ic_image_placeholder)
            .into(holder.imageView)

        holder.titleView.text = item.title
        holder.statusView.text = item.releaseDate ?: ""

        holder.favoriteIcon.setImageResource(
            if (item.isFavorite) R.drawable.ic_heart_filled else R.drawable.ic_heart
        )
        holder.bookmarkIcon.setImageResource(
            if (item.isSaved) R.drawable.ic_bookmark_filled else R.drawable.ic_bookmark
        )

        // Toggle Favorite
        holder.favoriteIcon.setOnClickListener {
            item.isFavorite = !item.isFavorite
            if (item.isFavorite) {
                MediaCollections.addToFavorites(item)
                Toast.makeText(holder.itemView.context, "\"${item.title}\" added to Favorites", Toast.LENGTH_SHORT).show()
            } else {
                MediaCollections.removeFromFavorites(item)
                Toast.makeText(holder.itemView.context, "\"${item.title}\" removed from Favorites", Toast.LENGTH_SHORT).show()
            }
            notifyItemChanged(position)
        }

        // Toggle Bookmark
        holder.bookmarkIcon.setOnClickListener {
            item.isSaved = !item.isSaved
            if (item.isSaved) {
                MediaCollections.addToWatchLater(item)
                Toast.makeText(holder.itemView.context, "\"${item.title}\" saved for later", Toast.LENGTH_SHORT).show()
            } else {
                MediaCollections.removeFromWatchLater(item)
                Toast.makeText(holder.itemView.context, "\"${item.title}\" removed from Watch Later", Toast.LENGTH_SHORT).show()
            }
            notifyItemChanged(position)
        }
    }
}

class MediaDiffCallback : DiffUtil.ItemCallback<MediaItem>() {
    override fun areItemsTheSame(oldItem: MediaItem, newItem: MediaItem): Boolean {
        return oldItem.title == newItem.title && oldItem.imageUrl == newItem.imageUrl
    }

    override fun areContentsTheSame(oldItem: MediaItem, newItem: MediaItem): Boolean {
        return oldItem == newItem
    }
}
