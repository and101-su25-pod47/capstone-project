package com.example.capstoneproject_su25.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.capstoneproject_su25.R
import com.example.capstoneproject_su25.models.MediaItem

class CollectionFolderAdapter(
    private val folders: List<Pair<String, List<MediaItem>>>, // Name + previews
    private val onClick: (String) -> Unit
) : RecyclerView.Adapter<CollectionFolderAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val folderName: TextView = itemView.findViewById(R.id.tv_folder_name)
        val card: CardView = itemView.findViewById(R.id.card_folder)
        val thumbnailContainer: LinearLayout = itemView.findViewById(R.id.thumbnail_container)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_collection_folder, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (folderName, mediaList) = folders[position]
        holder.folderName.text = folderName

        holder.thumbnailContainer.removeAllViews()

        val previewItems = mediaList.take(3) // Max 3 thumbnails
        previewItems.forEach { item ->
            val imageView = ImageView(holder.itemView.context).apply {
                layoutParams = LinearLayout.LayoutParams(100, 150).apply {
                    rightMargin = 8
                }
                scaleType = ImageView.ScaleType.CENTER_CROP
            }

            Glide.with(holder.itemView)
                .load(item.imageUrl)
                .placeholder(R.drawable.ic_image_placeholder)
                .into(imageView)

            holder.thumbnailContainer.addView(imageView)
        }

        holder.card.setOnClickListener {
            onClick(folderName)
        }
    }

    override fun getItemCount(): Int = folders.size
}
