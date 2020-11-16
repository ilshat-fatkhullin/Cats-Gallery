package com.example.catsgallery

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.catsgallery.network.TheCatCategoryResponseItem
import com.example.catsgallery.network.TheCatSearchResponseItem
import com.example.catsgallery.overview.PhotoGridAdapter
import com.example.catsgallery.overview.SearchViewItemsAdapter
import com.example.catsgallery.overview.TheCatsApiStatus

@BindingAdapter("searchListData")
fun bindSearchRecyclerView(recyclerView: RecyclerView, data: List<TheCatSearchResponseItem>?) {
    val adapter = recyclerView.adapter as PhotoGridAdapter
    adapter.submitList(data)
}

@BindingAdapter("categoryListData")
fun bindCategoryRecyclerView(recyclerView: RecyclerView, data: List<TheCatCategoryResponseItem>?) {
    val adapter = recyclerView.adapter as SearchViewItemsAdapter
    adapter.submitList(data)
}

/**
 * Uses the Glide library to load an image by URL into an [ImageView]
 */
@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image))
            .into(imgView)
    }
}

@BindingAdapter("marsApiStatus")
fun bindStatus(statusImageView: ImageView, status: TheCatsApiStatus?) {
    when (status) {
        TheCatsApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        TheCatsApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        TheCatsApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}
