package com.whatsthenews.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.nhaarman.mockitokotlin2.atLeastOnce
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.whatsthenews.MainCoroutinesRule
import com.whatsthenews.model.News
import com.whatsthenews.network.NewsClient
import com.whatsthenews.network.NewsService
import com.whatsthenews.repository.NewsRepository
import com.whatsthenews.ui.main.NewsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class NewsViewModelTest {
    
    private lateinit var viewModel: NewsViewModel
    private lateinit var newsRepository: NewsRepository
    private val newsService: NewsService = mock()
    private val newsClient: NewsClient = NewsClient(newsService)

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesRule = MainCoroutinesRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        newsRepository = NewsRepository(newsClient)
        viewModel = NewsViewModel(newsRepository)
    }

    @Test
    fun fetchNewsListTest() = runBlocking {
        val observer: Observer<List<News>> = mock()
        val fetchedData: LiveData<List<News>> =
                newsRepository.fetchNewsList(
                        onSuccess = {},
                        onError = {}
                ).asLiveData()
        fetchedData.observeForever(observer)

        viewModel.fetchHeadlines()
        delay(500L)

        verify(newsRepository, atLeastOnce()).fetchNewsList(mock(), mock())
        fetchedData.removeObserver(observer)
    }
}