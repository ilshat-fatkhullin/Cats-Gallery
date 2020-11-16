package com.example.catsgallery.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catsgallery.network.MarsApi
import com.example.catsgallery.network.TheCatApiFilter
import com.example.catsgallery.network.TheCatSearchResponseItem
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

enum class TheCatsApiStatus { LOADING, ERROR, DONE }

class OverviewViewModel : ViewModel() {
    private val _status = MutableLiveData<TheCatsApiStatus>()

    val status: LiveData<TheCatsApiStatus>
        get() = _status

    private val _properties = MutableLiveData<List<TheCatSearchResponseItem>>()

    val properties: LiveData<List<TheCatSearchResponseItem>>
        get() = _properties

    private val _navigateToSelectedProperty = MutableLiveData<TheCatSearchResponseItem>()

    val navigateToSelectedProperty: LiveData<TheCatSearchResponseItem>
        get() = _navigateToSelectedProperty

    private val _apiKey = MutableLiveData<String>()

    private val _page = MutableLiveData<Int>()

    private val _limit = MutableLiveData<Int>()

    private val _filter = MutableLiveData<TheCatApiFilter>()

    private val _imageLoadingJob = MutableLiveData<Job>()

    init {
        _apiKey.value = "bcfcd03c-8c1b-4a80-ab12-9e610d1b1720"
        _limit.value = 25
        updateFilter(TheCatApiFilter.BENG)
    }

    private fun loadMorePhotos() {
        if (_status.value == TheCatsApiStatus.LOADING)
            return

        _imageLoadingJob.value = viewModelScope.launch {
            _status.value = TheCatsApiStatus.LOADING
            try {
                val result = ArrayList<TheCatSearchResponseItem>(_properties.value!!)
                result.addAll(MarsApi.retrofitService.getSearchItems(_apiKey.value.orEmpty(), _page.value!!, _limit.value!!, _filter.value!!.value))
                _properties.value = result
                _status.value = TheCatsApiStatus.DONE
                _page.value = _page.value!! + 1
            } catch (e: Exception) {
                _status.value = TheCatsApiStatus.ERROR
            }
        }
    }

    fun displayPropertyDetails(theCatSearchResponseItem: TheCatSearchResponseItem) {
        _navigateToSelectedProperty.value = theCatSearchResponseItem
    }

    fun displayPropertyDetailsComplete() {
        _navigateToSelectedProperty.value = null
    }

    fun updateFilter(filter: TheCatApiFilter) {
        _page.value = 0
        _properties.value = ArrayList()
        _filter.value = filter
        _status.value = TheCatsApiStatus.DONE
        if (_imageLoadingJob.value != null) {
            _imageLoadingJob.value!!.cancel()
        }
        loadMorePhotos()
    }

    fun onScrolledUntilEnd() {
        loadMorePhotos()
    }
}