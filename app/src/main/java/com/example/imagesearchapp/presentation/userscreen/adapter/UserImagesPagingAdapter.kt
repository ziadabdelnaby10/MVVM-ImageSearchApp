package com.example.imagesearchapp.presentation.userscreen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.imagesearchapp.data.model.ImageSearch
import com.example.imagesearchapp.databinding.ItemUserImagesBinding

class UserImagesPagingAdapter(private val onClickListener: (pos: Int, image: ImageSearch) -> Unit) :
    PagingDataAdapter<ImageSearch, UserImagesViewHolder>(SearchDiffCallback()) {
    override fun onBindViewHolder(holder: UserImagesViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item, onClickListener)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserImagesViewHolder {
        return UserImagesViewHolder.from(parent)
    }
}

class SearchDiffCallback : DiffUtil.ItemCallback<ImageSearch>() {
    override fun areItemsTheSame(oldItem: ImageSearch, newItem: ImageSearch): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: ImageSearch, newItem: ImageSearch): Boolean =
        oldItem == newItem
}

class UserImagesViewHolder(val binding: ItemUserImagesBinding) : ViewHolder(binding.root) {
    fun bind(item: ImageSearch, onClickListener: (pos: Int, image: ImageSearch) -> Unit) {
        binding.image = item
        binding.executePendingBindings()
        binding.root.setOnClickListener {
            onClickListener.invoke(bindingAdapterPosition, item)
        }
    }

    companion object {
        fun from(parent: ViewGroup): UserImagesViewHolder {
            return UserImagesViewHolder(
                ItemUserImagesBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

}