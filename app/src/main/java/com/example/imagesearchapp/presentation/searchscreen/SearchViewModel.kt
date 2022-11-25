package com.example.imagesearchapp.presentation.searchscreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.imagesearchapp.data.repo.UnSplashRepository
import com.example.imagesearchapp.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: UnSplashRepository) : ViewModel() {
    private val currentQuery = MutableLiveData(Constants.DEFAULT_SEARCH)

    val photos = currentQuery.switchMap {
        repository.getSearchResults(it).cachedIn(viewModelScope)
    }

    fun searchPhotos(query : String){
        currentQuery.value = query
    }
}