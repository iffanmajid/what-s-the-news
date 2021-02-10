package com.whatsthenews.ui.main

import androidx.annotation.MainThread
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import com.whatsthenews.base.LiveCoroutinesViewModel
import com.whatsthenews.model.News
import com.whatsthenews.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
        private val newsRepository: NewsRepository
) : LiveCoroutinesViewModel() {

    val newsListData: LiveData<List<News>>
    val isLoading: ObservableBoolean = ObservableBoolean(false)
    private val countryHeadlines: MutableStateFlow<String> = MutableStateFlow("id")
    private val _toastLiveData: MutableLiveData<String> = MutableLiveData()


    init {
        newsListData = countryHeadlines.asLiveData().switchMap {
            isLoading.set(true)
            newsRepository.fetchNewsList(
                onSuccess = {
                    isLoading.set(false)
                },
                onError = {
                    _toastLiveData.postValue(it)
                }
            ).asLiveDataOnViewModelScope()
        }
    }

    @MainThread
    fun fetchHeadlines(country: String) {
        countryHeadlines.value = country
    }

}