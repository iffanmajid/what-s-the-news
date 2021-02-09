package com.whatsthenews.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.atLeastOnce
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.skydoves.sandwich.ApiResponse
import com.whatsthenews.MainCoroutinesRule
import com.whatsthenews.model.NewsResponse
import com.whatsthenews.network.NewsClient
import com.whatsthenews.network.NewsService
import com.whatsthenews.utils.MockUtil
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class NewsRepositoryTest {
    private lateinit var repository: NewsRepository
    private lateinit var client: NewsClient
    private val service: NewsService = mock()

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesRule = MainCoroutinesRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        client = NewsClient(service)
        repository = NewsRepository(client)
    }

    @Test
    fun fetchNewsListFromNetworkTest(): Unit = runBlocking {
        val mockData = NewsResponse(
                "ok",
                38,
                listOf()
        )
        whenever(service.fetchTopHeadlines()).thenReturn(ApiResponse.of { Response.success(mockData) })

        repository.fetchNewsList(
            onSuccess = {},
            onError = {}
        ).test {
            Assert.assertEquals(expectItem()[0].description, "")
            Assert.assertEquals(expectItem()[0].title, "")
            assertEquals(expectItem(), MockUtil.mockNewsList())
            expectComplete()
        }

        verify(service, atLeastOnce()).fetchTopHeadlines()
        verify(client, atLeastOnce()).fetchNewsList()
    }
}