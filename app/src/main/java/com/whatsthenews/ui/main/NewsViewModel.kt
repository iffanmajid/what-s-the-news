package com.whatsthenews.ui.main

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

class NewsViewModel(
        private val newsRepository: NewsRepository
) : LiveCoroutinesViewModel() {

    lateinit var newsListData: LiveData<List<News>>
    val isLoading: ObservableBoolean = ObservableBoolean(false)
    private val _toastLiveData: MutableLiveData<String> = MutableLiveData()

    init {
        fetchHeadlines()
    }

    fun fetchHeadlines() {
        isLoading.set(true)
        newsListData =
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