package com.example.catsgallery.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.catsgallery.databinding.GridViewItemBinding
import com.example.catsgallery.network.TheCatSearchResponseItem

class PhotoGridAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<TheCatSearchResponseItem, PhotoGridAdapter.TheCatSearchResponseViewHolder>(
        DiffCallback
    ) {

    class TheCatSearchResponseViewHolder(private var binding: GridViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(theCatSearchResponseItem: TheCatSearchResponseItem) {
            binding.property = theCatSearchResponseItem
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<TheCatSearchResponseItem>() {
        override fun areItemsTheSame(
            oldItem: TheCatSearchResponseItem,
            newItem: TheCatSearchResponseItem
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: TheCatSearchResponseItem,
            newItem: TheCatSearchResponseItem
        ): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TheCatSearchResponseViewHolder {
        return TheCatSearchResponseViewHolder(
            GridViewItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holderSearchResponse: TheCatSearchResponseViewHolder, position: Int) {
        val marsProperty = getItem(position)
        holderSearchResponse.itemView.setOnClickListener {
            onClickListener.onClick(marsProperty)
        }
        holderSearchResponse.bind(marsProperty)
    }

    class OnClickListener(val clickListener: (theCatSearchResponseItem: TheCatSearchResponseItem) -> Unit) {
        fun onClick(theCatSearchResponseItem: TheCatSearchResponseItem) =
            clickListener(theCatSearchResponseItem)
    }
}
