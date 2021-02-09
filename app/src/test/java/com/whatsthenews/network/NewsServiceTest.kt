package com.whatsthenews.network

import com.skydoves.sandwich.ApiResponse
import com.whatsthenews.MainCoroutinesRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException

@ExperimentalCoroutinesApi
class NewsServiceTest : ApiAbstract<NewsService>() {

    private lateinit var service: NewsService

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesRule = MainCoroutinesRule()

    @Before
    fun initService() {
        service = createService(NewsService::class.java)
    }

    @Throws(IOException::class)
    @Test
    fun fetchNewsTopHeadlinesNetworkTest() = runBlocking {
        enqueueResponse("/NewsResponse.json")
        val response = service.fetchTopHeadlines()
        val responseBody = requireNotNull((response as ApiResponse.Success).data)
        mockWebServer.takeRequest()

        MatcherAssert.assertThat(responseBody.status, CoreMatchers.`is`("ok"))
        MatcherAssert.assertThat(responseBody.articles?.get(0)?.source?.name, CoreMatchers.`is`("Sindonews.com"))
        MatcherAssert.assertThat(responseBody.articles?.get(1)?.author, CoreMatchers.`is`("Dythia Novianty"))
    }

}