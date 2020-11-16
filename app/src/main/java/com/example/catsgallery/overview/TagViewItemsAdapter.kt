package com.example.catsgallery.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.catsgallery.R
import com.example.catsgallery.databinding.TagViewItemBinding
import com.example.catsgallery.network.TheCatCategoryResponseItem
import kotlinx.android.synthetic.main.tag_view_item.view.*

class TagViewItemsAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<TheCatCategoryResponseItem, TagViewItemsAdapter.TheCatCategoryResponseViewHolder>(
        DiffCallback
    ) {

    class TheCatCategoryResponseViewHolder(private var binding: TagViewItemBinding) :
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
    ): TheCatCategoryResponseViewHolder {
        return TheCatCategoryResponseViewHolder(
            TagViewItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holderCategoryResponse: TheCatCategoryResponseViewHolder, position: Int) {
        val marsProperty = getItem(position)
        holderCategoryResponse.itemView.findViewById<Button>(R.id.delete_button).setOnClickListener {
            onClickListener.onClick(marsProperty)
        }
        holderCategoryResponse.bind(marsProperty)
    }

    class OnClickListener(val clickListener: (theCatCategoryResponseItem: TheCatCategoryResponseItem) -> Unit) {
        fun onClick(theCatCategoryResponseItem: TheCatCategoryResponseItem) =
            clickListener(theCatCategoryResponseItem)
    }
}
