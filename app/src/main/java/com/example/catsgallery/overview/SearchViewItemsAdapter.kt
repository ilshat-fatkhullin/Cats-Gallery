package com.example.catsgallery.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.catsgallery.R
import com.example.catsgallery.databinding.SearchViewItemBinding
import com.example.catsgallery.network.TheCatCategoryResponseItem

class SearchViewItemsAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<TheCatCategoryResponseItem, SearchViewItemsAdapter.TheCatCategoryViewHolder>(
        DiffCallback
    ) {

    class TheCatCategoryViewHolder(private var binding: SearchViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(theCatCategoryResponseItem: TheCatCategoryResponseItem) {
            binding.property = theCatCategoryResponseItem
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<TheCatCategoryResponseItem>() {
        override fun areItemsTheSame(
            oldItem: TheCatCategoryResponseItem,
            newItem: TheCatCategoryResponseItem
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: TheCatCategoryResponseItem,
            newItem: TheCatCategoryResponseItem
        ): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TheCatCategoryViewHolder {
        return TheCatCategoryViewHolder(
            SearchViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holderCategory: TheCatCategoryViewHolder, position: Int) {
        val marsProperty = getItem(position)
        holderCategory.itemView.setOnClickListener {
            onClickListener.onClick(marsProperty)
        }
        holderCategory.bind(marsProperty)
    }

    class OnClickListener(val clickListener: (theCatCategoryResponseItem: TheCatCategoryResponseItem) -> Unit) {
        fun onClick(theCatCategoryResponseItem: TheCatCategoryResponseItem) =
            clickListener(theCatCategoryResponseItem)
    }
}
