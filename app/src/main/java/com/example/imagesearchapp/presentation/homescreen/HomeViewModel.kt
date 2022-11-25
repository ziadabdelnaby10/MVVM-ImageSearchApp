package com.example.imagesearchapp.presentation.homescreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.imagesearchapp.data.repo.UnSplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: UnSplashRepository) : ViewModel() {
    val photos = repository.getGeneralPhotos().cachedIn(viewModelScope)
}