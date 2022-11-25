package com.example.imagesearchapp.presentation.searchscreen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.imagesearchapp.data.model.ImageSearch
import com.example.imagesearchapp.databinding.ItemSearchImageBinding

class SearchPagingAdapter(
    private val onClickListener: (image: ImageSearch) -> Unit,
    private val onUserImageClicked: (image: ImageSearch) -> Unit
) :
    PagingDataAdapter<ImageSearch, SearchViewHolder>(SearchDiffCallback()) {
    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item, onClickListener , onUserImageClicked)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder.from(parent)
    }

}

class SearchDiffCallback : DiffUtil.ItemCallback<ImageSearch>() {
    override fun areItemsTheSame(oldItem: ImageSearch, newItem: ImageSearch): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: ImageSearch, newItem: ImageSearch): Boolean =
        oldItem == newItem
}

class SearchViewHolder(val binding: ItemSearchImageBinding) : ViewHolder(binding.root) {
    fun bind(
        item: ImageSearch, onClickListener: (image: ImageSearch) -> Unit,
        onUserImageClicked: (image: ImageSearch) -> Unit
    ) {
        binding.searchImage = item
        binding.executePendingBindings()
        binding.ivImage.setOnClickListener {
            onClickListener.invoke(item)
        }
        binding.civUserImage.setOnClickListener {
            onUserImageClicked.invoke(item)
        }
        binding.txtUsername.setOnClickListener {
            onUserImageClicked.invoke(item)
        }
    }

    companion object {
        fun from(parent: ViewGroup): SearchViewHolder {
            return SearchViewHolder(
                ItemSearchImageBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

}