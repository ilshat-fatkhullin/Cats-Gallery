package com.example.catsgallery.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catsgallery.network.TheCatApi
import com.example.catsgallery.network.TheCatCategoryResponseItem
import com.example.catsgallery.network.TheCatSearchResponseItem
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.StringBuilder

enum class TheCatsApiStatus { LOADING, ERROR, DONE }

class OverviewViewModel : ViewModel() {
    private val _isSearchViewInFocus = MutableLiveData<Boolean>()

    val isSearchViewInFocus: LiveData<Boolean>
        get() = _isSearchViewInFocus

    private val _searchItemsLoadingStatus = MutableLiveData<TheCatsApiStatus>()

    val searchItemsLoadingStatus: LiveData<TheCatsApiStatus>
        get() = _searchItemsLoadingStatus

    private val _categoriesLoadingStatus = MutableLiveData<TheCatsApiStatus>()

    private val _searchItems = MutableLiveData<List<TheCatSearchResponseItem>>()

    val searchItems: LiveData<List<TheCatSearchResponseItem>>
        get() = _searchItems

    private val _navigateToSelectedProperty = MutableLiveData<TheCatSearchResponseItem>()

    val navigateToSelectedProperty: LiveData<TheCatSearchResponseItem>
        get() = _navigateToSelectedProperty

    private val _searchText = MutableLiveData<String>()

    val searchText: LiveData<String>
        get() = _searchText

    private val _apiKey = MutableLiveData<String>()

    private val _page = MutableLiveData<Int>()

    private val _limit = MutableLiveData<Int>()

    private val _imageLoadingJob = MutableLiveData<Job>()

    private val _categoryItems = MutableLiveData<List<TheCatCategoryResponseItem>>()

    private val _filteredCategoryItems = MutableLiveData<List<TheCatCategoryResponseItem>>()

    val filteredCategoryItems: LiveData<List<TheCatCategoryResponseItem>>
        get() = _filteredCategoryItems

    private val _selectedCategories = MutableLiveData<List<TheCatCategoryResponseItem>>()

    val selectedCategories: LiveData<List<TheCatCategoryResponseItem>>
        get() = _selectedCategories

    init {
        _apiKey.value = "bcfcd03c-8c1b-4a80-ab12-9e610d1b1720"
        _limit.value = 25
        _selectedCategories.value = ArrayList()
        resetSearchItems()
        loadCategories()
    }

    private fun resetSearchItems() {
        _page.value = 0
        _searchItems.value = ArrayList()
        _searchItemsLoadingStatus.value = TheCatsApiStatus.DONE
        if (_imageLoadingJob.value != null) {
            _imageLoadingJob.value!!.cancel()
        }
        loadMorePhotos()
    }

    private fun loadMorePhotos() {
        if (_searchItemsLoadingStatus.value == TheCatsApiStatus.LOADING)
            return

        _imageLoadingJob.value = viewModelScope.launch {
            _searchItemsLoadingStatus.value = TheCatsApiStatus.LOADING
            try {
                val result = ArrayList<TheCatSearchResponseItem>(_searchItems.value!!)
                result.addAll(
                    TheCatApi.retrofitService.getSearchItems(
                        _apiKey.value.orEmpty(),
                        _page.value!!,
                        _limit.value!!,
                        getSelectedCategoryIds()
                    )
                )
                _searchItems.value = result
                _searchItemsLoadingStatus.value = TheCatsApiStatus.DONE
                _page.value = _page.value!! + 1
            } catch (e: Exception) {
                _searchItemsLoadingStatus.value = TheCatsApiStatus.ERROR
            }
        }
    }

    private fun loadCategories() {
        if (_categoriesLoadingStatus.value == TheCatsApiStatus.LOADING)
            return

        viewModelScope.launch {
            try {
                _categoryItems.value = TheCatApi.retrofitService.getCategories()
                _categoriesLoadingStatus.value = TheCatsApiStatus.DONE
                updateFilteredCategories()
            } catch (e: Exception) {
                _categoryItems.value = null
                _categoriesLoadingStatus.value = TheCatsApiStatus.ERROR
            }
        }
    }

    private fun updateFilteredCategories() {
        if (_categoryItems.value == null) {
            loadCategories()
            _filteredCategoryItems.value = ArrayList()
            return
        }

        val filteredList = ArrayList<TheCatCategoryResponseItem>()
        for (c in _categoryItems.value!!) {
            if (!c.name.startsWith(_searchText.value.orEmpty(), true))
                continue
            filteredList.add(c)
        }

        _filteredCategoryItems.value = filteredList
    }

    private fun getSelectedCategoryIds(): String {
        val result = StringBuilder()
        if (_selectedCategories.value == null) {
            return ""
        }
        for (i in 0 until _selectedCategories.value!!.count()) {
            result.append(_selectedCategories.value!![i].id)
            if (i < _selectedCategories.value!!.count() - 1) {
                result.append(',')
            }
        }
        return result.toString()
    }

    fun displayPropertyDetails(theCatSearchResponseItem: TheCatSearchResponseItem) {
        _navigateToSelectedProperty.value = theCatSearchResponseItem
    }

    fun displayPropertyDetailsComplete() {
        _navigateToSelectedProperty.value = null
    }

    fun onScrolledUntilEnd() {
        loadMorePhotos()
    }

    fun setSearchText(text: String?) {
        _searchText.value = text
        updateFilteredCategories()
    }

    fun setSearchViewFocus(value: Boolean) {
        _isSearchViewInFocus.value = value
    }

    fun addSelectedCategory(value: TheCatCategoryResponseItem) {
        val result = ArrayList(_selectedCategories.value!!)
        result.add(value)
        _selectedCategories.value = result
        resetSearchItems()
    }

    fun deleteSelectedCategory(value: TheCatCategoryResponseItem) {
        val result = ArrayList<TheCatCategoryResponseItem>()
        for (c in _selectedCategories.value!!)
        {
            if (c.name == value.name) {
                continue
            }
            result.add(c)
        }
        _selectedCategories.value = result
        resetSearchItems()
    }
}