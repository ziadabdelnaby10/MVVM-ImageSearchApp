package com.example.imagesearchapp.presentation.userscreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.imagesearchapp.data.model.ImageSearch
import com.example.imagesearchapp.data.model.User
import com.example.imagesearchapp.data.repo.UnSplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val repository: UnSplashRepository) : ViewModel() {
    var userName : MutableLiveData<String> = MutableLiveData()
    val photos = userName.switchMap {
        //getUserData(it)
        repository.getUserPhotos(it).cachedIn(viewModelScope)
    }

    val user = MutableLiveData<User>()

    /*fun getPhotos(userName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getUserPhotos(userName)
            photos.postValue(response.value)
        }
    }*/

    fun getUserData(userName : String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getUserAsync(userName)
            user.postValue(response)
        }
    }
}