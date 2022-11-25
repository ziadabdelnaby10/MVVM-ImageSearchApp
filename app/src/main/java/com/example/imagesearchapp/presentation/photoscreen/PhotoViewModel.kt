package com.example.imagesearchapp.presentation.photoscreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imagesearchapp.data.model.Image
import com.example.imagesearchapp.data.repo.UnSplashRepository
import com.example.imagesearchapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoViewModel @Inject constructor(private val repository: UnSplashRepository) : ViewModel() {

    val loadingObserver = MutableLiveData<Resource<String>>()
    private val _photo = MutableLiveData<Image>()
    val photo: LiveData<Image>
        get() = _photo

    fun getPhoto(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            loadingObserver.postValue(Resource.Loading())
            try {
                val image = repository.getSearchPhoto(id)
                _photo.postValue(image)
                loadingObserver.postValue(Resource.Success("Done"))
            } catch (ex: Exception) {
                ex.localizedMessage?.let { Log.e("PhotoViewModel" , it) }
                loadingObserver.postValue(Resource.Failed(ex))
            }
        }
    }

}