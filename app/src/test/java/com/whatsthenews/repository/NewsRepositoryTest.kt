package com.whatsthenews.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.*
import com.skydoves.sandwich.ApiResponse
import com.whatsthenews.MainCoroutinesRule
import com.whatsthenews.model.NewsResponse
import com.whatsthenews.network.NewsClient
import com.whatsthenews.network.NewsService
import com.whatsthenews.persistence.NewsDao
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
    private val newsDao: NewsDao = mock()

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesRule = MainCoroutinesRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        client = NewsClient(service)
        repository = NewsRepository(client, newsDao)
    }

    @Test
    fun fetchNewsListFromNetworkTest(): Unit = runBlocking {
        val mockData = NewsResponse(
                "ok",
                38,
                listOf()
        )
        whenever(newsDao.getNewsList()).thenReturn(emptyList())
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

        verify(newsDao, atLeastOnce()).getNewsList()
        verify(service, atLeastOnce()).fetchTopHeadlines()
        verify(client, atLeastOnce()).fetchNewsList()
        verify(newsDao, atLeastOnce()).insertNewsList(listOf())
    }

    @Test
    fun fetchNewsListFromDatabaseTest() = runBlocking {
        val mockData = NewsResponse(
            "ok",
            38,
            listOf()
        )
        whenever(newsDao.getNewsList()).thenReturn(MockUtil.mockNewsList())
        whenever(service.fetchTopHeadlines()).thenReturn(ApiResponse.of { Response.success(mockData) })

        repository.fetchNewsList(
            onSuccess = {},
            onError = {}
        ).test {
            Assert.assertEquals(expectItem()[0].description, "Majalah Farmasetika - Afrika Selatan telah menangguhkan penggunaan vaksin penyakit coronavirus 2019 (COVID-19) Oxford dan AstraZeneca, setelah temuan itu menunjukkan perlindungan minimal terhadap infe")
            Assert.assertEquals(expectItem()[0].title, "Tak Efektif Varian Baru COVID-19, Afrika Selatan Tangguhkan Penggunaan Vaksin AstraZeneca - Majalah Farmasetika")
            assertEquals(expectItem(), MockUtil.mockNewsList())
            expectComplete()
        }

        verify(newsDao, atLeastOnce()).getNewsList()
        verifyNoMoreInteractions(service)
    }
}