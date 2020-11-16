package com.example.catsgallery.detail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.catsgallery.network.TheCatSearchResponseItem

class DetailViewModelFactory(
    private val theCatSearchResponseItem: TheCatSearchResponseItem,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(theCatSearchResponseItem, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
