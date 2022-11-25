package com.example.imagesearchapp.presentation.homescreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.imagesearchapp.data.model.Image
import com.example.imagesearchapp.data.model.ImageSearch
import com.example.imagesearchapp.databinding.ItemImageBinding

//update this to view list of General photos not the searched one
class ImageRecyclerAdapter(private val onClickListener: ImageOnClickListener) :
    ListAdapter<Image, ImageViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Image>() {
        override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder =
        ImageViewHolder.create(parent)

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onClickListener)
    }
}

class ImageViewHolder(private val binding: ItemImageBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Image, onClickListener: ImageOnClickListener) {
        binding.image = item
        binding.executePendingBindings()
        binding.root.setOnClickListener {
            onClickListener.onClick(bindingAdapterPosition, item)
        }
    }

    companion object {
        fun create(parent: ViewGroup): ImageViewHolder {
            val binding = ItemImageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return ImageViewHolder(binding)
        }
    }
}

interface ImageOnClickListener {
    fun onClick(position: Int, item: Image)
}