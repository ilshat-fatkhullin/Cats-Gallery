package com.example.catsgallery.detail

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.catsgallery.network.TheCatSearchResponseItem

class DetailViewModel(theCatSearchResponseItem: TheCatSearchResponseItem, app: Application) : ViewModel() {
    private val _selectedProperty = MutableLiveData<TheCatSearchResponseItem>()

    val selectedProperty: LiveData<TheCatSearchResponseItem>
        get() = _selectedProperty

    init {
        _selectedProperty.value = theCatSearchResponseItem
    }
}
